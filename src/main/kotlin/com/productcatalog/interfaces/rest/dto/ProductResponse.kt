package src.main.kotlin.com.productcatalog.interfaces.rest.dto

import java.math.BigDecimal
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED

@Schema(
    name = "ProductResponse",
    description = "Response with products and applied discounts"
)
data class ProductResponse(
    @field:Schema(
        description = "Stock Keeping Unit",
        example = "SKU0001",
        requiredMode = REQUIRED
    )
    val sku: String,

    @field:Schema(
        description = "DEscription of the product",
        example = "Wireless Mouse with ergonomic design",
        requiredMode = REQUIRED
    )
    val description: String,

    @field:Schema(
        description = "Product category",
        example = "Electronics",
        requiredMode = REQUIRED
    )
    val category: String,

    @field:Schema(
        description = "Original price of the product",
        example = "19.99",
        requiredMode = REQUIRED,
        type = "number",
        format = "decimal"
    )
    val originalPrice: BigDecimal,

    @field:Schema(
        description = "Final price after discount",
        example = "16.99",
        requiredMode = REQUIRED,
        type = "number",
        format = "decimal"
    )
    val finalPrice: BigDecimal,

    @field:Schema(
        description = "Applied discount (0-100)",
        example = "15.00",
        requiredMode = REQUIRED,
        type = "number",
        format = "decimal"
    )
    val discountApplied: BigDecimal,
)