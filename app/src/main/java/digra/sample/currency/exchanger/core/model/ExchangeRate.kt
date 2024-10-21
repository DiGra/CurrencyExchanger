package digra.sample.currency.exchanger.core.model

import java.math.BigDecimal
import java.time.LocalDate

data class ExchangeRate(
    val baseCurrency: String,
    val toCurrency: String,
    val date: LocalDate,
    val rate: BigDecimal
)
