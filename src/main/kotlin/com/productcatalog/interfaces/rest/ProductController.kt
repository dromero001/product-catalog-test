package src.main.kotlin.com.productcatalog.interfaces.rest

import java.math.BigDecimal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import src.main.kotlin.com.productcatalog.application.usecases.GetProductsUseCase
import src.main.kotlin.com.productcatalog.interfaces.rest.dto.ProductResponse

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val getProductsUseCase: GetProductsUseCase
) {
    @GetMapping
    fun listProducts(
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false, defaultValue = "sku") sortBy: String,
        @RequestParam(required = false, defaultValue = "asc") sortOrder: String,
    ): List<ProductResponse> {
        return getProductsUseCase.execute(category, sortBy, sortOrder).map { (product, priceData) ->
            ProductResponse(
                sku = product.sku,
                name = product.description,
                category = product.category,
                originalPrice = priceData.originalPrice,
                finalPrice = priceData.finalPrice,
                discountApplied = priceData.discountPercentage * BigDecimal(100)
            )
        }
    }
}