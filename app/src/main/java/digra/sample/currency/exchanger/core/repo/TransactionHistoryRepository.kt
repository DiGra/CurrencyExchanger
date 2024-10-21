package digra.sample.currency.exchanger.core.repo

import digra.sample.currency.exchanger.core.model.TransactionSubmissionData

interface TransactionHistoryRepository {
    suspend fun insertTransaction(transactionSubmissionData: TransactionSubmissionData)
}
