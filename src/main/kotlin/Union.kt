package kunion

/**
 * A union between [T1] and [T2].
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

    fun getT1OrNull(): T1? = when(this) {
        is Union.T1 -> this.value
        is Union.T2 -> null
    }

    fun getT2OrNull(): T2? = when(this) {
        is Union.T1 -> null
        is Union.T2 -> this.value
    }

    companion object {
        fun <T1, T2> t1(value: T1): Union<T1, T2> = T1(value)
        fun <T1, T2> t2(value: T2): Union<T1, T2> = T2(value)
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

    fun getT1OrNull(): T1? = when(this) {
        is TripleUnion.T1 -> this.value
        is TripleUnion.T2 -> null
        is TripleUnion.T3 -> null
    }

    fun getT2OrNull(): T2? = when(this) {
        is TripleUnion.T1 -> null
        is TripleUnion.T2 -> this.value
        is TripleUnion.T3 -> null
    }

    fun getT3OrNull(): T3? = when(this) {
        is TripleUnion.T1 -> null
        is TripleUnion.T2 -> null
        is TripleUnion.T3 -> this.value
    }

    companion object {
        fun <T1, T2, T3> t1(value: T1): TripleUnion<T1, T2, T3> = T1(value)
        fun <T1, T2, T3> t2(value: T2): TripleUnion<T1, T2, T3> = T2(value)
        fun <T1, T2, T3> t3(value: T3): TripleUnion<T1, T2, T3> = T3(value)
    }
}