package src.main.kotlin.com.productcatalog.application.usecases

import org.springframework.stereotype.Service
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.discount.DiscountStrategyApplier

@Service
class DefaultApplyProductDiscountUseCase(
    private val discountStrategyApplier: DiscountStrategyApplier
) : ApplyProductDiscountUseCase {
    override fun execute(product: Product): ProductPriceAfterDiscount {
        return discountStrategyApplier.applyBiggerDiscount(product)
    }
}