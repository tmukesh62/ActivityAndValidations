package validation;

import activities.FooActivityRequest;
import org.junit.Test;
import validation.rules.NonEmptyCollectionRule;
import validation.rules.NonEmptyStringRule;
import validation.rules.RequiredRule;
import validation.rules.SupportedValuesRule;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class FieldValidatorTest {

    @Test
    public void testValidateFailed() {
        FooActivityRequest request = new FooActivityRequest();
        request.setText("");
        request.setItems(new ArrayList<>());

        ValidationResult result = new FieldsValidator<>(request)
                .with("num", new RequiredRule())
                .with("text", new NonEmptyStringRule())
                .with("text", new SupportedValuesRule<>(Arrays.asList(new String[]{"ABC", "CBS"})))
                .with("items", new NonEmptyCollectionRule())
                .validate();

        assertNotNull(result);
        assertFalse(result.isValid());
        assertEquals("The field num is required but not present", result.getErrorMessages().get(0));
        assertEquals("The text of field text can not be empty", result.getErrorMessages().get(1));
        assertEquals("The field text can only have values in (ABC, CBS) but found ",
                result.getErrorMessages().get(2));
        assertEquals("The collection items can not be empty", result.getErrorMessages().get(3));
    }

    @Test
    public void testValidateSuccessful() {
        FooActivityRequest request = new FooActivityRequest();
        request.setNum(100);
        request.setText("ABC");
        request.setItems(new ArrayList<>(Arrays.asList(new Double[]{ 0.01 })));

        ValidationResult result = new FieldsValidator<>(request)
                .with("num", new RequiredRule())
                .with("text", new NonEmptyStringRule())
                .with("text", new SupportedValuesRule<>(Arrays.asList(new String[]{"ABC", "CBS"})))
                .with("items", new NonEmptyCollectionRule())
                .validate();

        assertNotNull(result);
        assertTrue(result.isValid());
        assertEquals(0, result.getErrorMessages().size());
    }
}
