package digra.sample.currency.exchanger.core.model

import java.math.BigDecimal

data class TransactionSubmissionResult(
    val sellAmount: BigDecimal,
    val sellCurrency: String,
    val receiveAmount: BigDecimal,
    val receiveCurrency: String,
    val commissionFee: BigDecimal
)
