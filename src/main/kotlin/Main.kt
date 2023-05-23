package kunion

fun main() {
    println(returnUnion())
    when(patternMatching()) {
        is Union.T1 -> TODO()
        is Union.T2 -> TODO()
    }
}

fun returnUnion(): Union<String, Int> {
    return Union.t1("hi world")
}

fun patternMatching(): Union<String, Int> {
    return Union.t2(10)
}