package digra.sample.currency.exchanger.di

import digra.sample.currency.exchanger.ui.home.balances.BalancesViewModel
import digra.sample.currency.exchanger.ui.home.exchanger.ExchangerViewModel
import digra.sample.currency.exchanger.ui.home.rates.RatesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::BalancesViewModel)
    viewModelOf(::ExchangerViewModel)
    viewModelOf(::RatesViewModel)
}
