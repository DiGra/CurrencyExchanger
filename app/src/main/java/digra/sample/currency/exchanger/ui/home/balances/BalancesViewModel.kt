package digra.sample.currency.exchanger.ui.home.balances

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import digra.sample.currency.exchanger.core.interactor.BalancesInteractor
import digra.sample.currency.exchanger.core.model.Balance
import digra.sample.currency.exchanger.ui.common.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class BalancesViewModel(
    private val balancesInteractor: BalancesInteractor
) : BaseViewModel() {

    private val balances: LiveData<List<Balance>> by lazy {
        balancesInteractor.loadBalances().asLiveData(defaultIOContext)
    }

    fun loadBalances() = balances

    fun fillDummyData() {
        viewModelScope.launch {
            balancesInteractor.fillDummyData()
        }
    }
}
