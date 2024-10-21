package digra.sample.currency.exchanger.ui.home.exchanger

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import digra.sample.currency.exchanger.R
import digra.sample.currency.exchanger.ui.theme.Blue

@Composable
fun TransactionDialog(
    viewModel: ExchangerViewModel
) {

    val state = viewModel.dialogState.collectAsStateWithLifecycle()
    if (state.value.showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onDismissTransactionDialog() },
            title = {
                Text(
                    text = stringResource(R.string.currency_converted_dialog_title),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = stringResource(
                        R.string.currency_converted_dialog_text,
                        "%.2f".format(state.value.transactionResult.sellAmount),
                        state.value.transactionResult.sellCurrency,
                        "%.2f".format(state.value.transactionResult.receiveAmount),
                        state.value.transactionResult.receiveCurrency,
                        "%.2f".format(state.value.transactionResult.commissionFee),
                        state.value.transactionResult.sellCurrency
                    ),
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = Blue
                    ),
                    onClick = {
                        viewModel.onDismissTransactionDialog()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.done),
                        color = Color.White
                    )
                }
            }

        )
    }
}
