package digra.sample.currency.exchanger.core.repo

import digra.sample.currency.exchanger.core.model.Balance
import kotlinx.coroutines.flow.Flow

interface BalancesRepository {
    fun loadBalances(): Flow<List<Balance>>
    suspend fun updateBalance(balance: Balance)
    suspend fun updateBalances(balance: List<Balance>)
}
