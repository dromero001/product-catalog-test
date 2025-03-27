package src.main.kotlin.com.productcatalog.application.usecases

import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.model.Product

interface ApplyProductDiscountUseCase {
    fun execute(product: Product): ProductPriceAfterDiscount
}