package src.test.kotlin.com.productcatalog.interfaces.rest

import io.mockk.every
import io.mockk.mockk
import java.math.BigDecimal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
import src.main.kotlin.com.productcatalog.application.usecases.GetProductsUseCase
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.discount.ELECTRONICS_CATEGORY
import src.main.kotlin.com.productcatalog.domain.discount.a15PercentDiscount
import src.main.kotlin.com.productcatalog.domain.discount.a30PercentDiscount
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.interfaces.rest.ProductController

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
    fun `should return paginated products with discounts`() {
        val pageable = PageRequest.of(0, 1)
        val products = PageImpl(
            listOf(
                Pair(
                    Product("SKU0002", BigDecimal("499.00"), "4K Ultra HD Smart TV, 55 inches", ELECTRONICS_CATEGORY),
                    ProductPriceAfterDiscount(
                        originalPrice = BigDecimal("499.00"),
                        discountPercentage = a15PercentDiscount,
                        finalPrice = BigDecimal("424.15")
                    )
                )
            ),
            pageable,
            4
        )

        every { getProductsUseCase.execute(null, any()) } returns products

        mockMvc.perform(get("/api/products?page=1&size=1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content[0].sku").value("SKU0002"))
            .andExpect(jsonPath("$.content[0].finalPrice").value(424.15))
            .andExpect(jsonPath("$.totalElements").value(4))
            .andExpect(jsonPath("$.currentPage").value(1))
    }

    @Test
    fun `should return filtered and paginated products by category`() {
        val pageable = PageRequest.of(0, 2)
        val products = PageImpl(
            listOf(
                Pair(
                    Product("SKU0005", BigDecimal("120.00"), "Noise-Cancelling Over-Ear Headphones", ELECTRONICS_CATEGORY),
                    ProductPriceAfterDiscount(
                        originalPrice = BigDecimal("120.00"),
                        discountPercentage = a30PercentDiscount,
                        finalPrice = BigDecimal("84.00")
                    )
                )
            ),
            pageable,
            1
        )

        every { getProductsUseCase.execute(ELECTRONICS_CATEGORY, any()) } returns products

        mockMvc.perform(get("/api/products?category=Electronics&page=1&size=2"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content[0].sku").value("SKU0005"))
            .andExpect(jsonPath("$.content[0].discountApplied").value(30))
            .andExpect(jsonPath("$.totalElements").value(1))
    }

    @Test
    fun `should return sorted products`() {
        val pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "price"))
        val products = PageImpl(
            listOf(
                Pair(
                    Product("SKU0002", BigDecimal("499.00"), "Smart TV", ELECTRONICS_CATEGORY),
                    ProductPriceAfterDiscount(
                        originalPrice = BigDecimal("499.00"),
                        discountPercentage = a15PercentDiscount,
                        finalPrice = BigDecimal("424.15")
                    )
                )
            ),
            pageable,
            1
        )

        every { getProductsUseCase.execute(null, any()) } returns products

        mockMvc.perform(get("/api/products?sortBy=price&sortOrder=desc&page=1&size=2"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content[0].sku").value("SKU0002"))
            .andExpect(jsonPath("$.content[0].finalPrice").value(424.15))
    }
}