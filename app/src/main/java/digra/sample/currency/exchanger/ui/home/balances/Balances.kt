package digra.sample.currency.exchanger.ui.home.balances

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import digra.sample.currency.exchanger.ui.theme.BalanceItemStyle

@Composable
fun Balances(
    viewModel: BalancesViewModel
) {
    viewModel.fillDummyData()
    val balancesList = viewModel.loadBalances().observeAsState(listOf()).value
    if (balancesList.isEmpty()) {
        LoadingIndicator()
    } else {
        Column {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                modifier = Modifier.height(40.dp)
            ) {
                items(balancesList) { balance ->
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .wrapContentHeight(),
                        textAlign = TextAlign.Center,
                        text = "%.2f ${balance.currency}".format(balance.value),
                        style = BalanceItemStyle
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingIndicator() {
    LinearProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}
