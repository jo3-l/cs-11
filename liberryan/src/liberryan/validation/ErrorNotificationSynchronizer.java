package liberryan.validation;

import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

// Synchronizes validation errors with their state in the GUI.
// It is generic over F, which is an enum of all the possible fields validation is applied to.
public class ErrorNotificationSynchronizer<F extends Enum<F>> {
    // Connects fields validation is applied to to text fields where errors should be displayed.
    private final List<ErrorNotificationMapping<F>> connections = new ArrayList<>();

    // Requires: F field, TextField display.
    // Modifies: this, fields.
    // Effects: Connects the given field to a text field where errors corresponding to that field should be displayed.
    public ErrorNotificationSynchronizer<F> connectField(F field, Text display) {
        connections.add(new ErrorNotificationMapping<>(field, display));
        return this;
    }

    // Requires: ValidationErrorList<F> errors.
    // Modifies: Nothing.
    // Effects: Syncs the state of the errors in the GUI with the validation errors provided. More specifically, errors
    // which are displayed in the GUI but are no longer part of errors will be hidden. The same goes for new errors which
    // are not yet displayed in the GUI.
    public void sync(ValidationErrorList<F> errors) {
        // clear all the previous errors, as they may no longer be applicable
        for (ErrorNotificationMapping<F> mapping : connections) mapping.getTextField().setText(null);

        // add all the new validation errors
        for (ValidationError<F> error : errors.getErrors()) {
            // Find the text field where errors should be displayed.
            Text textField = connections.stream()
                    .filter(mapping -> mapping.getField() == error.getField())
                    .map(ErrorNotificationMapping::getTextField)
                    .findFirst()
                    .get();
            textField.setText(error.getMessage());
        }
    }

    // A tuple of field validation is applied to to the text field where errors should be displayed.
    private static class ErrorNotificationMapping<F extends Enum<F>> {
        private final F field;
        private final Text textField;

        private ErrorNotificationMapping(F field, Text textField) {
            this.field = field;
            this.textField = textField;
        }

        // Requires: Nothing.
        // Modifies: Nothing.
        // Effects: Returns the field which this mapping is for.
        public F getField() {
            return field;
        }

        // Requires: Nothing.
        // Modifies: Nothing.
        // Effects: Returns the text field where errors should be displayed.
        public Text getTextField() {
            return textField;
        }
    }
}
