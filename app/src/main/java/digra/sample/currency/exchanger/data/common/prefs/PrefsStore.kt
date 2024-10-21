package digra.sample.currency.exchanger.data.common.prefs

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class PrefsStore(
    context: Context,
    storeName: String
) : CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    protected val prefs: SharedPreferences =
        context.getSharedPreferences(storeName, Context.MODE_PRIVATE)

    protected inline fun <reified T> get(key: String, defaultValue: T): T =
        prefs.get(key, defaultValue)

    protected inline fun <reified T> put(key: String, value: T) = prefs.put(key, value)
}
