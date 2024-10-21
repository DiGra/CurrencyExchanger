package digra.sample.currency.exchanger.core.model

sealed class SubmissionResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : SubmissionResult<T>()
    data class Error(val error: String) : SubmissionResult<Nothing>()
}
