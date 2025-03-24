package src.main.kotlin.com.product.productcatalog.domain.discount

import java.math.BigDecimal
import src.main.kotlin.com.product.productcatalog.domain.Product

fun interface DiscountStrategy {
    fun applyDiscount(product: Product): BigDecimal
}