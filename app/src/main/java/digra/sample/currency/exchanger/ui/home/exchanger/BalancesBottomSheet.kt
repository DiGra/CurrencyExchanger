package digra.sample.currency.exchanger.ui.home.exchanger

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import digra.sample.currency.exchanger.ui.home.balances.BalancesViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalancesBottomSheet(
    viewModel: ExchangerViewModel,
    isFromPicker: Boolean,
    currency: String?,
    balancesViewModel: BalancesViewModel = koinViewModel(),
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = { onDismiss() }
    ) {
        val balances = balancesViewModel.loadBalances().value?.filter {
            it.currency != currency
        }
        balances?.let {
            LazyColumn {
                items(it) { balance ->
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable {
                                if (isFromPicker) {
                                    viewModel.setFromBalance(balance)
                                } else {
                                    viewModel.setToBalance(balance)
                                }
                                onDismiss()
                            },
                        text = balance.currency
                    )
                }
            }
        }
    }
}
