package src.main.kotlin.com.productcatalog.application.usecases

import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.discount.DiscountStrategyApplier

class ApplyProductDiscountUseCase(
    private val discountStrategyApplier: DiscountStrategyApplier
) {
    fun execute(product: Product): ProductPriceAfterDiscount {
        return discountStrategyApplier.applyBiggerDiscount(product)
    }
}