package src.test.kotlin.com.productcatalog.domain.discount.strategies

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.discount.ELECTRONICS_CATEGORY
import src.main.kotlin.com.productcatalog.domain.discount.HOME_KITCHEN_CATEGORY
import src.main.kotlin.com.productcatalog.domain.discount.a15PercentDiscount
import src.main.kotlin.com.productcatalog.domain.discount.strategies.ElectronicsDiscount
import src.main.kotlin.com.productcatalog.domain.model.Product

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

        discountStrategy.applyDiscount(product) shouldBe ProductPriceAfterDiscount(
            originalPrice = BigDecimal("100.00"),
            discountPercentage = a15PercentDiscount,
            finalPrice = BigDecimal("85.00")
        )
    }

    @Test
    fun `should return original price for other categories`() {

        mockProduct(HOME_KITCHEN_CATEGORY)

        discountStrategy.applyDiscount(product) shouldBe ProductPriceAfterDiscount(
            originalPrice = BigDecimal("100.00"),
            discountPercentage = ZERO,
            finalPrice = BigDecimal("100.00")
        )
    }

    private fun mockProduct(category: String) {
        every { product.category } returns category
        every { product.price } returns BigDecimal("100.00")
    }
}