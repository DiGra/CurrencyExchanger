package digra.sample.currency.exchanger.core.repo

import digra.sample.currency.exchanger.core.model.CommissionFeeRule

interface RulesRepository {
    fun getRules(): List<CommissionFeeRule>
}
