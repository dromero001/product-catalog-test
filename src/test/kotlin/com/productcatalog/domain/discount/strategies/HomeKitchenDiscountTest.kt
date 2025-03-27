package src.test.kotlin.com.productcatalog.domain.discount.strategies

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.discount.ELECTRONICS_CATEGORY
import src.main.kotlin.com.productcatalog.domain.discount.HOME_KITCHEN_CATEGORY
import src.main.kotlin.com.productcatalog.domain.discount.a25PercentDiscount
import src.main.kotlin.com.productcatalog.domain.discount.strategies.HomeKitchenDiscount

class HomeKitchenDiscountTest {

    private lateinit var product: Product
    private val discountStrategy = HomeKitchenDiscount()

    @BeforeEach
    fun setUp() {
        product = mockk()
    }

    @Test
    fun `should apply 25 percent discount for Home and Kitchen`() {

        mockProduct(HOME_KITCHEN_CATEGORY)

        discountStrategy.applyDiscount(product) shouldBe ProductPriceAfterDiscount(
            originalPrice = BigDecimal("100.00"),
            discountPercentage = a25PercentDiscount,
            finalPrice = BigDecimal("75.00")
        )
    }

    @Test
    fun `should return original price for other categories`() {

        mockProduct(ELECTRONICS_CATEGORY)

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
