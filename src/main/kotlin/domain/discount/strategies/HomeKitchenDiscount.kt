package src.main.kotlin.domain.discount.strategies

import java.math.BigDecimal
import java.math.RoundingMode
import src.main.kotlin.domain.Product
import src.main.kotlin.domain.discount.DiscountStrategy
import src.main.kotlin.domain.discount.HOME_KITCHEN_CATEGORY
import src.main.kotlin.domain.discount.a25PercentDiscount

class HomeKitchenDiscount : DiscountStrategy {
    override fun applyDiscount(product: Product): BigDecimal {
        return if (product.category == HOME_KITCHEN_CATEGORY) {
            product.price.multiply(a25PercentDiscount).setScale(2, RoundingMode.HALF_UP)
        } else {
            product.price
        }
    }
}