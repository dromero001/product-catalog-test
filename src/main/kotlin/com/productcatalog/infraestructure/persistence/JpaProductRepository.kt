package src.main.kotlin.com.productcatalog.infraestructure.persistence

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.domain.repository.ProductRepository

@Repository
open class JpaProductRepository(
    private val entityManager: EntityManager
) : ProductRepository {

    override fun findAll(): List<Product> {
        val query = entityManager.createQuery(
            "SELECT p FROM Product p",
            Product::class.java
        )
        return query.resultList
    }

    override fun findByCategory(category: String): List<Product> {
        val query = entityManager.createQuery(
            "SELECT p FROM Product p WHERE p.category = :category",
            Product::class.java
        )
        query.setParameter("category", category)
        return query.resultList
    }
}