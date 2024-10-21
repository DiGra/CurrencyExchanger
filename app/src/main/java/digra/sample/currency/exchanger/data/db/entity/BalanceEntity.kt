package digra.sample.currency.exchanger.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.math.BigDecimal

@Entity(tableName = "balances", primaryKeys = ["id"])
data class BalanceEntity(
    @ColumnInfo(name = "id")
    val balanceId: String,
    @ColumnInfo(name = "value")
    val value: BigDecimal,
    @ColumnInfo(name = "currency")
    val currency: String,
    @ColumnInfo(name = "priority")
    val priority: Int,
)
