package digra.sample.currency.exchanger.core.interactor

import digra.sample.currency.exchanger.core.model.Balance
import digra.sample.currency.exchanger.core.model.CalculationResult
import digra.sample.currency.exchanger.core.model.CommissionFeeRule
import digra.sample.currency.exchanger.core.model.ExchangeRate
import digra.sample.currency.exchanger.core.model.ResultState
import digra.sample.currency.exchanger.core.model.SubmissionResult
import digra.sample.currency.exchanger.core.model.TransactionSubmissionData
import digra.sample.currency.exchanger.core.model.TransactionSubmissionResult
import digra.sample.currency.exchanger.core.repo.BalancesRepository
import digra.sample.currency.exchanger.core.repo.RulesRepository
import digra.sample.currency.exchanger.core.repo.TransactionHistoryRepository
import java.math.BigDecimal

class TransactionInteractor(
    private val rulesRepository: RulesRepository,
    private val transactionHistoryRepository: TransactionHistoryRepository,
    private val balancesRepository: BalancesRepository
) {

    suspend fun calculate(
        baseCurrency: String,
        fromBalance: Balance,
        toBalance: Balance,
        sellAmount: BigDecimal,
        exchangeRates: List<ExchangeRate>,
        rules: List<CommissionFeeRule>,
        baseFee: BigDecimal
    ): CalculationResult {
        var resultState = ResultState.NON_VALID_TRANSACTION
        var receiveAmount = BigDecimal.ZERO

        // Calculate receive amount
        when (baseCurrency) {
            // Transaction from base currency to any currency
            fromBalance.currency -> {
                exchangeRates.find { it.toCurrency == toBalance.currency }?.let {
                    receiveAmount = sellAmount * it.rate
                } ?: {
                    resultState = ResultState.NON_VALID_TRANSACTION
                }

            }
            // Transaction from any currency to base currency
            toBalance.currency -> {
                exchangeRates.find { it.toCurrency == fromBalance.currency }?.let {
                    receiveAmount = sellAmount / it.rate
                } ?: {
                    resultState = ResultState.NON_VALID_TRANSACTION
                }
            }
            // Transaction from any currency to any currency
            else -> {
                exchangeRates.find { it.toCurrency == fromBalance.currency }?.let {
                    var tempAmount = sellAmount / it.rate
                    exchangeRates.find { it.toCurrency == toBalance.currency }?.let {
                        receiveAmount = tempAmount * it.rate
                    } ?: {
                        resultState = ResultState.NON_VALID_TRANSACTION
                    }
                } ?: {
                    resultState = ResultState.NON_VALID_TRANSACTION
                }
            }
        }

        // Check transaction fee rules and calculate fee amount
        var fee = baseFee
        run breaking@{
            rules.forEach {
                if (fee == BigDecimal.ZERO) return@breaking
                fee = it.calculateFee(sellAmount, fromBalance.currency)
            }
        }
        val resultFeeAmount = sellAmount * fee
        val fromBalanceAfter = fromBalance.value - sellAmount - resultFeeAmount
        val toBalanceAfter = toBalance.value + receiveAmount

        // Check is it's enough cash on balance for transaction
        return if (fromBalanceAfter > BigDecimal.ZERO) {
            CalculationResult(
                fromBalanceAfter = fromBalanceAfter,
                toBalanceAfter = toBalanceAfter,
                receiveAmount = receiveAmount,
                resultFeePercent = fee,
                resultFeeAmount = resultFeeAmount,
                resultState = ResultState.VALID_TRANSACTION
            )
        } else {
            CalculationResult(
                fromBalanceAfter = fromBalanceAfter,
                toBalanceAfter = toBalanceAfter,
                receiveAmount = receiveAmount,
                resultFeePercent = fee,
                resultFeeAmount = resultFeeAmount,
                resultState = ResultState.NOT_ENOUGH_CASH_FOR_TRANSACTION
            )
        }
    }

    suspend fun submitTransaction(
        data: TransactionSubmissionData
    ): SubmissionResult<TransactionSubmissionResult> =
        // Double check that transaction is valid
        if (data.calculationResult.resultState == ResultState.VALID_TRANSACTION) {
            // Insert to transaction history
            transactionHistoryRepository.insertTransaction(data)
            // Updating balance that sell from
            balancesRepository.updateBalance(
                data.fromBalance.copy(
                    value = data.calculationResult.fromBalanceAfter
                )
            )
            // Updating balance that receive
            balancesRepository.updateBalance(
                data.toBalance.copy(
                    value = data.calculationResult.toBalanceAfter
                )
            )
            SubmissionResult.Success(
                TransactionSubmissionResult(
                    sellAmount = data.sellAmount,
                    sellCurrency = data.fromBalance.currency,
                    receiveAmount = data.calculationResult.receiveAmount,
                    receiveCurrency = data.toBalance.currency,
                    commissionFee = data.calculationResult.resultFeeAmount
                )
            )
        } else {
            SubmissionResult.Error(ResultState.NON_VALID_TRANSACTION.toString())
        }

    fun loadRules(): List<CommissionFeeRule> =
        rulesRepository.getRules()
}
