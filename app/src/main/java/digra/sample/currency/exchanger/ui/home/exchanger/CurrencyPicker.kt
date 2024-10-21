package digra.sample.currency.exchanger.ui.home.exchanger

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import digra.sample.currency.exchanger.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyPicker(
    viewModel: ExchangerViewModel,
    isFromPicker: Boolean,
    currency: String?
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(start = 0.dp, end = 16.dp)
            .clickable {
                showBottomSheet = true
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currency ?: ""
        )
        Image(
            painterResource(R.drawable.picker_arrow_down),
            contentDescription = null,
            colorFilter = ColorFilter.tint(LocalContentColor.current)
        )
    }

    if (showBottomSheet) {
        BalancesBottomSheet(viewModel, isFromPicker, currency) {
            showBottomSheet = false
        }
    }
}
