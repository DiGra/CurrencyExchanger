package digra.sample.currency.exchanger.data.repo.rules

import digra.sample.currency.exchanger.core.model.CommissionFeeRule
import digra.sample.currency.exchanger.core.prefs.AppStore
import digra.sample.currency.exchanger.core.properties.PropertiesFields
import digra.sample.currency.exchanger.data.db.dao.ExchangeRatesDao
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import java.math.BigDecimal

class RuleLessThan200ForSell(
    priority: Int,
    active: Boolean,
    private val appStore: AppStore,
    private val propertiesFields: PropertiesFields,
    private val exchangeRatesDao: ExchangeRatesDao
) : CommissionFeeRule(priority, active) {

    // Conversion of up to equivalent 200 EUR is free of charge
    override suspend fun calculateFee(sellAmount: BigDecimal, currency: String): BigDecimal =

        if (currency == appStore.baseCurrency.value) {
            checkAmount(sellAmount)
        } else {
            exchangeRatesDao.loadExchangeRates().take(1).single().find { it.toCurrency == currency }
                ?.let {
                    checkAmount(sellAmount / it.rate)
                } ?: run {
                propertiesFields.baseFeePercent
            }
        }

    private fun checkAmount(sellAmount: BigDecimal): BigDecimal =
        if (sellAmount < BigDecimal(200)) {
            BigDecimal.ZERO
        } else {
            propertiesFields.baseFeePercent
        }

}
