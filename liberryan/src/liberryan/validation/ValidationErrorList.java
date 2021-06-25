package liberryan.validation;

import java.util.ArrayList;
import java.util.List;

// A list of validation errors.
// It is generic over F, which is an enum type representing all possible fields validation is applied to.
public class ValidationErrorList<F extends Enum<F>> {
    private final List<ValidationError<F>> errors = new ArrayList<>();

    public List<ValidationError<F>> getErrors() {
        return errors;
    }

    // Requires: F field - field the error is for, String message - error message.
    // Modifies: errors.
    // Effects: Adds a validation error for the given field and with the message provided to the internal list of errors.
    public void add(F field, String message) {
        ValidationError<F> error = new ValidationError<>(field, message);
        errors.add(error);
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Reports whether this validation error list has errors.
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
