package liberryan.validation;

public class ValidationError<F extends Enum<F>> {
    private final String message;
    private final F field;

    public ValidationError(F field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public F getField() {
        return field;
    }
}
