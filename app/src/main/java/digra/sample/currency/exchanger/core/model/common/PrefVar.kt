package digra.sample.currency.exchanger.core.model.common

import kotlinx.coroutines.flow.Flow

interface PrefVar<T> {
    var value: T
    fun observe(): Flow<T>
}
