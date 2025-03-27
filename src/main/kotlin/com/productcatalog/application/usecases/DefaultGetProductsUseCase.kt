package src.main.kotlin.com.productcatalog.application.usecases

import org.springframework.stereotype.Service
import src.main.kotlin.com.productcatalog.domain.ProductPriceAfterDiscount
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.domain.repository.ProductRepository

@Service
class DefaultGetProductsUseCase(
    private val productRepository: ProductRepository,
    private val applyDiscount: ApplyProductDiscountUseCase,
) : GetProductsUseCase {

    override fun execute(
        category: String?,
        sortBy: String,
        sortOrder: String,
    ): List<Pair<Product, ProductPriceAfterDiscount>> {
        val products = category?.let { productRepository.findByCategory(it) } ?: productRepository.findAll()

        return products.sortedWith(getComparator(sortBy, sortOrder))
            .map { product ->
                product to applyDiscount.execute(product)
            }
    }

    private fun getComparator(sortBy: String, sortOrder: String): Comparator<Product> {
        val comparator = when (sortBy.lowercase()) {
            "price" -> compareBy<Product> { it.price }
            "description" -> compareBy { it.description }
            "category" -> compareBy { it.category }
            else -> compareBy { it.sku }
        }

        return if (sortOrder.equals("desc", ignoreCase = true)) {
            comparator.reversed()
        } else {
            comparator
        }
    }
}