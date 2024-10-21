package digra.sample.currency.exchanger.core.prefs

import digra.sample.currency.exchanger.core.model.common.PrefVar

interface AppStore {
    var isFirstLaunch: PrefVar<Boolean>
    var baseCurrency: PrefVar<String>
}
