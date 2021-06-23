package liberryan.validation;

// A validation error.
// It is generic over F, which is an enum type representing all possible fields validation is applied to.
public class ValidationError<F extends Enum<F>> {
    private final String message;
    private final F field;

    public ValidationError(F field, String message) {
        this.field = field;
        this.message = message;
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the validation error message.
    public String getMessage() {
        return message;
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the field which caused this validation error.
    public F getField() {
        return field;
    }
}
