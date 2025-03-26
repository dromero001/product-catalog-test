package src.test.kotlin.com.productcatalog.domain.discount

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.discount.DiscountStrategy
import src.main.kotlin.com.productcatalog.domain.discount.DiscountStrategyApplier
import src.main.kotlin.com.productcatalog.domain.discount.a15PercentDiscount
import src.main.kotlin.com.productcatalog.domain.discount.a25PercentDiscount
import src.main.kotlin.com.productcatalog.domain.discount.a30PercentDiscount

class DiscountStrategyApplierTest {

    private val product = mockk<Product>()
    private val strategy1 = mockk<DiscountStrategy>()
    private val strategy2 = mockk<DiscountStrategy>()
    private val strategy3 = mockk<DiscountStrategy>()
    private lateinit var discountStrategyApplier : DiscountStrategyApplier

    @BeforeEach
    fun setUp() {
        discountStrategyApplier = DiscountStrategyApplier(listOf(strategy1, strategy2, strategy3))
    }

    @Test
    fun `should apply the strategy with the biggest discount`() {

        every { strategy1.applyDiscount(product) } returns electronicsDiscount
        every { strategy2.applyDiscount(product) } returns specialSKUDiscount
        every { strategy3.applyDiscount(product) } returns homeKitchenDiscount

        discountStrategyApplier.applyBiggerDiscount(product) shouldBe specialSKUDiscount
    }

    @Test
    fun `should return original price if no discount is applied`() {

        every { strategy1.applyDiscount(product) } returns noDiscount
        every { strategy2.applyDiscount(product) } returns noDiscount
        every { strategy3.applyDiscount(product) } returns noDiscount
        every { product.price } returns BigDecimal("100.00")

        discountStrategyApplier.applyBiggerDiscount(product) shouldBe noDiscount
    }

    @Test
    fun `should return original price if no strategies are provided`() {

        val emptyDiscountStrategyApplier = DiscountStrategyApplier(emptyList())
        every { product.price } returns BigDecimal("100.00")

        emptyDiscountStrategyApplier.applyBiggerDiscount(product) shouldBe noDiscount
    }

    companion object {
        private const val ORIGINAL_PRICE = "100.00"

        private fun createDiscountResult(
            original: String = ORIGINAL_PRICE,
            percentage: BigDecimal,
            final: String
        ) = ProductPriceAfterDiscount(
            originalPrice = BigDecimal(original),
            discountPercentage = percentage,
            finalPrice = BigDecimal(final)
        )

        val electronicsDiscount = createDiscountResult(
            percentage = a15PercentDiscount,
            final = "85.00"
        )

        val specialSKUDiscount = createDiscountResult(
            percentage = a30PercentDiscount,
            final = "70.00"
        )

        val homeKitchenDiscount = createDiscountResult(
            percentage = a25PercentDiscount,
            final = "75.00"
        )

        val noDiscount = createDiscountResult(
            percentage = ZERO,
            final = ORIGINAL_PRICE
        )
    }
}
