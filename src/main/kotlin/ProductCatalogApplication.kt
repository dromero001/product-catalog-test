package src.main.kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class ProductCatalogApplication

fun main(args: Array<String>) {
    runApplication<ProductCatalogApplication>(*args)
}
