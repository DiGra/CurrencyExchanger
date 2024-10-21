package digra.sample.currency.exchanger.di

import android.util.Log
import digra.sample.currency.exchanger.data.api.Api
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

private const val NETWORK_TIME_OUT = 6_000L

val networkModule = module {
    single { provideApi(get()) }
    single { provideHttpClient() }
}

fun provideHttpClient(): HttpClient =
    httpClientAndroid

fun provideApi(httpClient: HttpClient): Api =
    Api(httpClient)

val httpClientAndroid = HttpClient(Android) {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = false
                useAlternativeNames = true
                ignoreUnknownKeys = true
                encodeDefaults = false
            }
        )
    }

    install(HttpTimeout) {
        requestTimeoutMillis = NETWORK_TIME_OUT
        connectTimeoutMillis = NETWORK_TIME_OUT
        socketTimeoutMillis = NETWORK_TIME_OUT
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.v("Logger Ktor: ", message)
            }
        }
        level = LogLevel.ALL
    }

    install(ResponseObserver) {
        onResponse { response ->
            Log.d("HTTP status: ", "${response.status.value}")
        }
    }

    install(DefaultRequest) {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }

    defaultRequest {
        contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)
    }
}
