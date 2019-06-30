## This project contains following two packages:
* activities
* validation

### How to use activity
Activity provides a standard structure that we can follow for all the Api implementations. 
What we get by default is logging, metrics(in progress), basic validations, etc.

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
