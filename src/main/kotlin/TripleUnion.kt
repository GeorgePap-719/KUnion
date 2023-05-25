package kunion

import kunion.Union.T1
import kunion.Union.T2
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A discriminated union between [T1], [T2] and [T3].
 */
sealed class TripleUnion<T1, T2, T3> {
    class T1<T1, T2, T3>(val value: T1) : TripleUnion<T1, T2, T3>() {
        override fun toString(): String = value.toString()
        override fun hashCode(): Int = value.hashCode()
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as TripleUnion.T1<*, *, *>

            return value == other.value
        }
    }

    class T2<T1, T2, T3>(val value: T2) : TripleUnion<T1, T2, T3>() {
        override fun toString(): String = value.toString()
        override fun hashCode(): Int = value.hashCode()
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as TripleUnion.T2<*, *, *>

            return value == other.value
        }
    }

    class T3<T1, T2, T3>(val value: T3) : TripleUnion<T1, T2, T3>() {
        override fun toString(): String = value.toString()
        override fun hashCode(): Int = value.hashCode()
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as TripleUnion.T3<*, *, *>

            return value == other.value
        }
    }

    fun getT1OrNull(): T1? = when (this) {
        is TripleUnion.T1 -> this.value
        is TripleUnion.T2 -> null
        is TripleUnion.T3 -> null
    }

    fun getT2OrNull(): T2? = when (this) {
        is TripleUnion.T1 -> null
        is TripleUnion.T2 -> this.value
        is TripleUnion.T3 -> null
    }

    fun getT3OrNull(): T3? = when (this) {
        is TripleUnion.T1 -> null
        is TripleUnion.T2 -> null
        is TripleUnion.T3 -> this.value
    }
}

/**
 * Returns the encapsulated [Type1][T1] value if this represents [T1] or the result of [action] function for the
 * encapsulated value if it is either [Type2][T2] or [Type3][T3].
 */
@OptIn(ExperimentalContracts::class)
inline fun <T1: R, T2, T3, R> TripleUnion<T1, T2, T3>.getT1OrElse(action: (value: Union<T2, T3>) -> R): R {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is TripleUnion.T1 -> this.value
        is TripleUnion.T2 -> action(T1(this.value))
        is TripleUnion.T3 -> action(T2(this.value))
    }
}

/**
 * Returns the encapsulated [Type2][T2] value if this represents [T2] or the result of [action] function for the
 * encapsulated value if it is either [Type1][T1] or [Type3][T3].
 */
@OptIn(ExperimentalContracts::class)
inline fun <T1, T2: R, T3, R> TripleUnion<T1, T2, T3>.getT2OrElse(action: (value: Union<T1, T3>) -> R): R {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is TripleUnion.T1 -> action(T1(this.value))
        is TripleUnion.T2 -> this.value
        is TripleUnion.T3 -> action(T2(this.value))
    }
}

/**
 * Returns the encapsulated [Type3][T3] value if this represents [T3] or the result of [action] function for the
 * encapsulated value if it is either [Type1][T1] or [Type2][T2].
 */
@OptIn(ExperimentalContracts::class)
inline fun <T1, T2, T3: R, R> TripleUnion<T1, T2, T3>.getT3OrElse(action: (value: Union<T1, T2>) -> R): R {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is TripleUnion.T1 -> action(T1(this.value))
        is TripleUnion.T2 -> action(T2(this.value))
        is TripleUnion.T3 -> this.value
    }
}

/**
 * Returns the encapsulated triple-union of the given [transform] function applied to the encapsulated value if this
 * instance represents [Type1][T1] or the original encapsulated value if it is either [Type2][T2] or [Type3][T2].
 */
@OptIn(ExperimentalContracts::class)
inline fun <T1, T2, T3, TR> TripleUnion<T1, T2, T3>.mapT1(transform: (value: T1) -> TR): TripleUnion<TR, T2, T3> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is TripleUnion.T1 -> TripleUnion.T1(transform(this.value))
        is TripleUnion.T2 -> TripleUnion.T2(this.value)
        is TripleUnion.T3 -> TripleUnion.T3(this.value)
    }
}

/**
 * Returns the encapsulated triple-union of the given [transform] function applied to the encapsulated value if this
 * instance represents [Type2][T2] or the original encapsulated value if it is either [Type1][T1] or [Type3][T2].
 */
@OptIn(ExperimentalContracts::class)
inline fun <T1, T2, T3, TR> TripleUnion<T1, T2, T3>.mapT2(transform: (value: T2) -> TR): TripleUnion<T1, TR, T3> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is TripleUnion.T1 -> TripleUnion.T1(this.value)
        is TripleUnion.T2 -> TripleUnion.T2(transform(this.value))
        is TripleUnion.T3 -> TripleUnion.T3(this.value)
    }
}

/**
 * Returns the encapsulated triple-union of the given [transform] function applied to the encapsulated value if this
 * instance represents [Type3][T3] or the original encapsulated value if it is either [Type1][T1] or [Type3][T2].
 */
@OptIn(ExperimentalContracts::class)
inline fun <T1, T2, T3, TR> TripleUnion<T1, T2, T3>.mapT3(transform: (value: T3) -> TR): TripleUnion<T1, T2, TR> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is TripleUnion.T1 -> TripleUnion.T1(this.value)
        is TripleUnion.T2 -> TripleUnion.T2(this.value)
        is TripleUnion.T3 -> TripleUnion.T3(transform(this.value))
    }
}

/**
 * Performs the given [action] on the encapsulated value if this instance represents [Type1][T1].
 * Returns the original `TripleUnion` unchanged.
 */
@OptIn(ExperimentalContracts::class)
inline fun <T1, T2, T3> TripleUnion<T1, T2, T3>.onT1(action: (value: T1) -> Unit): TripleUnion<T1, T2, T3> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is TripleUnion.T1) action(value)
    return this
}

/**
 * Performs the given [action] on the encapsulated value if this instance represents [Type2][T2].
 * Returns the original `TripleUnion` unchanged.
 */
@OptIn(ExperimentalContracts::class)
inline fun <T1, T2, T3> TripleUnion<T1, T2, T3>.onT2(action: (value: T2) -> Unit): TripleUnion<T1, T2, T3> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is TripleUnion.T2) action(value)
    return this
}

/**
 * Performs the given [action] on the encapsulated value if this instance represents [Type3][T3].
 * Returns the original `TripleUnion` unchanged.
 */
@OptIn(ExperimentalContracts::class)
inline fun <T1, T2, T3> TripleUnion<T1, T2, T3>.onT3(action: (value: T3) -> Unit): TripleUnion<T1, T2, T3> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is TripleUnion.T3) action(value)
    return this
}