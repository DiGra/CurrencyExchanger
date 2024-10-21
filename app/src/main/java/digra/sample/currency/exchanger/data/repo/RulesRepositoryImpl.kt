package digra.sample.currency.exchanger.data.repo

import digra.sample.currency.exchanger.core.model.CommissionFeeRule
import digra.sample.currency.exchanger.core.repo.RulesRepository
import digra.sample.currency.exchanger.data.repo.rules.RuleEvery10Transaction
import digra.sample.currency.exchanger.data.repo.rules.RuleFirst5Transaction
import digra.sample.currency.exchanger.data.repo.rules.RuleLessThan200ForSell

class RulesRepositoryImpl(
    private val ruleFirst5Transaction: RuleFirst5Transaction,
    private val ruleEvery10Transaction: RuleEvery10Transaction,
    private val ruleLessThan200ForSell: RuleLessThan200ForSell,
) : RulesRepository {
    override fun getRules(): List<CommissionFeeRule> {
        val rules = mutableListOf<CommissionFeeRule>()
        if (ruleFirst5Transaction.active) {
            rules.add(ruleFirst5Transaction)
        }
        if (ruleEvery10Transaction.active) {
            rules.add(ruleEvery10Transaction)
        }
        if (ruleLessThan200ForSell.active) {
            rules.add(ruleLessThan200ForSell)
        }
        return rules.sortedBy { it.priority }
    }
}
