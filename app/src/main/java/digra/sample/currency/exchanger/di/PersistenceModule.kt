package digra.sample.currency.exchanger.di

import android.app.Application
import androidx.room.Room
import digra.sample.currency.exchanger.data.db.ExchangerDatabase
import org.koin.dsl.module

val persistenceModule = module {
    single { provideDataBase(get()) }
    single { provideBalancesDao(get()) }
    single { provideExchangeRatesDao(get()) }
    single { provideTransactionHistoryDao(get()) }
}

fun provideDataBase(app: Application): ExchangerDatabase =
    Room.databaseBuilder(
        app,
        ExchangerDatabase::class.java,
        "exchanger.db"
    ).fallbackToDestructiveMigration().build()

fun provideBalancesDao(db: ExchangerDatabase) = db.balancesDao()
fun provideExchangeRatesDao(db: ExchangerDatabase) = db.exchangeRatesDao()
fun provideTransactionHistoryDao(db: ExchangerDatabase) = db.transactionHistoryDao()
