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
inline fun <T1: R, T2, R> Union<T1, T2>.getT1OrElse(action: (t2: T2) -> R): R {
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
inline fun <T1, T2: R, R> Union<T1, T2>.getT2OrElse(action: (t1: T1) -> R): R {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Union.T1 -> action(this.value)
        is Union.T2 -> this.value
    }
}


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