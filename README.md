## This project contains following two packages:
* activities
* validation

### How to use activity
Activity provides a standard structure that we can follow for all the Api implementations. 
What we get by default is logging, metrics(in progress), basic validations, etc.

* Write a class that contains business logic like this:

```java
public class FooActivityImpl extends BaseActivityImpl<FooActivityRequest, FooActivityResponse> {
    FooActivityImpl(FooActivityRequest request) {
        super(request);
    }

    @Override
    protected void validateRequest() {
        new FieldsValidator<>(request)
                .with("num", new RequiredRule())
                .validate();
    }

    @Override
    protected FooActivityResponse doExecute() {
        // add the execution logic here
        return new FooActivityResponse();
    }
}
```

* Use it in the actual api method:

```java
public FooResponse foo(FooRequest request) {
  return new FooActivityImpl(request).execute();
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
