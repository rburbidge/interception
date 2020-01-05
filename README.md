# interception
Supports wrapping Java code in interceptor pipelines.

Interception lets you create interceptor chains to perform operations around you code.

## Interceptor chains
Define simple or complex pipelines around your code.
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

## How it works
If we have the following chain of interceptors that each print enter/exit statements around the work to be executed...
```java
private static final InterceptorChain chain = InterceptorChain.builder()
            .interceptor(new MyInterceptor("A"))
            .interceptor(new MyInterceptor("B"))
            .interceptor(new MyInterceptor("C"))
            .build();
            
chain.execute(() -> { System.out.println("Do some work"); });
```

Then we get the following output:
```
Enter A
Enter B
Enter C
Do some work
Exit C
Exit B
Exit A
```

```java
class MyInterceptor implements Interceptor {
    private final String name;
    MyInterceptor(String name) {
        this.name = name;
    }

    @Override
    public Object execute(Operation operation) {
        System.out.println("Enter " + this.name);
        Object result = operation.execute();
        System.out.println("Exit " + this.name);
        return result;
    }
}
```

# Interceptors
## Begin/end logging
```java
class LoggingInterceptor implements Interceptor {
    @Override
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
