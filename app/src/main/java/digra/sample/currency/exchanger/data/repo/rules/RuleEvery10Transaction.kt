package digra.sample.currency.exchanger.data.repo.rules

import digra.sample.currency.exchanger.core.model.CommissionFeeRule
import digra.sample.currency.exchanger.core.properties.PropertiesFields
import digra.sample.currency.exchanger.data.db.dao.TransactionHistoryDao
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import java.math.BigDecimal

class RuleEvery10Transaction(
    priority: Int,
    active: Boolean,
    private val propertiesFields: PropertiesFields,
    private val transactionHistoryDao: TransactionHistoryDao
) : CommissionFeeRule(priority, active) {

    // Every 10 transaction is free of fee charge
    override suspend fun calculateFee(sellAmount: BigDecimal, currency: String): BigDecimal =
        transactionHistoryDao.loadTransactionHistory().take(1).single().let {
            if (it.isNotEmpty() && it.size % 10 == 0) {
                BigDecimal.ZERO
            } else {
                propertiesFields.baseFeePercent
            }
        }

}
