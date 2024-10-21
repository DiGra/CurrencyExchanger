package digra.sample.currency.exchanger.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import digra.sample.currency.exchanger.R
import digra.sample.currency.exchanger.ui.home.HomePage
import digra.sample.currency.exchanger.ui.theme.AppBarStyle
import digra.sample.currency.exchanger.ui.theme.Blue
import digra.sample.currency.exchanger.ui.theme.CurrencyExchangerTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyExchangerTheme {
                Scaffold(
                    topBar = { AppBar() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    HomePage(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxHeight())
                }
            }
        }
    }
}

@Composable
fun AppBar() {
    @OptIn(ExperimentalMaterial3Api::class)
    KoinContext {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Blue
            ),
            title = {
                Text(
                    style = AppBarStyle,
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.currency_converter)
                )
            }
        )
    }
}
