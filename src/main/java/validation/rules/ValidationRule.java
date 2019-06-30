package validation.rules;

import validation.exceptions.ValidationException;

public abstract class ValidationRule {
    public void validate(String fieldName, Object val) throws ValidationException {
        if(!isValid(val)) {
            throw new ValidationException(getErrorMessage(fieldName, val));
        }
    }

    public abstract boolean isValid(Object val);

    public abstract String getErrorMessage(String fieldName, Object val);
}
