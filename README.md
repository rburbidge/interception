# Interception
Interception is a library for addressing cross-cutting concerns in Java apps. Create interceptor chains and execute them
across common code operations.

Here is a logging interceptor that logs begin and end.
```java
public class LoggingInterceptor implements Interceptor {
    public Object execute(Operation operation) {
        System.out.println(operation.name() + " starting with input " + operation.getInput());
        Object result = operation.execute();
        System.out.println(operation.name() + " ending with result " + result);
        return result;
    }
}
```

Interception can be used to abstract and implement pipelines code, including:
* Logging
* Instrumentation
* Retries
* Rate limiting
* Auth
* Validation
* Modification of input/output
* ... basically anything that wraps code!

# Getting started

The above `LoggingInterceptor` can be chained with a `TimingInterceptor` to log and time different operations.
```java

InterceptorChain chain = InterceptorChain.builder()
                .interceptor(new LoggingInterceptor())
                .interceptor(new TimingInterceptor())
                .build();

int multiplyResult = chain.start()
        .name("multiplyBy2")
        .execute(2, num -> num * 2);

int divideResult = chain.start()
        .name("divideBy2")
        .execute(2, num -> num / 2);
```

Resulting in this output.
```java
multiplyBy2 starting with input 2
multiplyBy2 execution time: 20922ns
multiplyBy2 ending with result 4
divideBy2 starting with input 2
divideBy2 execution time: 10263ns
divideBy2 ending with result 1
```

See [LoggingAndTiming.java](./samples/src/main/java/com/sirnommington/interception/samples/LoggingAndTiming.java).

## Why this exists

Software projects often have several similar blocks of code (e.g. API calls to services) that we wrap in other code for
things like logging, authentication, retries, metrics, more logging. This wrapping code often evolves over time, and it
often involves the use of several different frameworks. This results in a large hunk of code that ends up working in
some cases, but not others.

Interception aims to solve this problem by.
1. Separate the (often unrelated) wrapping code and abstract it into a common interface.
2. Create reusable pipelines (we call "chains") of wrapping code.
3. Executing operations with said chains.

Or in Interception terms:
1. Separate and abstract wrapping code into [Interceptors](./lib/src/main/java/com/sirnommington/interception/Interceptor.java).
2. Compose a set of `Interceptor`s into one or more [InterceptorChains](./lib/src/main/java/com/sirnommington/interception/InterceptorChain.java).
3. Execute multiple operations with each `InterceptorChain`.

## How it works
Let's say we have an `EnterExitInterceptor` that logs `"Enter <name>"` and `"Exit <name>"` on begin/end.

Then we create an `InterceptorChain`, and execute it.
```java
InterceptorChain chain = InterceptorChain.builder()
        .interceptor(new EnterExitInterceptor("A"))
        .interceptor(new EnterExitInterceptor("B"))
        .interceptor(new EnterExitInterceptor("C"))
        .build();

chain.start().execute(() -> { System.out.println("Do some work"); });
```

We get the following output.
```
Enter A
Enter B
Enter C
Do some work
Exit C
Exit B
Exit A
```

Given this `EnterExitInterceptor`.
```java
class EnterExitInterceptor implements Interceptor {
    private final String name;

    public EnterExitInterceptor(String name) { this.name = name; }
    
    public Object execute(Operation operation) {
        System.out.println("Enter " + this.name);
        Object result = operation.execute();
        System.out.println("Exit " + this.name);
        return result;
    }
}
```

See [HowItWorks.java](./samples/src/main/java/com/sirnommington/interception/samples/HowItWorks.java).

## Interceptor chains
Define simple or complex pipelines:
```java
private static final InterceptorChain chain = InterceptorChain.builder()
            .interceptor(new AuthRetryInterceptor(authProvider))
            .interceptor(new LoggingInterceptor())
            .interceptor(new TimingInterceptor())
            .interceptor(new SuccessInterceptor())
            .build();
```

Now reuse this chain across multiple parts of your code.
```java
chain.start()
    .name("getKetchup")
    .execute(() -> {
        /** Your wrapped code */
    });
```

## Providing data to Interceptors

You can provide parameters to the `Interceptors` when starting the operation.

```java
int count = chain.start()
        .name("getKetchup")
        .param("userId", 8317)
        .execute(() -> /* get the ketchup */ 20);

chain.start()
        .name("addKetchup")
        .param("userId", 2485)
        .execute(() -> /* add the ketchup */ {});
````

And then access them in an `Interceptor` using `operation.param(key)`. For operation name, use `operation.name()`.
```java
class KetchupInterceptor implements Interceptor {
        @Override
        public Object execute(Operation operation) {
            Integer userId = (Integer) operation.param("userId");

            System.out.println(operation.name() + " called by user " + userId);

            return operation.execute();
        }
    }
```

This results in the following output.

```
getKetchup called by user 8317
addKetchup called by user 2485
```

See [InterceptorParameters.java](./samples/src/main/java/com/sirnommington/interception/samples/InterceptorParameters.java).

## Interceptor use cases
### Begin/end logging
```java
class LoggingInterceptor implements Interceptor {
    public Object execute(Operation operation) {
        System.out.println("Begin operation " + operation.name() + " with input " + operation.getInput());
        Object result = operation.execute();
        System.out.println("End operation " + operation.name() + " with result " + result);
        return result;
    }
}
```

# Building and publishing
* `gradle build` - Builds the library
* `gradle bintrayUpload` - Publishes the library to bintray. See [Publishing](#Publishing).

## Publishing
Builds are published using `gradle bintrayUpload`.

Setup these environment variables on your system containing your https://bintray.com/ user name and API key.
* `bintrayUser`
* `bintrayApiKey`

Publishing will fail if bintray contains the same version. Bump up `libraryVersion` in `./ext.gradle` to retry the publish step.
