package digra.sample.currency.exchanger.data.repo

import digra.sample.currency.exchanger.core.model.ExchangeRate
import digra.sample.currency.exchanger.core.model.ExchangeRatesBulk
import digra.sample.currency.exchanger.core.repo.ExchangeRatesRepository
import digra.sample.currency.exchanger.data.api.Api
import digra.sample.currency.exchanger.data.common.mappers.exchangeRateEntityToExchangeRate
import digra.sample.currency.exchanger.data.common.mappers.exchangeRateToExchangeRateEntity
import digra.sample.currency.exchanger.data.db.dao.ExchangeRatesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExchangeRatesRepositoryImpl(
    private val api: Api,
    private val exchangeRatesDao: ExchangeRatesDao
) : ExchangeRatesRepository {

    override suspend fun fetchRates(): ExchangeRatesBulk =
        api.getRates()

    override suspend fun saveRate(rates: ExchangeRate) =
        exchangeRatesDao.insertRate(exchangeRateToExchangeRateEntity(rates))

    override fun loadRates(): Flow<List<ExchangeRate>> =
        exchangeRatesDao.loadExchangeRates().map { exchangeRateEntityToExchangeRate.listMapper(it) }

}
