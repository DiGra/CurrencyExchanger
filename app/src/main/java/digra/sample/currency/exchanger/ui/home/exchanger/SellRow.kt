package digra.sample.currency.exchanger.ui.home.exchanger

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import digra.sample.currency.exchanger.R
import digra.sample.currency.exchanger.core.model.Balance
import kotlinx.coroutines.delay
import java.math.BigDecimal

@Composable
fun SellRow(
    viewModel: ExchangerViewModel,
    fromBalance: Balance?
) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val pattern = remember { Regex("[\\d,]*[.]?[\\d,]*") }
        var sellValue by remember {
            mutableStateOf(
                TextFieldValue(
                    "100",
                    selection = TextRange(start = 0, end = 3)
                )
            )
        }
        val focusRequester = remember { FocusRequester() }
        Image(
            painterResource(R.drawable.arrow_up),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.sell),
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            textAlign = TextAlign.Start
        )
        BasicTextField(
            value = sellValue,
            modifier = Modifier
                .focusRequester(focusRequester)
                .width(80.dp)
                .padding(8.dp),
            onValueChange = {
                if (it.text.isNotEmpty() && it.text.matches(pattern)) {
                    sellValue = it
                    it.text.toBigDecimalOrNull()?.let {
                        viewModel.setSellAmount(it)
                        viewModel.calculate()
                    }
                } else {
                    sellValue = it
                    viewModel.setSellAmount(BigDecimal.ZERO)
                    viewModel.calculate()
                }
            },
            cursorBrush = SolidColor(LocalContentColor.current),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.End,
                color = LocalContentColor.current
            ),
            singleLine = true,
        )
        LaunchedEffect(Unit) {
            delay(200)
            focusRequester.requestFocus()
        }
        CurrencyPicker(viewModel, true, fromBalance?.currency)
    }
}
