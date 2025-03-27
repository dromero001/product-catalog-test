package src.test.kotlin.com.productcatalog.application.usecases

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import src.main.kotlin.com.productcatalog.application.usecases.ApplyProductDiscountUseCase
import src.main.kotlin.com.productcatalog.application.usecases.DefaultGetProductsUseCase
import src.main.kotlin.com.productcatalog.application.usecases.GetProductsUseCase
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.discount.CLOTHING_CATEGORY
import src.main.kotlin.com.productcatalog.domain.discount.ELECTRONICS_CATEGORY
import src.main.kotlin.com.productcatalog.domain.discount.HOME_KITCHEN_CATEGORY
import src.main.kotlin.com.productcatalog.domain.discount.a15PercentDiscount
import src.main.kotlin.com.productcatalog.domain.discount.a25PercentDiscount
import src.main.kotlin.com.productcatalog.domain.discount.a30PercentDiscount
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.domain.repository.ProductRepository

class DefaultGetProductsUseCaseTest {

    private lateinit var defaultGetProductsUseCase: GetProductsUseCase
    private val productRepository = mockk<ProductRepository>()
    private val applyDiscount = mockk<ApplyProductDiscountUseCase>()

    @BeforeEach
    fun setUp() {
        defaultGetProductsUseCase = DefaultGetProductsUseCase(productRepository, applyDiscount)
    }

    @Test
    fun `should apply 30 percent discount for SKU ending with 5 and category discounts for others`() {

        every { productRepository.findAll() } returns allProducts

        discounts.forEachIndexed { i, discount ->
            every { applyDiscount.execute(allProducts[i]) } returns discount
        }

        defaultGetProductsUseCase.execute(null) shouldBe listOf(
            TV to discounts[0],
            BOTTLE to discounts[1],
            TSHIRT to discounts[2],
            HEADPHONES to discounts[3]
        )
    }

    @Test
    fun `should prioritize SKU discount over category discount`() {

        every { productRepository.findByCategory(ELECTRONICS_CATEGORY) } returns listOf(specialProduct, normalProduct)
        every { applyDiscount.execute(specialProduct) } returns
                createDiscount(specialProduct, a30PercentDiscount, BigDecimal("140.00"))
        every { applyDiscount.execute(normalProduct) } returns
                createDiscount(normalProduct, a15PercentDiscount, BigDecimal("170.00"))

        defaultGetProductsUseCase.execute(ELECTRONICS_CATEGORY) shouldBe listOf(
            specialProduct to createDiscount(specialProduct, a30PercentDiscount, BigDecimal("140.00")),
            normalProduct to createDiscount(normalProduct, a15PercentDiscount, BigDecimal("170.00"))
        )
    }

    companion object {

        val TV = Product("SKU0002", BigDecimal("499.00"), "4K Ultra HD Smart TV, 55 inches", ELECTRONICS_CATEGORY)
        val BOTTLE = Product("SKU0003", BigDecimal("29.50"), "Stainless Steel Water Bottle, 1L", HOME_KITCHEN_CATEGORY)
        val TSHIRT = Product("SKU0004", BigDecimal("15.00"), "Cotton T-Shirt, Unisex, Size M", CLOTHING_CATEGORY)
        val HEADPHONES = Product("SKU0005", BigDecimal("120.00"), "Noise-Cancelling Over-Ear Headphones", ELECTRONICS_CATEGORY)

        val allProducts = listOf(TV, BOTTLE, TSHIRT, HEADPHONES)
        val discounts = listOf(
            createDiscount(TV, a15PercentDiscount, BigDecimal("424.15")),
            createDiscount(BOTTLE, a25PercentDiscount, BigDecimal("22.13")),
            createDiscount(TSHIRT, ZERO, BigDecimal("15.00")),
            createDiscount(HEADPHONES, a30PercentDiscount, BigDecimal("84.00")),
        )

        val specialProduct = Product("SKU1235", BigDecimal("200.00"), "Special Product", ELECTRONICS_CATEGORY)
        val normalProduct = Product("SKU1234", BigDecimal("200.00"), "Normal Product", ELECTRONICS_CATEGORY)

        fun createDiscount(product: Product, percentage: BigDecimal, finalPrice: BigDecimal) =
            ProductPriceAfterDiscount(
                originalPrice = product.price,
                discountPercentage = percentage,
                finalPrice = finalPrice,
            )
    }
}