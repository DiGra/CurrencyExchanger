package digra.sample.currency.exchanger.ui.home.exchanger

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import digra.sample.currency.exchanger.R
import digra.sample.currency.exchanger.core.model.ResultState
import digra.sample.currency.exchanger.ui.theme.Blue
import digra.sample.currency.exchanger.ui.theme.White
import org.koin.compose.viewmodel.koinViewModel

@Preview(showBackground = true)
@Composable
fun Exchanger(
    viewModel: ExchangerViewModel = koinViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val calcResult = viewModel.getCalculationResult().observeAsState().value
        val fromBalance = viewModel.getFromBalance().observeAsState().value
        val toBalance = viewModel.getToBalance().observeAsState().value
        TransactionDialog(viewModel)
        SellRow(viewModel, fromBalance)
        HorizontalDivider(
            modifier = Modifier.padding(start = 60.dp, end = 16.dp),
            color = Color(0x663D3D3D)
        )
        ReceiveRow(viewModel, toBalance, calcResult)
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .padding(8.dp)
                .defaultMinSize(minWidth = 200.dp),
            border = null,

            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Blue
            ),
            enabled = (calcResult?.resultState == ResultState.VALID_TRANSACTION),
            onClick = {
                viewModel.startExchangeTransaction()
            }
        ) {
            Text(
                text = stringResource(R.string.submit),
                color = White
            )
        }
    }
}
