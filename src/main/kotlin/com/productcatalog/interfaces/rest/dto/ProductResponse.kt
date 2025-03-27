package src.main.kotlin.com.productcatalog.interfaces.rest.dto

import java.math.BigDecimal

data class ProductResponse(
    val sku: String,
    val description: String,
    val category: String,
    val originalPrice: BigDecimal,
    val finalPrice: BigDecimal,
    val discountApplied: BigDecimal,
)