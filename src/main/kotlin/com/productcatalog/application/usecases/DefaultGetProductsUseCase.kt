package src.main.kotlin.com.productcatalog.application.usecases

import org.springframework.stereotype.Service
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.domain.repository.ProductRepository

@Service
class DefaultGetProductsUseCase(
    private val productRepository: ProductRepository,
    private val applyDiscount: ApplyProductDiscountUseCase
) : GetProductsUseCase {

    override fun execute(category: String?): List<Pair<Product, ProductPriceAfterDiscount>> {
        return (category?.let { productRepository.findByCategory(it) }
            ?: productRepository.findAll())
            .map { product ->
                product to applyDiscount.execute(product)
            }
    }
}