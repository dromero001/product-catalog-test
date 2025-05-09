package src.main.kotlin.com.productcatalog.domain.discount

import java.math.BigDecimal.ZERO
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount

class DiscountStrategyApplier(private val strategies: List<DiscountStrategy>) {

    fun applyBiggerDiscount(product: Product): ProductPriceAfterDiscount {
        return strategies
            .filter { it.canApply(product) }
            .minByOrNull { it.applyDiscount(product).finalPrice }?.applyDiscount(product)
            ?: ProductPriceAfterDiscount(
                originalPrice = product.price,
                discountPercentage = ZERO,
                finalPrice = product.price,
            )
    }
}