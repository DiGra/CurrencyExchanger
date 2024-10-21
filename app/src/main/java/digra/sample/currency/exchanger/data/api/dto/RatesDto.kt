package digra.sample.currency.exchanger.data.api.dto

import digra.sample.currency.exchanger.data.api.BigDecimalSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class RatesDto(
    @SerialName("base")
    val base: String,
    @SerialName("date")
    val date: String,
    @SerialName("rates")
    val rates: Map<String, @Serializable(with = BigDecimalSerializer::class) BigDecimal>
)
