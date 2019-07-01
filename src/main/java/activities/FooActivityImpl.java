package activities;

import validation.FieldsValidator;
import validation.rules.RequiredRule;

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
