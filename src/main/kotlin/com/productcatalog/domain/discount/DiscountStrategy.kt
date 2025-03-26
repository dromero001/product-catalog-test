package src.main.kotlin.com.productcatalog.domain.discount

import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount

fun interface DiscountStrategy {
    fun applyDiscount(product: Product): ProductPriceAfterDiscount
}