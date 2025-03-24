package src.main.kotlin.com.product.productcatalog.domain.discount

import src.main.kotlin.com.product.productcatalog.domain.Product
import java.math.BigDecimal

class ElectronicsDiscount : DiscountStrategy {
    override fun applyDiscount(product: Product): BigDecimal {
        return if (product.category == ELECTRONICS_CATEGORY) {
            product.price.multiply(a15PercentDiscount)
        } else {
            product.price
        }
    }
}