# KUnion

An ADT to represent discriminated union types in kotlin.

## Motivation

## Error handling in kotlin


### Sealed class

In idiomatic kotlin code usually we represent complex error states through a `sealed class` hierarchy. For example:
```kotlin
sealed class ResultOrError
class Result(val value: String) : ResultOrError()
class Error(val message: String) : ResultOrError()

fun getResult(): ResultOrError {
    return Result("some value")
}

fun idiomaticErrorHandling() {
    when (val result = getResult()) {
        is Result -> TODO("doSmth(result.value)")
        is Error -> TODO("showError(result.message)")
    }
}
```

### Union type

Using `union-types` the same code can be re-written like this:

```kotlin
typealias ResultOrError = Union<ResultUnion, ErrorUnion>
class Result(val value: String) 
class Error(val message: String)

fun getResult(): ResultOrError {
    return Union.T1(Result("some value"))
}

fun errorHandlingWithUnions() {
    when (val result = getResultUnion()) {
        is Union.T1 -> TODO("process(result.value)")
        is Union.T2 -> TODO("showError(result.message)")
    }
}
```
