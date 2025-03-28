package src.main.kotlin.com.productcatalog.domain.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import src.main.kotlin.com.productcatalog.domain.model.Product

interface ProductRepository {
    fun findAll(pageable: Pageable): Page<Product>
    fun findByCategory(category: String, pageable: Pageable): Page<Product>
}