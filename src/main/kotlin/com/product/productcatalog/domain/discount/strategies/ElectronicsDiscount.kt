package src.main.kotlin.com.product.productcatalog.domain.discount.strategies

import src.main.kotlin.com.product.productcatalog.domain.Product
import java.math.BigDecimal
import src.main.kotlin.com.product.productcatalog.domain.discount.DiscountStrategy
import src.main.kotlin.com.product.productcatalog.domain.discount.ELECTRONICS_CATEGORY
import src.main.kotlin.com.product.productcatalog.domain.discount.a15PercentDiscount

class ElectronicsDiscount : DiscountStrategy {
    override fun applyDiscount(product: Product): BigDecimal {
        return if (product.category == ELECTRONICS_CATEGORY) {
            product.price.multiply(a15PercentDiscount)
        } else {
            product.price
        }
    }
}