package src.main.kotlin.com.productcatalog.application.usecases

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.model.Product

interface GetProductsUseCase {
    fun execute(category: String?, pageable: Pageable): Page<Pair<Product, ProductPriceAfterDiscount>>
}