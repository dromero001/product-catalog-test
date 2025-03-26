package src.test.kotlin.domain.discount.strategies

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.math.BigDecimal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import src.main.kotlin.domain.Product
import src.main.kotlin.domain.discount.ELECTRONICS_CATEGORY
import src.main.kotlin.domain.discount.HOME_KITCHEN_CATEGORY
import src.main.kotlin.domain.discount.strategies.ElectronicsDiscount

class ElectronicsDiscountTest {

    private lateinit var product: Product
    private val discountStrategy = ElectronicsDiscount()

    @BeforeEach
    fun setUp() {
        product = mockk()
    }

    @Test
    fun `should apply 15 percent discount for Electronics`() {
        mockProduct(ELECTRONICS_CATEGORY)
        discountStrategy.applyDiscount(product) shouldBe BigDecimal("85.00")
    }

    @Test
    fun `should return original price for other categories`() {
        mockProduct(HOME_KITCHEN_CATEGORY)
        discountStrategy.applyDiscount(product) shouldBe BigDecimal("100.00")
    }

    private fun mockProduct(category: String) {
        every { product.category } returns category
        every { product.price } returns BigDecimal("100.00")
    }
}