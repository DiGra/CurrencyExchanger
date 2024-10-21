package digra.sample.currency.exchanger.data.db

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.time.LocalDate

class Converters {

    @TypeConverter
    fun localDateTimeToString(value: LocalDate?): String? = value?.toString()

    @TypeConverter
    fun stringToLocalDateTime(value: String?): LocalDate? = value?.let {
        LocalDate.parse(value)
    }

    @TypeConverter
    fun bigDecimalToString(input: BigDecimal?): String {
        return input?.toPlainString() ?: ""
    }

    @TypeConverter
    fun stringToBigDecimal(input: String?): BigDecimal {
        if (input.isNullOrBlank()) return BigDecimal.valueOf(0.0)
        return input.toBigDecimalOrNull() ?: BigDecimal.valueOf(0.0)
    }
}
