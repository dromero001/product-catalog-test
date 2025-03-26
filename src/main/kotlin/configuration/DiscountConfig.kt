package src.main.kotlin.configuration

import src.main.kotlin.domain.discount.DiscountStrategy
import src.main.kotlin.domain.discount.DiscountStrategyApplier
import src.main.kotlin.domain.discount.strategies.ElectronicsDiscount
import src.main.kotlin.domain.discount.strategies.HomeKitchenDiscount
import src.main.kotlin.domain.discount.strategies.SpecialSKUDiscount
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