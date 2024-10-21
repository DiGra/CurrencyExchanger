package digra.sample.currency.exchanger.di

import digra.sample.currency.exchanger.data.repo.rules.RuleEvery10Transaction
import digra.sample.currency.exchanger.data.repo.rules.RuleFirst5Transaction
import digra.sample.currency.exchanger.data.repo.rules.RuleLessThan200ForSell
import org.koin.dsl.module

val rulesModule = module {
    // Change true/false for activation of specific Rule
    factory<RuleFirst5Transaction> { RuleFirst5Transaction(1, true, get(), get()) }
    factory<RuleEvery10Transaction> { RuleEvery10Transaction(2, true, get(), get()) }
    factory<RuleLessThan200ForSell> { RuleLessThan200ForSell(3, false, get(), get(), get()) }
}
