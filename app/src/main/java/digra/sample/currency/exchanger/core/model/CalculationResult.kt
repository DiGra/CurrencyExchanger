package digra.sample.currency.exchanger.core.model

import java.math.BigDecimal

data class CalculationResult(
    val fromBalanceAfter: BigDecimal,
    val toBalanceAfter: BigDecimal,
    val receiveAmount: BigDecimal,
    val resultFeeAmount: BigDecimal,
    val resultFeePercent: BigDecimal,
    val resultState: ResultState
)
