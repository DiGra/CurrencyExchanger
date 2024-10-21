package digra.sample.currency.exchanger.ui.home.rates

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import digra.sample.currency.exchanger.ui.home.common.ComposableLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Rates(
    viewModel: RatesViewModel = koinViewModel()
) {
    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> viewModel.fetchRatesContinuously()
            Lifecycle.Event.ON_PAUSE -> viewModel.stop()
            else -> Unit
        }
    }
}
