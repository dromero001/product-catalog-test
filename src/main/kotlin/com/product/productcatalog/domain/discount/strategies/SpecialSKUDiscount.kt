package src.main.kotlin.com.product.productcatalog.domain.discount.strategies

import src.main.kotlin.com.product.productcatalog.domain.Product
import java.math.BigDecimal
import src.main.kotlin.com.product.productcatalog.domain.discount.DiscountStrategy
import src.main.kotlin.com.product.productcatalog.domain.discount.SPECIAL_SKU_SUFFIX
import src.main.kotlin.com.product.productcatalog.domain.discount.a30PercentDiscount

class SpecialSKUDiscount : DiscountStrategy {
    override fun applyDiscount(product: Product): BigDecimal {
        return if (product.sku.endsWith(SPECIAL_SKU_SUFFIX)) {
            product.price.multiply(a30PercentDiscount)
        } else {
            product.price
        }
    }
}