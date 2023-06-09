package kunion

fun main() {
    val union = returnUnion()
    val value = union.getT1OrElse {
        10
    }
    val resultValue = runCatching { throwErrrorE() }
    val finalVal = resultValue.getOrElse {
        10
    }
    println(finalVal)

    val triple = TripleUnion.T3<String, Int, Int>(11)
    val map = triple.mapT3 { Test("hi") }
    println(map)
}

fun returnUnion(): Union<String, Int> {
    return Union.T1("hi world")
}

fun patternMatching(): Union<String, Int> {
    return Union.T2(10)
}

fun throwErrrorE(): String {
    error("throws afterAll")
}

data class Test(val value: String)