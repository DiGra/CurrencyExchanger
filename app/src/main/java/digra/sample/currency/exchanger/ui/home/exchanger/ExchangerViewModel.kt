package digra.sample.currency.exchanger.ui.home.exchanger

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import digra.sample.currency.exchanger.core.interactor.BalancesInteractor
import digra.sample.currency.exchanger.core.interactor.ExchangeRatesInteractor
import digra.sample.currency.exchanger.core.interactor.TransactionInteractor
import digra.sample.currency.exchanger.core.model.Balance
import digra.sample.currency.exchanger.core.model.CalculationResult
import digra.sample.currency.exchanger.core.model.SubmissionResult
import digra.sample.currency.exchanger.core.model.TransactionSubmissionData
import digra.sample.currency.exchanger.core.model.TransactionSubmissionResult
import digra.sample.currency.exchanger.core.prefs.AppStore
import digra.sample.currency.exchanger.core.properties.PropertiesFields
import digra.sample.currency.exchanger.ui.common.model.TransactionState
import digra.sample.currency.exchanger.ui.common.viewmodel.BaseViewModel
import digra.sample.currency.exchanger.util.safeLet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal

class ExchangerViewModel(
    private val propertiesFields: PropertiesFields,
    private val appStore: AppStore,
    private val balancesInteractor: BalancesInteractor,
    private val exchangeRatesInteractor: ExchangeRatesInteractor,
    private val transactionInteractor: TransactionInteractor
) : BaseViewModel() {

    init {
        initPopulate()
    }

    private val transactionState = MutableStateFlow<TransactionState>(
        TransactionState(
            showDialog = false,
            transactionResult = TransactionSubmissionResult(
                BigDecimal.ZERO,
                "",
                BigDecimal.ZERO,
                "",
                BigDecimal.ZERO
            )
        )
    )
    val dialogState: StateFlow<TransactionState>
        get() = transactionState.asStateFlow()

    private val fromBalance = MutableLiveData<Balance>()
    fun getFromBalance() = fromBalance

    fun setFromBalance(balance: Balance) {
        toBalance.value?.let {
            if (it.balanceId == balance.balanceId) {
                toBalance.value = fromBalance.value
            }
        }
        fromBalance.value = balance
        calculate()
    }

    private val toBalance = MutableLiveData<Balance>()
    fun getToBalance() = toBalance

    fun setToBalance(balance: Balance) {
        fromBalance.value?.let {
            if (it.balanceId == balance.balanceId) {
                fromBalance.value = toBalance.value
            }
        }
        toBalance.value = balance
        calculate()
    }

    private val sellAmount = MutableLiveData<BigDecimal>()
    fun setSellAmount(value: BigDecimal) {
        sellAmount.value = value
    }

    private val calculationResult = MutableLiveData<CalculationResult>()
    fun getCalculationResult() = calculationResult

    fun initPopulate() {
        viewModelScope.launch {
            balancesInteractor.loadBalances().take(1).single().map {
                when (it.currency) {
                    appStore.baseCurrency.value -> fromBalance.value = it
                    propertiesFields.initialCurrencyToExchange -> toBalance.value = it
                }
            }
            sellAmount.value = BigDecimal(100)
            calculate()
        }
    }

    fun calculate() {
        safeLet(
            fromBalance.value,
            toBalance.value,
            sellAmount.value
        ) { from, to, amount ->
            viewModelScope.launch {
                exchangeRatesInteractor.loadExchangeRates().take(1).single().let { rates ->
                    transactionInteractor.calculate(
                        appStore.baseCurrency.value,
                        from,
                        to,
                        amount,
                        rates,
                        transactionInteractor.loadRules(),
                        propertiesFields.baseFeePercent
                    ).let {
                        calculationResult.value = it
                    }
                }
            }
        }
    }

    fun startExchangeTransaction() {
        safeLet(
            fromBalance.value,
            toBalance.value,
            sellAmount.value,
            calculationResult.value
        ) { from, to, amount, calcResult ->
            viewModelScope.launch {
                when (val result = transactionInteractor.submitTransaction(
                    TransactionSubmissionData(
                        fromBalance = from,
                        toBalance = to,
                        sellAmount = amount,
                        calculationResult = calcResult
                    )
                )) {
                    is SubmissionResult.Success -> {
                        transactionState.update {
                            TransactionState(
                                showDialog = true,
                                transactionResult = result.data
                            )
                        }
                    }

                    is SubmissionResult.Error -> {}
                }

                //Reload balances for calculation after transaction
                balancesInteractor.loadBalances().take(1).single().map {
                    when (it.currency) {
                        fromBalance.value?.currency -> fromBalance.value = it
                        toBalance.value?.currency -> toBalance.value = it
                    }
                }

                calculate()
            }
        }
    }

    fun onDismissTransactionDialog() {
        transactionState.update { state ->
            state.copy(showDialog = false)
        }
    }
}
