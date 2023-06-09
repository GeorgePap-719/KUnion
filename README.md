# KUnion

An ADT to represent discriminated union types in kotlin.

## Error handling

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

Using `union` type, the same code can be re-written like this:

```kotlin
typealias ResultOrError = Union<ResultUnion, ErrorUnion>

class Result(val value: String)
class Error(val message: String)

fun getResult(): ResultOrError {
    return Union.T1(Result("some value"))
}

fun errorHandlingWithUnions() {
    when (val result = getResult()) {
        /* needs one extra step to get `value` */
        is Union.T1 /* loses readability of `Result` */ -> TODO("process(result.value.value)")
        is Union.T2 /* loses readability of `Error` */ -> TODO("showError(result.value.message)")
    }
}
```

In plain comparison, `Union` type approach loses some readability. Also, `Union` type itself adds some extra complexity
to the solution, compared to a simple sealed class hierarchy.

## Motivation

In application code development, business scenarios frequently involve complex and extensive requirements. This often
leads to the creation of numerous sealed classes to represent various states or outcomes. However, managing a large
number of these classes can result in code duplication, especially when similar sealed classes are needed for different
contexts. In some cases, it becomes necessary to unify multiple sealed classes under a single sealed class type. The
primary goals of this project are to minimize clutter, eliminate code duplication, and provide a cohesive API for
managing diverse sealed classes within the given contexts, even at the cost of reduced readability.

## Usage

**tl;dr:**

- Use when there is a need to represent a union of sealed classes or a value and a sealed class.
- Use when the sealed class is simple and internal.
- **Do not** use as return types on public APIs.

### When it might be a good solution to use `Union` type

- Sometimes **internally** of an API, when you need a simple sealed class to represent a state/result. Such a sealed
  class could be:

```kotlin
private sealed class Result {
    class Success(val value: MyDevice) : Result()
    class Error(val value: MyError) : Result()
}
```

This sealed class can be easily converted to a `Union` type, since these mini sealed classes can add up pretty fast in a
large codebase. Also, `Union` type, comes with some extensions that can boost flow-readability
like, `getT1OrElse`, `getT2OrNull`, etc.

- During complex flow-control, sometimes there is a need to represent either a "value" and another "sealed class" or to
  unify two different sealed classes; such a sealed class can be:

```kotlin
private sealed class Result {
    class Success(val value: MyDevice) : Result()
    class Error(val value: SetKeysResult /* sealed class */) : Result()
}
```

I will not dwell on why this is needed to handle a business scenario, but representing such cases through a `Union` type
can save up some time from authoring some "weird" sealed class hierarchy. Don't forget, in the end `Union` is just a
"dynamic" sealed class.

### When it should be avoided to use `Union` type

- As a return type for a **public** API. This is because, a simple sealed class is always more readable from `Union`
  type. Also, it forces the consumer to "acknowledge" the `Union` API without any direct benefit.
- Do not depend on `Union` API just for the convenient/ready-to-use extensions. It is pretty easy to write appropriate
  extensions for a targeted sealed class, and probably most cases will not use more than one extension.