package validation.rules;

import java.text.MessageFormat;

public class NonEmptyStringRule extends ValidationRule {
    private static final String ERROR_MESSAGE_FORMAT = "The text of field {0} can not be empty";

    @Override
    public boolean isValid(Object val) {
        return val != null &&  val != "";
    }

    @Override
    public String getErrorMessage(String fieldName, Object val) {
        return MessageFormat.format(ERROR_MESSAGE_FORMAT, fieldName);
    }
}
