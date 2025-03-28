package src.main.kotlin.com.productcatalog.infraestructure.persistence

import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.domain.repository.ProductRepository

@Repository
open class JpaProductRepository(private val entityManager: EntityManager) : ProductRepository {
    override fun findAll(pageable: Pageable): Page<Product> {
        val query = entityManager.createQuery("SELECT p FROM Product p", Product::class.java)
        val total = entityManager.createQuery("SELECT COUNT(p) FROM Product p", Long::class.java).singleResult
        query.firstResult = pageable.pageNumber * pageable.pageSize
        query.maxResults = pageable.pageSize
        return PageImpl(query.resultList, pageable, total)
    }

    override fun findByCategory(category: String, pageable: Pageable): Page<Product> {
        val query = entityManager.createQuery("SELECT p FROM Product p WHERE p.category = :category", Product::class.java)
        query.setParameter("category", category)
        val countQuery = entityManager.createQuery("SELECT COUNT(p) FROM Product p WHERE p.category = :category", Long::class.java)
        countQuery.setParameter("category", category)
        val total = countQuery.singleResult
        query.firstResult = pageable.pageNumber * pageable.pageSize
        query.maxResults = pageable.pageSize
        return PageImpl(query.resultList, pageable, total)
    }
}