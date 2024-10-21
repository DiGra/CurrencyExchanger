package digra.sample.currency.exchanger.data.prefs

import android.content.Context
import digra.sample.currency.exchanger.core.model.common.PrefVar
import digra.sample.currency.exchanger.core.prefs.AppStore
import digra.sample.currency.exchanger.core.properties.PropertiesFields
import digra.sample.currency.exchanger.data.common.prefs.FlowVar
import digra.sample.currency.exchanger.data.common.prefs.PrefsStore

class AppPrefsStore(
    context: Context,
    propertiesFields: PropertiesFields
) : PrefsStore(context, PREFS_NAME), AppStore {

    override var isFirstLaunch: PrefVar<Boolean> = FlowVar(
        onRead = { get(PREF_FIRST_LAUNCH, true) },
        onUpdate = { put(PREF_FIRST_LAUNCH, it) }
    )

    // Base currency as a preference, because we assume that it could change at any time
    override var baseCurrency: PrefVar<String> = FlowVar(
        onRead = { get(PREF_BASE_CURRENCY, propertiesFields.defaultBaseCurrency) },
        onUpdate = { put(PREF_BASE_CURRENCY, it) }
    )

    companion object {
        private const val PREFS_NAME = "pref_store"
        private const val PREF_FIRST_LAUNCH = "is_first_launch"
        private const val PREF_BASE_CURRENCY = "base_currency"
    }
}
