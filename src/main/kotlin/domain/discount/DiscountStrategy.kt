package src.main.kotlin.domain.discount

import java.math.BigDecimal
import src.main.kotlin.domain.Product

fun interface DiscountStrategy {
    fun applyDiscount(product: Product): BigDecimal
}