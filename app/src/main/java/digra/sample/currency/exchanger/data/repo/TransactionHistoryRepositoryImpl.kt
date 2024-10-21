package digra.sample.currency.exchanger.data.repo

import digra.sample.currency.exchanger.core.model.TransactionSubmissionData
import digra.sample.currency.exchanger.core.repo.TransactionHistoryRepository
import digra.sample.currency.exchanger.data.common.mappers.transactionSubmissionDataToTransactionHistoryEntity
import digra.sample.currency.exchanger.data.db.dao.TransactionHistoryDao

class TransactionHistoryRepositoryImpl(
    private val transactionHistoryDao: TransactionHistoryDao
) : TransactionHistoryRepository {
    override suspend fun insertTransaction(data: TransactionSubmissionData) {
        transactionHistoryDao.insertTransaction(
            transactionSubmissionDataToTransactionHistoryEntity(data)
        )
    }
}
