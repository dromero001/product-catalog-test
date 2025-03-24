package src.main.kotlin.com.product.productcatalog.domain.discount

import src.main.kotlin.com.product.productcatalog.domain.Product
import java.math.BigDecimal

class SpecialSKUDiscount : DiscountStrategy {
    override fun applyDiscount(product: Product): BigDecimal {
        return if (product.sku.endsWith(SPECIAL_SKU_SUFFIX)) {
            product.price.multiply(a30PercentDiscount)
        } else {
            product.price
        }
    }
}