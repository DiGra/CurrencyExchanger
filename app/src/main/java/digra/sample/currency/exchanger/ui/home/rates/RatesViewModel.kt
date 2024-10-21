package digra.sample.currency.exchanger.ui.home.rates

import digra.sample.currency.exchanger.core.interactor.ExchangeRatesInteractor
import digra.sample.currency.exchanger.ui.common.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class RatesViewModel(
    private val exchangeRatesInteractor: ExchangeRatesInteractor
) : BaseViewModel() {

    private val coroutineContext = Executors.newCachedThreadPool().asCoroutineDispatcher()
    private val scope = CoroutineScope(coroutineContext)

    fun fetchRatesContinuously() {
        scope.launch {
            exchangeRatesInteractor.fetchRatesContinuously()
        }
    }

    fun stop() {
        scope.cancel()
    }
}
