package digra.sample.currency.exchanger.data.api

import digra.sample.currency.exchanger.core.model.ExchangeRatesBulk
import digra.sample.currency.exchanger.data.common.mappers.ratesDtoToExchangeRatesBulk
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class Api(private val client: HttpClient) {

    suspend fun getRates(): ExchangeRatesBulk = ratesDtoToExchangeRatesBulk(client.get(URL).body())

    companion object {
        private const val URL =
            "https://developers.paysera.com/tasks/api/currency-exchange-rates"
    }
}
