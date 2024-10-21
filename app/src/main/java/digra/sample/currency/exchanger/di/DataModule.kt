package digra.sample.currency.exchanger.di

import digra.sample.currency.exchanger.core.prefs.AppStore
import digra.sample.currency.exchanger.core.properties.PropertiesFields
import digra.sample.currency.exchanger.core.repo.BalancesRepository
import digra.sample.currency.exchanger.core.repo.ExchangeRatesRepository
import digra.sample.currency.exchanger.core.repo.RulesRepository
import digra.sample.currency.exchanger.core.repo.TransactionHistoryRepository
import digra.sample.currency.exchanger.data.common.PropertiesManager
import digra.sample.currency.exchanger.data.prefs.AppPrefsStore
import digra.sample.currency.exchanger.data.repo.BalancesRepositoryImpl
import digra.sample.currency.exchanger.data.repo.ExchangeRatesRepositoryImpl
import digra.sample.currency.exchanger.data.repo.RulesRepositoryImpl
import digra.sample.currency.exchanger.data.repo.TransactionHistoryRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    factory<PropertiesFields> { PropertiesManager(androidContext()) }
    factory<AppStore> { AppPrefsStore(androidContext(), get()) }

    single<ExchangeRatesRepository> { ExchangeRatesRepositoryImpl(get(), get()) }
    single<BalancesRepository> { BalancesRepositoryImpl(get()) }
    single<RulesRepository> { RulesRepositoryImpl(get(), get(), get()) }
    single<TransactionHistoryRepository> { TransactionHistoryRepositoryImpl(get()) }
}
