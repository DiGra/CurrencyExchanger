package digra.sample.currency.exchanger.data.common

import android.content.Context
import digra.sample.currency.exchanger.core.properties.PropertiesFields
import java.math.BigDecimal
import java.util.Properties

class PropertiesManager(
    context: Context
) : PropertiesFields {

    private val instance: Properties by lazy {
        val inputStream = context.assets.open(CONFIG_FILE)
        val properties = Properties()
        properties.load(inputStream)
        properties
    }

    override val defaultBaseCurrency: String
        get() = getStringValue(DEFAULT_BASE_CURRENCY)
    override val baseCurrencyValue: BigDecimal
        get() = getBigDecimalValue(BASE_CURRENCY_VALUE)
    override val fetchDelayMillis: Long
        get() = getLongValue(FETCH_DELAY_MILLIS)
    override val initialCurrencyToExchange: String
        get() = getStringValue(INITIAL_CURRENCY_TO_EXCHANGE)
    override val baseFeePercent: BigDecimal
        get() = getBigDecimalValue(BASE_FEE_PERCENT)

    private fun getStringValue(key: String, default: String = "") =
        instance.getProperty(key, default)

    private fun getBigDecimalValue(key: String, default: BigDecimal = BigDecimal.ZERO): BigDecimal =
        instance.getProperty(key).toBigDecimalOrNull() ?: default

    private fun getLongValue(key: String, default: Long = 0): Long =
        instance.getProperty(key).toLongOrNull() ?: default

    companion object {
        // Base
        private const val CONFIG_FILE = "config.properties"

        private const val FETCH_DELAY_MILLIS = "fetch.delay.millis"
        private const val DEFAULT_BASE_CURRENCY = "default.base.currency"
        private const val BASE_CURRENCY_VALUE = "base.currency.value"
        private const val INITIAL_CURRENCY_TO_EXCHANGE = "init.currency.to.exchange"
        private const val BASE_FEE_PERCENT = "base.fee.percent"
    }
}
