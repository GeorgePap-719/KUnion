package kunion

/*
 * Examples:
 */

// sealed class
sealed class ResultOrError
class Result(val value: String) : ResultOrError()
class Error(val message: String) : ResultOrError()

fun getResult(): ResultOrError {
    return Result("some value")
}

fun idiomaticErrorHandling() {
    when (val result = getResult()) {
        is Result -> TODO("process(result.value)")
        is Error -> TODO("showError(result.message)")
    }
}

// union type
typealias ResultOrErrorUnion = Union<ResultUnion, ErrorUnion>
class ResultUnion(val value: String)
class ErrorUnion(val message: String)

fun getResultUnion(): ResultOrErrorUnion {
    return Union.T1(ResultUnion("some value"))
}

fun errorHandlingWithUnions() {
    when (val result = getResultUnion()) {
        is Union.T1 ->TODO("process(result.value.value /* needs one extra step to get `value` */)")
        is Union.T2 -> TODO("showError(result.message)")
    }
}