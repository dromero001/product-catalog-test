package src.main.kotlin.com.productcatalog.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class OpenApiConfig {

    @Bean
    open fun productCatalogOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Product Catalog API")
                    .description("Products API to manage products and discounts")
                    .version("1.0.0")
                    .license(License().name("Apache 2.0"))
            )
    }
}