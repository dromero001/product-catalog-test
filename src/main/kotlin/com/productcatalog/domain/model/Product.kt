package src.main.kotlin.com.productcatalog.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

@Entity
@Table(name = "product")
data class Product(
    @Id
    val sku: String = "",
    val price: BigDecimal = ZERO,
    val description: String = "",
    val category: String = "",
)