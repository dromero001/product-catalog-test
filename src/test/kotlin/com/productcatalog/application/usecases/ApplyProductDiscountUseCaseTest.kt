package src.test.kotlin.com.productcatalog.application.usecases

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal
import org.junit.jupiter.api.Test
import src.main.kotlin.com.productcatalog.application.usecases.ApplyProductDiscountUseCase
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.discount.DiscountStrategyApplier
import src.main.kotlin.com.productcatalog.domain.discount.a25PercentDiscount

class ApplyProductDiscountUseCaseTest {

    private val discountStrategyApplier = mockk<DiscountStrategyApplier>()
    private val applyProductDiscountUseCase = ApplyProductDiscountUseCase(discountStrategyApplier)
    private val product = mockk<Product>()

    @Test
    fun `should return discount application`() {

        val expectedResult = ProductPriceAfterDiscount(
            originalPrice = BigDecimal("100.00"),
            discountPercentage = a25PercentDiscount,
            finalPrice = BigDecimal("75.00")
        )
        every { discountStrategyApplier.applyBiggerDiscount(product) } returns expectedResult

        applyProductDiscountUseCase.execute(product) shouldBe expectedResult

        verify { discountStrategyApplier.applyBiggerDiscount(product) }
    }
}
