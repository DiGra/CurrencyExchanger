package digra.sample.currency.exchanger.core.model

import java.math.BigDecimal

abstract class CommissionFeeRule(
    val priority: Int,
    val active: Boolean
) {
    abstract suspend fun calculateFee(sellAmount: BigDecimal, currency: String): BigDecimal
}
