package digra.sample.currency.exchanger.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.math.BigDecimal
import java.time.LocalDate

@Entity(tableName = "rates", primaryKeys = ["toCurrency"])
data class ExchangeRateEntity(
    @ColumnInfo(name = "baseCurrency")
    val baseCurrency: String,
    @ColumnInfo(name = "toCurrency")
    val toCurrency: String,
    @ColumnInfo(name = "rate")
    val rate: BigDecimal,
    @ColumnInfo(name = "date")
    val date: LocalDate
)
