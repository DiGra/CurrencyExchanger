package digra.sample.currency.exchanger.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import digra.sample.currency.exchanger.data.db.dao.BalancesDao
import digra.sample.currency.exchanger.data.db.dao.ExchangeRatesDao
import digra.sample.currency.exchanger.data.db.dao.TransactionHistoryDao
import digra.sample.currency.exchanger.data.db.entity.BalanceEntity
import digra.sample.currency.exchanger.data.db.entity.ExchangeRateEntity
import digra.sample.currency.exchanger.data.db.entity.TransactionHistoryEntity

@Database(
    entities = [
        BalanceEntity::class,
        ExchangeRateEntity::class,
        TransactionHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ExchangerDatabase : RoomDatabase() {
    abstract fun balancesDao(): BalancesDao
    abstract fun exchangeRatesDao(): ExchangeRatesDao
    abstract fun transactionHistoryDao(): TransactionHistoryDao
}
