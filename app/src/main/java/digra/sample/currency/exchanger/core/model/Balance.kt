package digra.sample.currency.exchanger.core.model

import java.math.BigDecimal

data class Balance(
    val balanceId: String,
    val value: BigDecimal,
    val currency: String,
    val orderPriority: Int
)
