package src.test.kotlin.com.productcatalog.infrastructure.persistence

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.persistence.EntityManager
import jakarta.persistence.TypedQuery
import java.math.BigDecimal
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import src.main.kotlin.com.productcatalog.domain.discount.ELECTRONICS_CATEGORY
import src.main.kotlin.com.productcatalog.domain.discount.HOME_KITCHEN_CATEGORY
import src.main.kotlin.com.productcatalog.domain.model.Product
import src.main.kotlin.com.productcatalog.infraestructure.persistence.JpaProductRepository

class JpaProductRepositoryTest {

    private val entityManager = mockk<EntityManager>()
    private val query = mockk<TypedQuery<Product>>(relaxed = true)
    private val countQuery = mockk<TypedQuery<Long>>(relaxed = true)
    private val repository = JpaProductRepository(entityManager)

    @Test
    fun `findAll should return paginated products`() {
        val pageable = PageRequest.of(0, 2)
        val expectedProducts = testProducts.subList(0, 2)

        every { entityManager.createQuery("SELECT p FROM Product p", Product::class.java) } returns query
        every { entityManager.createQuery("SELECT COUNT(p) FROM Product p", Long::class.java) } returns countQuery
        every { query.resultList } returns expectedProducts
        every { countQuery.singleResult } returns testProducts.size.toLong()

        val result = repository.findAll(pageable)

        result shouldBe PageImpl(expectedProducts, pageable, testProducts.size.toLong())
        verify { query.firstResult = 0 }
        verify { query.maxResults = 2 }
    }

    @Test
    fun `findByCategory should return paginated products by category`() {
        val pageable = PageRequest.of(0, 1)
        val electronicsProducts = testProducts.filter { it.category == ELECTRONICS_CATEGORY }
        val expectedProducts = electronicsProducts.subList(0, 1)

        every {
            entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.category = :category",
                Product::class.java
            )
        } returns query
        every {
            entityManager.createQuery(
                "SELECT COUNT(p) FROM Product p WHERE p.category = :category",
                Long::class.java
            )
        } returns countQuery
        every { query.resultList } returns expectedProducts
        every { countQuery.singleResult } returns electronicsProducts.size.toLong()

        val result = repository.findByCategory(ELECTRONICS_CATEGORY, pageable)

        result shouldBe PageImpl(expectedProducts, pageable, electronicsProducts.size.toLong())
        verify { query.setParameter("category", ELECTRONICS_CATEGORY) }
        verify { countQuery.setParameter("category", ELECTRONICS_CATEGORY) }
        verify { query.firstResult = 0 }
        verify { query.maxResults = 1 }
    }

    companion object {
        private val testProducts = listOf(
            Product("SKU0001", BigDecimal("19.99"), "Wireless Mouse", ELECTRONICS_CATEGORY),
            Product("SKU0002", BigDecimal("499.00"), "Smart TV", ELECTRONICS_CATEGORY),
            Product("SKU0003", BigDecimal("29.50"), "Water Bottle", HOME_KITCHEN_CATEGORY)
        )
    }
}