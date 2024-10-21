package digra.sample.currency.exchanger.di

import digra.sample.currency.exchanger.core.interactor.BalancesInteractor
import digra.sample.currency.exchanger.core.interactor.ExchangeRatesInteractor
import digra.sample.currency.exchanger.core.interactor.TransactionInteractor
import org.koin.dsl.module

val coreModule = module {
    factory<BalancesInteractor> { BalancesInteractor(get(), get(), get()) }
    factory<ExchangeRatesInteractor> { ExchangeRatesInteractor(get(), get(), get()) }
    factory<TransactionInteractor> { TransactionInteractor(get(), get(), get()) }
}
