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
    public void testValidateNullObject() {
        // execute
        final ValidationResult result = new FieldsValidator<>(null).validate();

        // verify
        assertNotNull(result);
        assertFalse(result.isValid());
        assertEquals(1, result.getErrorMessages().size());
        assertEquals("The input is null", result.getErrorMessages().get(0));
    }

    @Test
    public void testValidateFailed() {
        // setup
        final FooActivityRequest request = new FooActivityRequest();
        request.setText("");
        request.setItems(new ArrayList<>());

        // execute
        final ValidationResult result = new FieldsValidator<>(request)
                .with("num", new RequiredRule())
                .with("text", new NonEmptyStringRule())
                .with("text", new SupportedValuesRule<>(Arrays.asList(new String[]{"ABC", "CBS"})))
                .with("items", new NonEmptyCollectionRule())
                .validate();

        // verify
        assertNotNull(result);
        assertFalse(result.isValid());
        assertEquals(4, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().contains("The field num is required but not present"));
        assertTrue(result.getErrorMessages().contains("The text of field text can not be empty"));
        assertTrue(result.getErrorMessages().contains("The field text can only have values in (ABC, CBS) but found "));
        assertTrue(result.getErrorMessages().contains("The collection items can not be empty"));
    }

    @Test
    public void testValidateSuccessful() {
        // setup
        final FooActivityRequest request = new FooActivityRequest();
        request.setNum(100);
        request.setText("ABC");
        request.setItems(new ArrayList<>(Arrays.asList(new Double[]{ 0.01 })));

        // execute
        final ValidationResult result = new FieldsValidator<>(request)
                .with("num", new RequiredRule())
                .with("text", new NonEmptyStringRule())
                .with("text", new SupportedValuesRule<>(Arrays.asList(new String[]{"ABC", "CBS"})))
                .with("items", new NonEmptyCollectionRule())
                .validate();

        // verify
        assertNotNull(result);
        assertTrue(result.isValid());
        assertEquals(0, result.getErrorMessages().size());
    }
}
