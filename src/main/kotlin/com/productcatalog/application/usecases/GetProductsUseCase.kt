package src.main.kotlin.com.productcatalog.application.usecases

import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.model.Product

interface GetProductsUseCase {
    fun execute(category: String?): List<Pair<Product, ProductPriceAfterDiscount>>
}