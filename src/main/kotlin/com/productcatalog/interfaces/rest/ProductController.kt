package src.main.kotlin.com.productcatalog.interfaces.rest

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import java.math.BigDecimal
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import src.main.kotlin.com.productcatalog.application.usecases.GetProductsUseCase
import src.main.kotlin.com.productcatalog.interfaces.rest.dto.ProductResponse

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Products retrieval operations")
class ProductController(private val getProductsUseCase: GetProductsUseCase) {

    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    @Operation(summary = "Get products filtered, sorted and paginated with discounts")
    fun listProducts(
        @Parameter(description = "Filter products by category")
        @RequestParam(required = false) category: String?,

        @Parameter(description = "Sorting field (sku|price|description|category)", schema = Schema(defaultValue = "sku"))
        @RequestParam(defaultValue = "sku") sortBy: String,

        @Parameter(description = "Sort order (asc|desc)", schema = Schema(defaultValue = "asc"))
        @RequestParam(defaultValue = "asc") sortOrder: String,

        @Parameter(description = "Page number (1-based)", schema = Schema(defaultValue = "1", minimum = "1"))
        @RequestParam(defaultValue = "1") page: Int,

        @Parameter(description = "Items per page", schema = Schema(defaultValue = "10", minimum = "1"))
        @RequestParam(defaultValue = "10") size: Int
    ): PageResponse<ProductResponse> {
        val pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy))
        val result = getProductsUseCase.execute(category, pageable)

        return PageResponse(
            content = result.content.map { (product, priceData) ->
                ProductResponse(
                    sku = product.sku,
                    description = product.description,
                    category = product.category,
                    originalPrice = priceData.originalPrice,
                    finalPrice = priceData.finalPrice,
                    discountApplied = priceData.discountPercentage * BigDecimal(100)
                )
            },
            totalElements = result.totalElements,
            totalPages = result.totalPages,
            currentPage = page,
            pageSize = result.size
        )
    }
}

data class PageResponse<T>(
    val content: List<T>,
    val totalElements: Long,
    val totalPages: Int,
    val currentPage: Int,
    val pageSize: Int
)