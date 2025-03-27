package src.test.kotlin.com.productcatalog.application.usecases

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import src.main.kotlin.com.productcatalog.application.usecases.ApplyProductDiscountUseCase
import src.main.kotlin.com.productcatalog.application.usecases.DefaultApplyProductDiscountUseCase
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.discount.CLOTHING_CATEGORY
import src.main.kotlin.com.productcatalog.domain.discount.DiscountStrategyApplier
import src.main.kotlin.com.productcatalog.domain.discount.ELECTRONICS_CATEGORY
import src.main.kotlin.com.productcatalog.domain.discount.a15PercentDiscount
import src.main.kotlin.com.productcatalog.domain.discount.a30PercentDiscount
import src.main.kotlin.com.productcatalog.domain.model.Product

class DefaultApplyProductDiscountUseCaseTest {

    private lateinit var defaultApplyProductDiscountUseCase: ApplyProductDiscountUseCase
    private val discountStrategyApplier = mockk<DiscountStrategyApplier>()

    @BeforeEach
    fun setUp() {
        defaultApplyProductDiscountUseCase = DefaultApplyProductDiscountUseCase(discountStrategyApplier)
    }

    @Test
    fun `should apply 30 percent discount for SKU ending with 5 even category is Electronics`() {

        val expectedDiscount = createDiscount(SPECIAL_PRODUCT.price, a30PercentDiscount, BigDecimal("70.00"))

        every { discountStrategyApplier.applyBiggerDiscount(SPECIAL_PRODUCT) } returns expectedDiscount

        defaultApplyProductDiscountUseCase.execute(SPECIAL_PRODUCT) shouldBe expectedDiscount

        verify { discountStrategyApplier.applyBiggerDiscount(SPECIAL_PRODUCT) }
    }

    @Test
    fun `should apply category discount for normal SKU`() {

        val expectedDiscount = createDiscount(NORMAL_PRODUCT.price, a15PercentDiscount, BigDecimal("85.00"))

        every { discountStrategyApplier.applyBiggerDiscount(NORMAL_PRODUCT) } returns expectedDiscount

        defaultApplyProductDiscountUseCase.execute(NORMAL_PRODUCT) shouldBe expectedDiscount
    }

    @Test
    fun `should apply no discount for non-special SKU in non-discounted category`() {

        val expectedDiscount = createDiscount(CLOTHING_PRODUCT.price, ZERO, BigDecimal("50.00"))

        every { discountStrategyApplier.applyBiggerDiscount(CLOTHING_PRODUCT) } returns expectedDiscount

        defaultApplyProductDiscountUseCase.execute(CLOTHING_PRODUCT) shouldBe expectedDiscount
    }

    companion object {

        val SPECIAL_PRODUCT = Product("SKU0005", BigDecimal("100.00"), "Special Product", ELECTRONICS_CATEGORY)
        val NORMAL_PRODUCT = Product("SKU0004", BigDecimal("100.00"), "Normal Product", ELECTRONICS_CATEGORY)
        val CLOTHING_PRODUCT = Product("SKU0001", BigDecimal("50.00"), "T-Shirt", CLOTHING_CATEGORY)

        fun createDiscount(original: BigDecimal, percentage: BigDecimal, final: BigDecimal) =
            ProductPriceAfterDiscount(
                originalPrice = original,
                discountPercentage = percentage,
                finalPrice = final
            )
    }
}