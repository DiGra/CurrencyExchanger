package digra.sample.currency.exchanger.ui.home.exchanger

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import digra.sample.currency.exchanger.R
import digra.sample.currency.exchanger.core.model.Balance
import digra.sample.currency.exchanger.core.model.CalculationResult
import digra.sample.currency.exchanger.ui.theme.Green

@Composable
fun ReceiveRow(
    viewModel: ExchangerViewModel,
    toBalance: Balance?,
    calculationResult: CalculationResult?
) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(R.drawable.arrow_down),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.receive),
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            textAlign = TextAlign.Start
        )
        Text(
            text = "%.2f".format(calculationResult?.receiveAmount),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            textAlign = TextAlign.Center,
            color = Green
        )
        CurrencyPicker(viewModel, false, toBalance?.currency)
    }
}
