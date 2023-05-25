package kunion

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A discriminated union between [T1] and [T2].
 */
sealed class Union<T1, T2> {
    class T1<T1, T2>(val value: T1) : Union<T1, T2>() {
        override fun toString(): String = value.toString()
        override fun hashCode(): Int = value.hashCode()
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Union.T1<*, *>

            return value == other.value
        }
    }

    class T2<T1, T2>(val value: T2) : Union<T1, T2>() {
        override fun toString(): String = value.toString()
        override fun hashCode(): Int = value.hashCode()
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Union.T2<*, *>

            return value == other.value
        }
    }

    fun getT1OrNull(): T1? = when (this) {
        is Union.T1 -> this.value
        is Union.T2 -> null
    }

    fun getT2OrNull(): T2? = when (this) {
        is Union.T1 -> null
        is Union.T2 -> this.value
    }
}

/**
 * Returns the encapsulated [Type1][T1] value if this represents [T1] or the result of [action] function for the
 * encapsulated [Type2][T2] if it is [T2].
 */
@OptIn(ExperimentalContracts::class)
inline fun <T1: R, T2, R> Union<T1, T2>.getT1OrElse(action: (value: T2) -> R): R {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Union.T1 -> this.value
        is Union.T2 -> action(this.value)
    }
}

/**
 * Returns the encapsulated [Type2][T2] value if this represents [T2] or the result of [action] function for the
 * encapsulated [Type1][T1] if it is [T1].
 */
@OptIn(ExperimentalContracts::class)
inline fun <T1, T2: R, R> Union<T1, T2>.getT2OrElse(action: (value: T1) -> R): R {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Union.T1 -> action(this.value)
        is Union.T2 -> this.value
    }
}

/**
 * Returns the encapsulated union of the given [transform] function applied to the encapsulated value if this
 * instance represents [Type1][T1] or the original encapsulated value if it is [Type2][T2].
 */
@OptIn(ExperimentalContracts::class)
inline fun <T1, T2, TR> Union<T1, T2>.mapT1(transform: (value: T1) -> TR): Union<TR, T2> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Union.T1 -> Union.T1(transform(this.value))
        is Union.T2 -> Union.T2(this.value)
    }
}

/**
 * Returns the encapsulated union of the given [transform] function applied to the encapsulated value if this
 * instance represents [Type2][T2] or the original encapsulated value if it is [Type1][T1].
 */
@OptIn(ExperimentalContracts::class)
inline fun <T1, T2, TR> Union<T1, T2>.mapT2(transform: (value: T2) -> TR): Union<T1, TR> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Union.T1 -> Union.T1(this.value)
        is Union.T2 -> Union.T2(transform(this.value))
    }
}

/**
 * Performs the given [action] on the encapsulated value if this instance represents [Type1][T1].
 * Returns the original `Union` unchanged.
 */
@OptIn(ExperimentalContracts::class)
inline fun <T1, T2> Union<T1, T2>.onT1(action: (value: T1) -> Unit): Union<T1, T2> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is Union.T1) action(value)
    return this
}

/**
 * Performs the given [action] on the encapsulated value if this instance represents [Type2][T2].
 * Returns the original `Union` unchanged.
 */
@OptIn(ExperimentalContracts::class)
inline fun <T1, T2> Union<T1, T2>.onT2(action: (value: T2) -> Unit): Union<T1, T2> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is Union.T2) action(value)
    return this
}