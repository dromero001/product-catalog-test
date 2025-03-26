package src.main.kotlin.domain.discount.strategies

import java.math.BigDecimal
import java.math.RoundingMode
import src.main.kotlin.domain.Product
import src.main.kotlin.domain.discount.DiscountStrategy
import src.main.kotlin.domain.discount.ELECTRONICS_CATEGORY
import src.main.kotlin.domain.discount.a15PercentDiscount

class ElectronicsDiscount : DiscountStrategy {
    override fun applyDiscount(product: Product): BigDecimal {
        return if (product.category == ELECTRONICS_CATEGORY) {
            product.price.multiply(a15PercentDiscount).setScale(2, RoundingMode.HALF_UP)
        } else {
            product.price
        }
    }
}