package validation.rules;

import java.text.MessageFormat;
import java.util.Collection;

public class NonEmptyCollectionRule extends ValidationRule {
    private static final String ERROR_MESSAGE_FORMAT = "The collection {0} can not be empty";

    @Override
    public boolean isValid(Object val) {
        return val != null && ((Collection) val).size() != 0;
    }

    @Override
    public String getErrorMessage(String fieldName, Object val) {
        return MessageFormat.format(ERROR_MESSAGE_FORMAT, fieldName);
    }
}
