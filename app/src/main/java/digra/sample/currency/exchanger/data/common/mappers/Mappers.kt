package digra.sample.currency.exchanger.data.common.mappers

import digra.sample.currency.exchanger.core.model.Balance
import digra.sample.currency.exchanger.core.model.ExchangeRate
import digra.sample.currency.exchanger.core.model.ExchangeRatesBulk
import digra.sample.currency.exchanger.core.model.TransactionSubmissionData
import digra.sample.currency.exchanger.data.api.dto.RatesDto
import digra.sample.currency.exchanger.data.db.entity.BalanceEntity
import digra.sample.currency.exchanger.data.db.entity.ExchangeRateEntity
import digra.sample.currency.exchanger.data.db.entity.TransactionHistoryEntity
import digra.sample.currency.exchanger.util.Mapper
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

val ratesDtoToExchangeRatesBulk = Mapper.build<RatesDto, ExchangeRatesBulk> {
    ExchangeRatesBulk(
        base = base,
        date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        rates = rates
    )
}

val balanceEntityToBalance = Mapper.build<BalanceEntity, Balance> {
    Balance(
        balanceId = balanceId,
        value = value,
        currency = currency,
        orderPriority = priority
    )
}

val balanceToBalanceEntity = Mapper.build<Balance, BalanceEntity> {
    BalanceEntity(
        balanceId = balanceId,
        value = value,
        currency = currency,
        priority = orderPriority
    )
}

val exchangeRateToExchangeRateEntity = Mapper.build<ExchangeRate, ExchangeRateEntity> {
    ExchangeRateEntity(
        baseCurrency = baseCurrency,
        toCurrency = toCurrency,
        date = date,
        rate = rate
    )
}

val exchangeRateEntityToExchangeRate = Mapper.build<ExchangeRateEntity, ExchangeRate> {
    ExchangeRate(
        baseCurrency = baseCurrency,
        toCurrency = toCurrency,
        date = date,
        rate = rate
    )
}

val transactionSubmissionDataToTransactionHistoryEntity =
    Mapper.build<TransactionSubmissionData, TransactionHistoryEntity> {
        TransactionHistoryEntity(
            id = UUID.randomUUID().toString(),
            fromBalanceId = fromBalance.balanceId,
            fromCurrency = fromBalance.currency,
            fromValueBefore = fromBalance.value,
            fromValueAfter = calculationResult.fromBalanceAfter,
            sellValue = sellAmount,
            toBalanceId = toBalance.balanceId,
            toCurrency = toBalance.currency,
            toValueBefore = toBalance.value,
            toValueAfter = calculationResult.fromBalanceAfter,
            commissionFee = calculationResult.resultFeePercent,
            commissionFeeAmount = calculationResult.resultFeePercent,
            date = LocalDate.now()
        )
    }
