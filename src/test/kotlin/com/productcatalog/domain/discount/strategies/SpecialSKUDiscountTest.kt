package src.test.kotlin.com.productcatalog.domain.discount.strategies

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.discount.a30PercentDiscount
import src.main.kotlin.com.productcatalog.domain.discount.strategies.SpecialSKUDiscount

class SpecialSKUDiscountTest {

    private val discountStrategy = SpecialSKUDiscount()
    private val product = mockk<Product>()

    @Nested
    inner class CanApply {
        @Test
        fun `should return true for special SKU`() {

            every { product.sku } returns "SKU0005"
            every { product.price } returns BigDecimal("100.00")
            discountStrategy.canApply(product) shouldBe true
        }

        @Test
        fun `shouldn't apply to not special SKU category`() {

            every { product.sku } returns "SKU0006"
            every { product.price } returns BigDecimal("100.00")
            discountStrategy.canApply(product) shouldBe false
        }
    }

    @Test
    fun `should apply 30 percent discount for special SKU ending with 5`() {

        every { product.sku } returns "SKU0005"
        every { product.price } returns BigDecimal("100.00")

        discountStrategy.applyDiscount(product) shouldBe ProductPriceAfterDiscount(
            originalPrice = BigDecimal("100.00"),
            discountPercentage = a30PercentDiscount,
            finalPrice = BigDecimal("70.00")
        )
    }
}
