package liberryan.validation;

import javafx.scene.control.TextField;

import java.util.EnumMap;
import java.util.Map;

public class ErrorNotificationSynchronizer<F extends Enum<F>> {
    private final Map<F, TextField> fields;

    public ErrorNotificationSynchronizer(Class<F> fieldType) {
        fields = new EnumMap<>(fieldType);
    }

    public ErrorNotificationSynchronizer<F> connectField(F field, TextField display) {
        fields.put(field, display);
        return this;
    }

    public void sync(ValidationErrorList<F> errors) {
        // clear all the previous errors, as they may no longer be applicable
        for (TextField field : fields.values()) field.clear();

        // add all the new validation errors
        for (ValidationError<F> violation : errors.getErrors()) {
            TextField field = fields.get(violation.getField());
            field.setText(violation.getMessage());
        }
    }
}
