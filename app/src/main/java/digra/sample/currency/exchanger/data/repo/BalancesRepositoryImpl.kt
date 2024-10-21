package digra.sample.currency.exchanger.data.repo

import digra.sample.currency.exchanger.core.model.Balance
import digra.sample.currency.exchanger.core.repo.BalancesRepository
import digra.sample.currency.exchanger.data.common.mappers.balanceEntityToBalance
import digra.sample.currency.exchanger.data.common.mappers.balanceToBalanceEntity
import digra.sample.currency.exchanger.data.db.dao.BalancesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BalancesRepositoryImpl(
    private val balancesDao: BalancesDao
) : BalancesRepository {

    override fun loadBalances(): Flow<List<Balance>> =
        balancesDao.loadAllBalances().map { balanceEntityToBalance.listMapper(it) }

    override suspend fun updateBalance(balance: Balance) {
        balancesDao.insertBalance(balanceToBalanceEntity(balance))
    }

    override suspend fun updateBalances(balance: List<Balance>) {
        balancesDao.insertBalances(balanceToBalanceEntity.listMapper(balance))
    }
}
