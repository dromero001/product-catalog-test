package src.main.kotlin.com.productcatalog.domain.discount.strategies

import java.math.RoundingMode
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.discount.DiscountStrategy
import src.main.kotlin.com.productcatalog.domain.discount.SPECIAL_SKU_SUFFIX
import src.main.kotlin.com.productcatalog.domain.discount.a30PercentDiscount
import src.main.kotlin.com.productcatalog.domain.discount.a30PercentDiscountMultiplier
import src.main.kotlin.com.productcatalog.domain.model.Product

class SpecialSKUDiscount : DiscountStrategy {

    override fun canApply(product: Product): Boolean = product.sku.endsWith(SPECIAL_SKU_SUFFIX)

    override fun applyDiscount(product: Product): ProductPriceAfterDiscount =
        ProductPriceAfterDiscount(
            originalPrice = product.price,
            discountPercentage = a30PercentDiscount,
            finalPrice = product.price.multiply(a30PercentDiscountMultiplier)
                .setScale(2, RoundingMode.HALF_UP),
        )
}