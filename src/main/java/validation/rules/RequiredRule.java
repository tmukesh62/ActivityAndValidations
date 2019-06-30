package validation.rules;

import java.text.MessageFormat;

public class RequiredRule extends ValidationRule {
    private static final String ERROR_MESSAGE_FORMAT = "The field {0} is required but not present";

    @Override
    public boolean isValid(Object val) {
        return val != null;
    }

    @Override
    public String getErrorMessage(String fieldName, Object val) {
        return MessageFormat.format(ERROR_MESSAGE_FORMAT, fieldName);
    }
}
