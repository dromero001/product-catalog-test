package src.main.kotlin.com.productcatalog.domain.repository

import src.main.kotlin.com.productcatalog.domain.model.Product

interface ProductRepository {
    fun findAll(): List<Product>
    fun findByCategory(category: String): List<Product>
}