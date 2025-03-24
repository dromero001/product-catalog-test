package src.main.kotlin.com.product.productcatalog.domain.discount

import src.main.kotlin.com.product.productcatalog.domain.Product
import java.math.BigDecimal

class DiscountStrategyApplier(private val strategies: List<DiscountStrategy>) {

    fun applyBiggerDiscount(product: Product): BigDecimal {
        return strategies.minOfOrNull { it.applyDiscount(product) } ?: product.price
    }
}