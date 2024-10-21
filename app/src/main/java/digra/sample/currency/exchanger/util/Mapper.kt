package digra.sample.currency.exchanger.util

abstract class Mapper<in T, out U> private constructor() : (T) -> U {

    val listMapper: Mapper<List<T>?, List<U>>
        get() = asListMapper()

    private fun asListMapper(): Mapper<List<T>?, List<U>> =
        object : Mapper<List<T>?, List<U>>() {
            override fun invoke(t: List<T>?) = t?.map(this@Mapper) ?: emptyList()
        }

    companion object {
        fun <T, U> build(action: T.() -> U): Mapper<T, U> =
            object : Mapper<T, U>() {
                override fun invoke(t: T) = action(t)
            }
    }
}
