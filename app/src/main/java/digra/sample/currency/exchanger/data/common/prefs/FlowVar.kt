package digra.sample.currency.exchanger.data.common.prefs

import digra.sample.currency.exchanger.core.model.common.PrefVar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FlowVar<T>(
    private val onRead: FlowVar<T>.() -> T,
    private val onUpdate: FlowVar<T>.(T) -> Unit
) : PrefVar<T> {
    override var value: T
        get() = onRead()
        set(value) {
            onUpdate(value)
            publish(value)
        }

    private val state by lazy { MutableStateFlow(value) }

    private fun publish(value: T) {
        state.value = value
    }

    override fun observe(): Flow<T> = state
}
