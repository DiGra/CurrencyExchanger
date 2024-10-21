package digra.sample.currency.exchanger.core.properties

import java.math.BigDecimal

interface PropertiesFields {
    val defaultBaseCurrency: String
    val baseCurrencyValue: BigDecimal
    val fetchDelayMillis: Long
    val initialCurrencyToExchange: String
    val baseFeePercent: BigDecimal
}
