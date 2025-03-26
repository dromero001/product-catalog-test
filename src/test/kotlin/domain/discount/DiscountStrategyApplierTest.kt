package src.test.kotlin.domain.discount


import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.math.BigDecimal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import src.main.kotlin.domain.Product
import src.main.kotlin.domain.discount.DiscountStrategy
import src.main.kotlin.domain.discount.DiscountStrategyApplier

class DiscountStrategyApplierTest {

    private lateinit var mockProduct: Product

    @BeforeEach
    fun setup() {
        mockProduct = mockk()
        every { mockProduct.price } returns BigDecimal("100.00")
    }

    @Test
    fun `should apply the biggest discount (lowest price) from all strategies`() {
        every { mockProduct.price } returns BigDecimal("100.00")

        val electronicsDiscount = mockk<DiscountStrategy>().apply {
            every { applyDiscount(mockProduct) } returns BigDecimal("85.00")
        }
        val specialSKUDiscount = mockk<DiscountStrategy>().apply {
            every { applyDiscount(mockProduct) } returns BigDecimal("70.00")
        }
        val homeKitchenDiscount = mockk<DiscountStrategy>().apply {
            every { applyDiscount(mockProduct) } returns BigDecimal("75.00")
        }

        val applier = DiscountStrategyApplier(listOf(electronicsDiscount, specialSKUDiscount, homeKitchenDiscount))

        applier.applyBiggerDiscount(mockProduct) shouldBe BigDecimal("70.00")
    }

    @Test
    fun `should return original price if no strategies are provided`() {
        every { mockProduct.price } returns BigDecimal("100.00")

        val applier = DiscountStrategyApplier(emptyList())

        applier.applyBiggerDiscount(mockProduct) shouldBe BigDecimal("100.00")
    }
}