package src.test.kotlin.com.productcatalog.interfaces.rest

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
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

        every { getProductsUseCase.execute(null,"sku", "asc") } returns products

        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk)
            .andExpect(content().json("""
            [
                {
                    "sku": "SKU0002",
                    "description": "4K Ultra HD Smart TV, 55 inches",
                    "category": "Electronics",
                    "originalPrice": 499.00,
                    "finalPrice": 424.15,
                    "discountApplied": 15
                }
            ]
        """))

        verify { getProductsUseCase.execute(null,"sku", "asc") }
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

        every { getProductsUseCase.execute(category,"sku", "asc") } returns products

        mockMvc.perform(get("/api/products?category=$category"))
            .andExpect(status().isOk)
            .andExpect(content().json("""
            [{
                "sku": "SKU0005",
                "description": "Noise-Cancelling Over-Ear Headphones",
                "category": "Electronics",
                "originalPrice": 120.00,
                "finalPrice": 84.00,
                "discountApplied": 30
            }]
        """))
    }

    @Test
    fun `should return empty list when no products found`() {

        every { getProductsUseCase.execute("NonExisting","sku", "asc") } returns emptyList()

        mockMvc.perform(get("/api/products?category=NonExisting"))
            .andExpect(status().isOk)
            .andExpect {
                it.response.contentAsString shouldBe emptyList<ProductResponse>().toJson()
            }
    }

    @Test
    fun `should return products sorted by category ascending`() {
        val products = listOf(
            Pair(
                Product("SKU0004", BigDecimal("15.00"), "Cotton T-Shirt", "Clothing"),
                ProductPriceAfterDiscount(
                    originalPrice = BigDecimal("15.00"),
                    discountPercentage = ZERO,
                    finalPrice = BigDecimal("15.00")
                )
            ),
            Pair(
                Product("SKU0002", BigDecimal("499.00"), "Smart TV", "Electronics"),
                ProductPriceAfterDiscount(
                    originalPrice = BigDecimal("499.00"),
                    discountPercentage = BigDecimal("0.15"),
                    finalPrice = BigDecimal("424.15")
                )
            )
        )

        every { getProductsUseCase.execute(null, "category", "asc") } returns products

        mockMvc.perform(get("/api/products?sortBy=category&sortOrder=asc"))
            .andExpect(status().isOk)
            .andExpect(content().json("""
            [
                {
                    "sku": "SKU0004",
                    "description": "Cotton T-Shirt",
                    "category": "Clothing",
                    "originalPrice": 15.00,
                    "finalPrice": 15.00,
                    "discountApplied": 0
                },
                {
                    "sku": "SKU0002",
                    "description": "Smart TV",
                    "category": "Electronics",
                    "originalPrice": 499.00,
                    "finalPrice": 424.15,
                    "discountApplied": 15
                }
            ]
        """))
    }

    @Test
    fun `should return products sorted by price descending`() {
        val products = listOf(
            Pair(
                Product("SKU0002", BigDecimal("499.00"), "Smart TV", "Electronics"),
                ProductPriceAfterDiscount(
                    originalPrice = BigDecimal("499.00"),
                    discountPercentage = BigDecimal("0.15"),
                    finalPrice = BigDecimal("424.15")
                )
            ),
            Pair(
                Product("SKU0004", BigDecimal("15.00"), "Cotton T-Shirt", "Clothing"),
                ProductPriceAfterDiscount(
                    originalPrice = BigDecimal("15.00"),
                    discountPercentage = ZERO,
                    finalPrice = BigDecimal("15.00")
                )
            )
        )

        every { getProductsUseCase.execute(null, "price", "desc") } returns products

        mockMvc.perform(get("/api/products?sortBy=price&sortOrder=desc"))
            .andExpect(status().isOk)
            .andExpect(content().json("""
            [
                {
                    "sku": "SKU0002",
                    "description": "Smart TV",
                    "category": "Electronics",
                    "originalPrice": 499.00,
                    "finalPrice": 424.15,
                    "discountApplied": 15
                },
                {
                    "sku": "SKU0004",
                    "description": "Cotton T-Shirt",
                    "category": "Clothing",
                    "originalPrice": 15.00,
                    "finalPrice": 15.00,
                    "discountApplied": 0
                }
            ]
        """))
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
                "description": "$description",
                "category": "$category",
                "originalPrice": ${originalPrice},
                "finalPrice": ${finalPrice},
                "discountApplied": $discountApplied
            }""".trimIndent()
            else -> toString()
        }
    }
}