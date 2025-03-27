package src.test.kotlin.com.productcatalog

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import src.main.kotlin.com.productcatalog.ProductCatalogApplication

@SpringBootTest(classes = [ProductCatalogApplication::class])
class ProductCatalogApplicationTests {

    @Test
    fun contextLoads() {
    }
}