package src.main.kotlin.com.productcatalog.domain.discount.strategies

import java.math.BigDecimal.ZERO
import java.math.RoundingMode.HALF_UP
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.discount.DiscountStrategy
import src.main.kotlin.com.productcatalog.domain.discount.ELECTRONICS_CATEGORY
import src.main.kotlin.com.productcatalog.domain.discount.a15PercentDiscount
import src.main.kotlin.com.productcatalog.domain.discount.a15PercentDiscountMultiplier

class ElectronicsDiscount : DiscountStrategy {
    override fun applyDiscount(product: Product): ProductPriceAfterDiscount {
        return if (product.category == ELECTRONICS_CATEGORY) {
            ProductPriceAfterDiscount(
                originalPrice = product.price,
                discountPercentage = a15PercentDiscount,
                finalPrice = product.price.multiply(a15PercentDiscountMultiplier)
                    .setScale(2, HALF_UP),
            )
        } else {
            ProductPriceAfterDiscount(
                originalPrice = product.price,
                discountPercentage = ZERO,
                finalPrice = product.price.setScale(2, HALF_UP)
            )
        }
    }
}