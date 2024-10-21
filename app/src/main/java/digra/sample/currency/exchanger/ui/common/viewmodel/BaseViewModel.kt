package digra.sample.currency.exchanger.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel : ViewModel() {

    protected val defaultIOContext = viewModelScope.coroutineContext + Dispatchers.IO
}
