package digra.sample.currency.exchanger.core.model

import java.math.BigDecimal
import java.time.LocalDate

data class ExchangeRatesBulk(
    val base: String,
    val date: LocalDate,
    val rates: Map<String, BigDecimal>
)
