package src.main.kotlin.com.product.productcatalog.domain.discount

import src.main.kotlin.com.product.productcatalog.domain.Product
import java.math.BigDecimal

class HomeKitchenDiscount : DiscountStrategy {
    override fun applyDiscount(product: Product): BigDecimal {
        return if (product.category == HOME_KITCHEN_CATEGORY) {
            product.price.multiply(a25PercentDiscount)
        } else {
            product.price
        }
    }
}