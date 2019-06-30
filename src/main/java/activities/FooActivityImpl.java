package activities;

import validation.FieldsValidator;
import validation.rules.RequiredRule;

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
