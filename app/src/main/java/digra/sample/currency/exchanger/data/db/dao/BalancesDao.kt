package digra.sample.currency.exchanger.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import digra.sample.currency.exchanger.data.db.entity.BalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BalancesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalance(balanceEntity: BalanceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalances(items: List<BalanceEntity>)

    @Query("SELECT * FROM balances ORDER BY priority, value DESC")
    fun loadAllBalances(): Flow<List<BalanceEntity>>
}
