package src.main.kotlin.com.productcatalog.application.usecases

import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.model.Product

interface GetProductsUseCase {
    fun execute(
        category: String?,
        sortBy: String = "sku",
        sortOrder: String = "asc"
    ): List<Pair<Product, ProductPriceAfterDiscount>>
}