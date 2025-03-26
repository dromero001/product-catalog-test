package src.main.kotlin.com.productcatalog.domain

import java.math.BigDecimal

data class ProductPriceAfterDiscount(
    val originalPrice: BigDecimal,
    val discountPercentage: BigDecimal,
    val finalPrice: BigDecimal
)