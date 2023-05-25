package kunion

class Examples {

    fun idiomaticErrorHandling() {
        when (val result = getResult()) {
            Error -> TODO("handle error")
            is Result -> TODO("doSmth(result.value)")
        }
    }


}

sealed class ResultOrError
class Result(val value: String) : ResultOrError()
object Error : ResultOrError()

fun getResult(): ResultOrError {
    return Result("some value")
}