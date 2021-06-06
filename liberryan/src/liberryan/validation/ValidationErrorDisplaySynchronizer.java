package liberryan.validation;

import javafx.scene.control.TextField;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ValidationErrorDisplaySynchronizer<F extends Enum<F>> {
    private final Map<F, TextField> fields;

    public ValidationErrorDisplaySynchronizer(Class<F> fieldType) {
        fields = new EnumMap<>(fieldType);
    }

    public ValidationErrorDisplaySynchronizer<F> connectField(F field, TextField display) {
        fields.put(field, display);
        return this;
    }

    public void sync(List<ValidationError<F>> errors) {
        // clear all the previous errors, as they may no longer be applicable
        for (TextField field : fields.values()) field.clear();

        // add all the new validation errors
        for (ValidationError<F> err : errors) {
            TextField field = fields.get(err.getField());
            field.setText(err.getMessage());
        }
    }
}
