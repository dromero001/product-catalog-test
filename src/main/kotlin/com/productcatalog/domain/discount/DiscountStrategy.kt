package src.main.kotlin.com.productcatalog.domain.discount

import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount

interface DiscountStrategy {
    fun applyDiscount(product: Product): ProductPriceAfterDiscount

    fun canApply(product: Product): Boolean
}