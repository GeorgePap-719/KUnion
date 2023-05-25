# KUnion

An ADT to represent discriminated union types in kotlin.

## Motivation

### Error handling in kotlin

In idiomatic kotlin code usually we represent different possible outcomes through a `sealed clsas` hierarchy.
```kotlin
    fun idiomaticErrorHandling() {
        when (val result = getResult()) {
            Error -> TODO("handle error")
            is Result -> TODO("doSmth(result.value)")
        }
    }
```