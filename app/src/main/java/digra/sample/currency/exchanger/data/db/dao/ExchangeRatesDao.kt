package digra.sample.currency.exchanger.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import digra.sample.currency.exchanger.data.db.entity.ExchangeRateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeRatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRate(exchangeRateEntity: ExchangeRateEntity)

    @Query("SELECT * FROM rates")
    fun loadExchangeRates(): Flow<List<ExchangeRateEntity>>
}
