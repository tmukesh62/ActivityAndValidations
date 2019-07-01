## This project contains following two packages:
* activities
* validation

### How to use activity
The idea is to isolate the business logic from the API classes and test them in isolation. ```BaseActivityImpl``` provides a standard structure that we can follow for any API implementation. All the business logic goes into the ActivityImpl classes.

* Write a class that contains business logic like this:

```java
public class FooActivityImpl extends BaseActivityImpl<FooActivityRequest, FooActivityResponse> {
    FooActivityImpl() {
        super();
    }

    @Override
    protected void validateRequest(FooActivityRequest request) {
        new FieldsValidator<>(request)
                .with("num", new RequiredRule())
                .validate();
    }

    @Override
    protected FooActivityResponse doExecute(FooActivityRequest request) {
        // add the execution logic here
        return new FooActivityResponse();
    }
}
```

For your reference, this is what ```BaseActivityImpl``` looks like:

```java
public abstract class BaseActivityImpl<T extends Object, K extends Object> {
    BaseActivityImpl() {
    }

    public K execute(T request) {
        logRequest(request);

        validateRequest(request);

        Object o = doIdempotencyCheck(request);

        K response = null;
        if (o != null) {
            response = doExecuteIdempotent(request);
        } else {
            response = doExecute(request);
        }

        logResponse(response);

        return response;
    }

    // validate the request here
    protected abstract void validateRequest(T request);

    protected K doIdempotencyCheck(T request) {
        // this is optional
        return null;
    }

    // put the business logic here
    protected abstract K doExecute(T request);

    protected K doExecuteIdempotent(T request) {
        // this is optional
        return null;
    }

    private void logRequest(T request) {
        // log request here
    }

    private void logResponse(K response) {
        // log response here
    }
}
```

* Use it in the actual api method:

```java
public class FooActivity extends Activity {
    @Inject
    FooActivityImpl fooActivityImpl;

    public FooResponse foo(FooRequest request) {
      return fooActivityImpl.execute(request);
    }
}
```

### How to use validation

```java
final ValidationResult result = new FieldsValidator<>(request)
                .with("num", new RequiredRule())
                .with("text", new NonEmptyStringRule())
                .with("text", new SupportedValuesRule<>(Arrays.asList(new String[]{"ABC", "CBS"})))
                .with("items", new NonEmptyCollectionRule())
                .validate();
```

If the activity fails, we get something like this in the response:

```bash
ValidationResult{
  valid=false, 
  errorMessages=[
    "The field num is required but not present", 
    "The text of field text can not be empty", 
    "The field text can only have values in (ABC, CBS) but found ", 
    "The collection items can not be empty"]
}
```

In case there are no validation errors, you will get the following response:

```bash
ValidationResult{
  valid=true, 
  errorMessages=[]
}
```

#### How to add a new validation rule?

* Adding a new rule is pretty straightforward and fun. All you need to do is to extend from class ```ValidationRule``` and implement the abstract methods.
