package src.main.kotlin.com.product.productcatalog.domain.discount.strategies

import src.main.kotlin.com.product.productcatalog.domain.Product
import java.math.BigDecimal
import src.main.kotlin.com.product.productcatalog.domain.discount.DiscountStrategy
import src.main.kotlin.com.product.productcatalog.domain.discount.HOME_KITCHEN_CATEGORY
import src.main.kotlin.com.product.productcatalog.domain.discount.a25PercentDiscount

class HomeKitchenDiscount : DiscountStrategy {
    override fun applyDiscount(product: Product): BigDecimal {
        return if (product.category == HOME_KITCHEN_CATEGORY) {
            product.price.multiply(a25PercentDiscount)
        } else {
            product.price
        }
    }
}