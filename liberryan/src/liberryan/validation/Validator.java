package liberryan.validation;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Validator<T, F extends Enum<F>> {
    private final List<Function<T, Optional<ValidationError<F>>>> conditions = new ArrayList<>();

    public List<ValidationError<F>> getErrors(T value) {
        return conditions
                .stream()
                .map(condition -> condition.apply(value))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Validator<T, F> addValidation(Predicate<T> predicate, String message, F field) {
        conditions.add(v -> predicate.test(v)
                ? Optional.of(new ValidationError<>(message, field)) // validation failed, return an error
                : Optional.empty() // validation succeeded, do nothing
        );
        return this;
    }
}
