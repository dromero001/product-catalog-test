package src.main.kotlin.domain.discount

import java.math.BigDecimal
import src.main.kotlin.domain.Product

class DiscountStrategyApplier(private val strategies: List<DiscountStrategy>) {

    fun applyBiggerDiscount(product: Product): BigDecimal {
        return strategies.minOfOrNull { it.applyDiscount(product) } ?: product.price
    }
}