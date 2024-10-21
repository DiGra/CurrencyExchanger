package digra.sample.currency.exchanger.core.interactor

import digra.sample.currency.exchanger.core.model.Balance
import digra.sample.currency.exchanger.core.model.ExchangeRate
import digra.sample.currency.exchanger.core.model.ExchangeRatesBulk
import digra.sample.currency.exchanger.core.properties.PropertiesFields
import digra.sample.currency.exchanger.core.repo.BalancesRepository
import digra.sample.currency.exchanger.core.repo.ExchangeRatesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import java.math.BigDecimal
import java.util.UUID

class ExchangeRatesInteractor(
    private val balancesRepository: BalancesRepository,
    private val exchangeRatesRepository: ExchangeRatesRepository,
    private val propertiesFields: PropertiesFields
) {

    /* Fetching rates continuously depending on the delay specified in the config.properties
        1 - Save fetched rates to persistent
        2 - Populate new balances if not exists
     */
    suspend fun fetchRatesContinuously() {
        while (true) {
            exchangeRatesRepository.fetchRates().let { exchangeRates ->
                saveRates(exchangeRates)
                populateBalancesIfNotExists(exchangeRates)
            }
            delay(propertiesFields.fetchDelayMillis)
        }
    }

    // Insert rates to persistent. Data replaces on conflict
    private suspend fun saveRates(exchangeRatesBulk: ExchangeRatesBulk) {
        exchangeRatesBulk.rates.forEach { rates ->
            exchangeRatesRepository.saveRate(
                ExchangeRate(
                    baseCurrency = exchangeRatesBulk.base,
                    toCurrency = rates.key,
                    date = exchangeRatesBulk.date,
                    rate = rates.value
                )
            )
        }
    }

    // Adding new balances with low priority if not exist according to date fetched from rates
    private suspend fun populateBalancesIfNotExists(exchangeRatesBulk: ExchangeRatesBulk) {
        val persistBalances = balancesRepository.loadBalances().take(1).single()
        val newBalances = mutableListOf<Balance>()
        exchangeRatesBulk.rates.map { rate ->
            persistBalances.find { it.currency == rate.key } ?: run {
                newBalances.add(
                    Balance(
                        balanceId = UUID.randomUUID().toString(),
                        value = BigDecimal.ZERO,
                        currency = rate.key,
                        orderPriority = 100
                    )
                )
            }
        }
        balancesRepository.updateBalances(newBalances)
    }

    fun loadExchangeRates(): Flow<List<ExchangeRate>> =
        exchangeRatesRepository.loadRates()
}
