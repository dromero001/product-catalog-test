package src.main.kotlin.domain.discount.strategies

import java.math.BigDecimal
import java.math.RoundingMode
import src.main.kotlin.domain.Product
import src.main.kotlin.domain.discount.DiscountStrategy
import src.main.kotlin.domain.discount.SPECIAL_SKU_SUFFIX
import src.main.kotlin.domain.discount.a30PercentDiscount

class SpecialSKUDiscount : DiscountStrategy {
    override fun applyDiscount(product: Product): BigDecimal {
        return if (product.sku.endsWith(SPECIAL_SKU_SUFFIX)) {
            product.price.multiply(a30PercentDiscount).setScale(2, RoundingMode.HALF_UP)
        } else {
            product.price
        }
    }
}