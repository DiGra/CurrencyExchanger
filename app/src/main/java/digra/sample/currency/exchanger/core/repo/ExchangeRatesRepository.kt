package digra.sample.currency.exchanger.core.repo

import digra.sample.currency.exchanger.core.model.ExchangeRate
import digra.sample.currency.exchanger.core.model.ExchangeRatesBulk
import kotlinx.coroutines.flow.Flow

interface ExchangeRatesRepository {
    suspend fun saveRate(rates: ExchangeRate)
    suspend fun fetchRates(): ExchangeRatesBulk
    fun loadRates(): Flow<List<ExchangeRate>>
}
