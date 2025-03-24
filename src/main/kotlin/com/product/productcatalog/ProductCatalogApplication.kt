package src.main.kotlin.com.product.productcatalog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class ProductCatalogApplication

fun main(args: Array<String>) {
    runApplication<ProductCatalogApplication>(*args)
}
