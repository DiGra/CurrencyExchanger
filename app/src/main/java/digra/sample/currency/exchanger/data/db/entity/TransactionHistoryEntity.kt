package digra.sample.currency.exchanger.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.math.BigDecimal
import java.time.LocalDate

@Entity(tableName = "transaction_history", primaryKeys = ["id"])
data class TransactionHistoryEntity(
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "fromBalanceId")
    val fromBalanceId: String,
    @ColumnInfo(name = "fromCurrency")
    val fromCurrency: String,
    @ColumnInfo(name = "fromValueBefore")
    val fromValueBefore: BigDecimal,
    @ColumnInfo(name = "fromValueAfter")
    val fromValueAfter: BigDecimal,
    @ColumnInfo(name = "sellValue")
    val sellValue: BigDecimal,
    @ColumnInfo(name = "toBalanceId")
    val toBalanceId: String,
    @ColumnInfo(name = "toCurrency")
    val toCurrency: String,
    @ColumnInfo(name = "toValueBefore")
    val toValueBefore: BigDecimal,
    @ColumnInfo(name = "toValueAfter")
    val toValueAfter: BigDecimal,
    @ColumnInfo(name = "commissionFee")
    val commissionFee: BigDecimal,
    @ColumnInfo(name = "commissionAmount")
    val commissionFeeAmount: BigDecimal,
    @ColumnInfo(name = "date")
    val date: LocalDate
)
