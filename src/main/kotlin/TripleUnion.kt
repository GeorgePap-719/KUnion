package kunion

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