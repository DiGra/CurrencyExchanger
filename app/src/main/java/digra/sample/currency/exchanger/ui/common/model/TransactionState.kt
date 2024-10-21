package digra.sample.currency.exchanger.ui.common.model

import digra.sample.currency.exchanger.core.model.TransactionSubmissionResult

data class TransactionState(
    val showDialog: Boolean,
    val transactionResult: TransactionSubmissionResult
)
