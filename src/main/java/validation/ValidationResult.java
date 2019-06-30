package validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private boolean valid;
    private final List<String> errorMessages;

    public ValidationResult() {
        valid = true;
        errorMessages = new ArrayList<>();
    }

    public boolean isValid() {
        return valid;
    }

    public void addErrorMessage(final String errorMessage) {
        errorMessages.add(errorMessage);

        valid = false;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
                "valid=" + valid +
                ", errorMessages=" + errorMessages +
                '}';
    }
}
