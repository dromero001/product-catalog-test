package src.test.kotlin.com.productcatalog.interfaces.rest

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
import src.main.kotlin.com.productcatalog.application.usecases.GetProductsUseCase
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.discount.ELECTRONICS_CATEGORY
import src.main.kotlin.com.productcatalog.domain.discount.a15PercentDiscount
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.interfaces.rest.ProductController
import src.main.kotlin.com.productcatalog.interfaces.rest.dto.ProductResponse

class ProductControllerTest {

    private lateinit var mockMvc: MockMvc
    private val getProductsUseCase = mockk<GetProductsUseCase>()

    @BeforeEach
    fun setUp() {
        mockMvc = standaloneSetup(
            ProductController(getProductsUseCase)
        ).build()
    }

    @Test
    fun `should return products with discounts when no category filter`() {

        val products = listOf(
            Pair(
                Product("SKU0002", BigDecimal("499.00"), "4K Ultra HD Smart TV, 55 inches", ELECTRONICS_CATEGORY),
                ProductPriceAfterDiscount(
                    originalPrice = BigDecimal("499.00"),
                    discountPercentage = a15PercentDiscount,
                    finalPrice = BigDecimal("424.15")
                )
            )
        )

        every { getProductsUseCase.execute(null) } returns products

        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk)
            .andExpect(content().json("""
            [
                {
                    "sku": "SKU0002",
                    "name": "4K Ultra HD Smart TV, 55 inches",
                    "category": "Electronics",
                    "originalPrice": 499.00,
                    "finalPrice": 424.15,
                    "discountApplied": 15
                }
            ]
        """))

        verify { getProductsUseCase.execute(null) }
    }

    @Test
    fun `should return filtered products when category is specified`() {

        val category = "Electronics"
        val products = listOf(
            Pair(
                Product("SKU0005", BigDecimal("120.00"), "Noise-Cancelling Over-Ear Headphones", "Electronics"),
                ProductPriceAfterDiscount(
                    originalPrice = BigDecimal("120.00"),
                    discountPercentage = BigDecimal("0.30"),
                    finalPrice = BigDecimal("84.00")
                )
            )
        )

        every { getProductsUseCase.execute(category) } returns products

        mockMvc.perform(get("/api/products?category=$category"))
            .andExpect(status().isOk)
            .andExpect(content().json("""
            [{
                "sku": "SKU0005",
                "name": "Noise-Cancelling Over-Ear Headphones",
                "category": "Electronics",
                "originalPrice": 120.00,
                "finalPrice": 84.00,
                "discountApplied": 30
            }]
        """))
    }

    @Test
    fun `should return empty list when no products found`() {

        every { getProductsUseCase.execute(any()) } returns emptyList()

        mockMvc.perform(get("/api/products?category=NonExisting"))
            .andExpect(status().isOk)
            .andExpect {
                it.response.contentAsString shouldBe emptyList<ProductResponse>().toJson()
            }
    }

    private fun Any.toJson(): String {
        return when (this) {
            is List<*> -> {
                if (isEmpty()) "[]" else joinToString(
                    prefix = "[",
                    postfix = "]",
                    transform = { it!!.toJson() }
                )
            }
            is ProductResponse -> """{
                "sku": "$sku",
                "name": "$name",
                "category": "$category",
                "originalPrice": ${originalPrice},
                "finalPrice": ${finalPrice},
                "discountApplied": $discountApplied
            }""".trimIndent()
            else -> toString()
        }
    }
}