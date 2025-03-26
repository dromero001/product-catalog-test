package src.main.kotlin.com.productcatalog.domain.discount.strategies

import java.math.BigDecimal.ZERO
import java.math.RoundingMode.HALF_UP
import src.main.kotlin.com.productcatalog.domain.Product
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.discount.DiscountStrategy
import src.main.kotlin.com.productcatalog.domain.discount.HOME_KITCHEN_CATEGORY
import src.main.kotlin.com.productcatalog.domain.discount.a25PercentDiscount
import src.main.kotlin.com.productcatalog.domain.discount.a25PercentDiscountMultiplier

class HomeKitchenDiscount : DiscountStrategy {
    override fun applyDiscount(product: Product): ProductPriceAfterDiscount {
        return if (product.category == HOME_KITCHEN_CATEGORY) {
            ProductPriceAfterDiscount(
                originalPrice = product.price,
                discountPercentage = a25PercentDiscount,
                finalPrice = product.price.multiply(a25PercentDiscountMultiplier)
                    .setScale(2, HALF_UP),
            )
        } else {
            ProductPriceAfterDiscount(
                originalPrice = product.price,
                discountPercentage = ZERO,
                finalPrice = product.price,
            )
        }
    }
}