package digra.sample.currency.exchanger.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import digra.sample.currency.exchanger.data.db.entity.TransactionHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionHistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transactionHistoryEntity: TransactionHistoryEntity)

    @Query("SELECT * FROM transaction_history")
    fun loadTransactionHistory(): Flow<List<TransactionHistoryEntity>>
}
