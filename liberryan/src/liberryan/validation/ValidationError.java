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

    // Getters

    public String getMessage() {
        return message;
    }

    public F getField() {
        return field;
    }
}
