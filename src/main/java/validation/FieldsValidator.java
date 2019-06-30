package validation;

import validation.exceptions.ValidationException;
import validation.rules.RequiredRule;
import validation.rules.ValidationRule;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class FieldsValidator<T extends Object> {
    private static final String MESSAGE_FOR_NULL_INPUT = "The input is null";

    private final T object;
    private Map<String, Set<ValidationRule>> validationRulesByField;

    public FieldsValidator(T object) {
        this.object = object;
        this.validationRulesByField = new HashMap<>();
    }

    public FieldsValidator with(String fieldName, ValidationRule rule) {
        if (!validationRulesByField.containsKey(fieldName)) {
            validationRulesByField.put(fieldName, new HashSet<>());
        }

        validationRulesByField.get(fieldName).add(rule);

        return this;
    }

    public ValidationResult validate() {
        ValidationResult result = new ValidationResult();

        try {
            new RequiredRule().validate("RequestObject", object);
        } catch (ValidationException e) {
            result.addErrorMessage(MESSAGE_FOR_NULL_INPUT);
            return result;
        }

        validationRulesByField.keySet().stream()
                .forEach(
                        fieldName -> {
                            validationRulesByField.get(fieldName).stream()
                                    .forEach(
                                            validationRule -> {
                                                try {
                                                    validationRule.validate(fieldName,
                                                            getValueForField(object, fieldName));
                                                } catch (ValidationException e) {
                                                    result.addErrorMessage(e.getMessage());
                                                }
                                            });
                        });

        return result;
    }

    private Object getValueForField(Object object, String fieldName) {
        try {
            Field field = object.getClass().
                    getDeclaredField(fieldName);

            // set accessible to true for private fields
            field.setAccessible(true);

            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Unable to access the field: " + fieldName, e);
        }
    }
}
