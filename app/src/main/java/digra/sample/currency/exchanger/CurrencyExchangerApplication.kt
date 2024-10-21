package digra.sample.currency.exchanger

import android.app.Application
import digra.sample.currency.exchanger.di.coreModule
import digra.sample.currency.exchanger.di.dataModule
import digra.sample.currency.exchanger.di.networkModule
import digra.sample.currency.exchanger.di.persistenceModule
import digra.sample.currency.exchanger.di.rulesModule
import digra.sample.currency.exchanger.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androix.startup.KoinStartup.onKoinStartup

class CurrencyExchangerApplication : Application() {
    init {
        @Suppress("OPT_IN_USAGE")
        onKoinStartup {
            androidLogger()
            androidContext(applicationContext)
            modules(
                coreModule,
                dataModule,
                networkModule,
                persistenceModule,
                viewModelModule,
                rulesModule
            )
        }
    }
}
