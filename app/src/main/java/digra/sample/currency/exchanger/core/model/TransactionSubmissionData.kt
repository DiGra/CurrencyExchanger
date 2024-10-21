package digra.sample.currency.exchanger.core.model

import java.math.BigDecimal

data class TransactionSubmissionData(
    val fromBalance: Balance,
    val toBalance: Balance,
    val sellAmount: BigDecimal,
    val calculationResult: CalculationResult
)
