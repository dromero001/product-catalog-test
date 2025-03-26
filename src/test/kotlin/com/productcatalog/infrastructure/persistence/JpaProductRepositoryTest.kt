package src.test.kotlin.com.productcatalog.infrastructure.persistence

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.persistence.EntityManager
import jakarta.persistence.TypedQuery
import java.math.BigDecimal
import org.junit.jupiter.api.Test
import src.main.kotlin.com.productcatalog.domain.discount.ELECTRONICS_CATEGORY
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.infraestructure.persistence.JpaProductRepository

class JpaProductRepositoryTest {

    private val entityManager = mockk<EntityManager>()
    private val query = mockk<TypedQuery<Product>>()
    private val repository = JpaProductRepository(entityManager)

    @Test
    fun `findAll should return all products`() {
        every { entityManager.createQuery("SELECT p FROM Product p", Product::class.java) } returns query
        every { query.resultList } returns testProducts

        val result = repository.findAll()

        result.size shouldBe 3
        result shouldBe testProducts
        verify { entityManager.createQuery("SELECT p FROM Product p", Product::class.java) }
    }

    @Test
    fun `findByCategory should return only products from specified category`() {
        val electronicsProducts = testProducts.filter { it.category == ELECTRONICS_CATEGORY }
        every {
            entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.category = :category",
                Product::class.java
            )
        } returns query
        every { query.setParameter("category", ELECTRONICS_CATEGORY) } returns query
        every { query.resultList } returns electronicsProducts

        val result = repository.findByCategory(ELECTRONICS_CATEGORY)

        result.size shouldBe 2
        result shouldBe electronicsProducts
        verify {
            entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.category = :category",
                Product::class.java
            )
        }
        verify { query.setParameter("category", ELECTRONICS_CATEGORY) }
    }

    companion object {
        private val testProducts = listOf(
            Product("SKU0001", BigDecimal("19.99"), "Wireless Mouse with ergonomic design", "Electronics"),
            Product("SKU0002", BigDecimal("499.00"), "4K Ultra HD Smart TV, 55 inches", "Electronics"),
            Product("SKU0003", BigDecimal("29.50"), "Stainless Steel Water Bottle, 1L", "Home & Kitchen")
        )
    }
}