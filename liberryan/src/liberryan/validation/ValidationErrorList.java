package liberryan.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorList<F extends Enum<F>> {
    private final List<ValidationError<F>> errors = new ArrayList<>();

    public List<ValidationError<F>> getErrors() {
        return errors;
    }

    public void add(F field, String message) {
        ValidationError<F> error = new ValidationError<>(field, message);
        errors.add(error);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
