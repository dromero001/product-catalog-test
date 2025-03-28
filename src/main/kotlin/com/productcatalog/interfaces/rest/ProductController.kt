package src.main.kotlin.com.productcatalog.interfaces.rest

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import java.math.BigDecimal
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import src.main.kotlin.com.productcatalog.application.usecases.GetProductsUseCase
import src.main.kotlin.com.productcatalog.interfaces.rest.dto.ProductResponse

@RestController
@RequestMapping("/api/products")
@Tag(
    name = "Products",
    description = "Products retrieval operations"
)
class ProductController(
    private val getProductsUseCase: GetProductsUseCase
) {
    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Get products filtered and sorted with discounts",
        description = """
        Returns a list of products filtered by category and sorted by by `SKU`, `Price`, `Description` and `Category`
        with biggest discount that can be applied.
        """,
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "List of products with discounts",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ProductResponse::class))
                    )
                ]
            )
        ]
    )
    fun listProducts(
        @Parameter(
            name = "category",
            description = "Filter products by category (ex: 'Electronics')",
            example = "Electronics",
            required = false
        )
        @RequestParam(required = false)
        category: String?,

        @Parameter(
            name = "sortBy",
            description = "sorting field (sku|price|description|category)",
            example = "price",
            schema = Schema(allowableValues = ["sku", "price","description", "category"], defaultValue = "sku")
        )
        @RequestParam(required = false, defaultValue = "sku")
        sortBy: String,

        @Parameter(
            name = "sortOrder",
            description = "asc|desc",
            example = "asc",
            schema = Schema(allowableValues = ["asc", "desc"], defaultValue = "asc")
        )
        @RequestParam(required = false, defaultValue = "asc")
        sortOrder: String,
    ): List<ProductResponse> {
        return getProductsUseCase.execute(category, sortBy, sortOrder).map { (product, priceData) ->
            ProductResponse(
                sku = product.sku,
                description = product.description,
                category = product.category,
                originalPrice = priceData.originalPrice,
                finalPrice = priceData.finalPrice,
                discountApplied = priceData.discountPercentage * BigDecimal(100)
            )
        }
    }
}