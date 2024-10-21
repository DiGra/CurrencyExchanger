package digra.sample.currency.exchanger.core.interactor

import digra.sample.currency.exchanger.core.model.Balance
import digra.sample.currency.exchanger.core.prefs.AppStore
import digra.sample.currency.exchanger.core.properties.PropertiesFields
import digra.sample.currency.exchanger.core.repo.BalancesRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class BalancesInteractor(
    private val appStore: AppStore,
    private val propertiesFields: PropertiesFields,
    private val balancesRepository: BalancesRepository
) {

    fun loadBalances(): Flow<List<Balance>> =
        balancesRepository.loadBalances()

    // On first start of application fill database with a dummy data
    // Base values could be modified at config.properties file
    suspend fun fillDummyData() {
        if (appStore.isFirstLaunch.value) {
            balancesRepository.updateBalance(
                Balance(
                    balanceId = UUID.randomUUID().toString(),
                    currency = appStore.baseCurrency.value,
                    value = propertiesFields.baseCurrencyValue,
                    orderPriority = 0
                )
            )
            appStore.isFirstLaunch.value = false
        }
    }
}
