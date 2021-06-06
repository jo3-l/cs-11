package liberryan.validation;

public class ValidationError<F extends Enum<F>> {
    private final String message;
    private final F field;

    public ValidationError(String message, F field) {
        this.message = message;
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public F getField() {
        return field;
    }
}
