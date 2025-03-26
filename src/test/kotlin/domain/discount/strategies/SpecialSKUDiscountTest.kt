package src.test.kotlin.domain.discount.strategies

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.math.BigDecimal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import src.main.kotlin.domain.Product
import src.main.kotlin.domain.discount.ELECTRONICS_CATEGORY
import src.main.kotlin.domain.discount.strategies.SpecialSKUDiscount

class SpecialSKUDiscountTest {

    private lateinit var product: Product
    private val discountStrategy = SpecialSKUDiscount()

    @BeforeEach
    fun setUp() {
        product = mockk()
    }

    @Test
    fun `should apply 30 percent discount for Electronics with specific SKU`() {
        mockProduct("SKU0015")
        discountStrategy.applyDiscount(product) shouldBe BigDecimal("70.00")
    }

    @Test
    fun `should return original price for other SKUs`() {
        mockProduct("SKU0016")
        discountStrategy.applyDiscount(product) shouldBe BigDecimal("100.00")
    }

    private fun mockProduct(sku: String) {
        every { product.category } returns ELECTRONICS_CATEGORY
        every { product.sku } returns sku
        every { product.price } returns BigDecimal("100.00")
    }
}
