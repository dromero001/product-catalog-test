package src.main.kotlin.com.productcatalog.application.usecases

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.domain.repository.ProductRepository

@Service
class DefaultGetProductsUseCase(
    private val productRepository: ProductRepository,
    private val applyDiscount: ApplyProductDiscountUseCase
) : GetProductsUseCase {
    override fun execute(category: String?, pageable: Pageable): Page<Pair<Product, ProductPriceAfterDiscount>> {
        val productPage = if (category != null) {
            productRepository.findByCategory(category, pageable)
        } else {
            productRepository.findAll(pageable)
        }
        return productPage.map { product -> product to applyDiscount.execute(product) }
    }
}