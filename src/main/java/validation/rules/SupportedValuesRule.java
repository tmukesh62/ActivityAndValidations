package validation.rules;

import java.text.MessageFormat;
import java.util.Collection;

public class SupportedValuesRule<T extends Object> extends ValidationRule {
    private static final String ERROR_MESSAGE_FORMAT = "The field {0} can only have values in ({1}) but found {2}";

    private final Collection supportedValues;

    public SupportedValuesRule(Collection<T> supportedValues) {
        this.supportedValues = supportedValues;
    }

    @Override
    public boolean isValid(Object val) {
        return supportedValues.stream().findAny().filter(supportedValue -> supportedValue == (T) val).isPresent();
    }

    @Override
    public String getErrorMessage(String fieldName, Object val) {
        return MessageFormat.format(ERROR_MESSAGE_FORMAT, fieldName, String.join(", ", supportedValues), val);
    }
}
