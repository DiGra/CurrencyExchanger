package digra.sample.currency.exchanger.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import digra.sample.currency.exchanger.R
import digra.sample.currency.exchanger.ui.home.balances.Balances
import digra.sample.currency.exchanger.ui.home.balances.BalancesViewModel
import digra.sample.currency.exchanger.ui.home.exchanger.Exchanger
import digra.sample.currency.exchanger.ui.home.rates.Rates
import digra.sample.currency.exchanger.ui.theme.TitleStyle
import org.koin.compose.viewmodel.koinViewModel

@Preview(showBackground = true)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: BalancesViewModel = koinViewModel()
) {
    Rates()
    Column(modifier = modifier.imePadding()) {
        TitleText(text = stringResource(id = R.string.my_balances_title))
        Balances(viewModel)
        TitleText(text = stringResource(id = R.string.currency_exchange_title))
        val balances = viewModel.loadBalances().observeAsState()
        balances.value?.let {
            if (it.size > 2) {
                Exchanger()
            }
        }
    }
}

@Composable
fun TitleText(text: String) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = text.uppercase(),
        style = TitleStyle,
    )
}
