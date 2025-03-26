package src.main.kotlin.com.productcatalog.configuration

import src.main.kotlin.com.productcatalog.domain.discount.DiscountStrategy
import src.main.kotlin.com.productcatalog.domain.discount.DiscountStrategyApplier
import src.main.kotlin.com.productcatalog.domain.discount.strategies.ElectronicsDiscount
import src.main.kotlin.com.productcatalog.domain.discount.strategies.HomeKitchenDiscount
import src.main.kotlin.com.productcatalog.domain.discount.strategies.SpecialSKUDiscount
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class DiscountConfig {

    @Bean
    open fun discountStrategies(): List<DiscountStrategy> {
        return listOf(
            SpecialSKUDiscount(),
            ElectronicsDiscount(),
            HomeKitchenDiscount()
        )
    }

    @Bean
    open fun discountStrategyApplier(strategies: List<DiscountStrategy>): DiscountStrategyApplier {
        return DiscountStrategyApplier(strategies)
    }
}