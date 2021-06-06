package liberryan;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

// Sometimes, we want to control the time that Java's time API uses in tests.
// The proper way to do this is to 1) use a third-party library like Mockito - not feasible for this project; or 2)
// use dependency injection to supply a clock which is passed to Instant.now() and so on. That's also not possible, so
// we do the next best thing: use a wrapper over the time API constructors that uses a clock we can configure.
public class Time {
    private static Clock clock = Clock.systemDefaultZone();

    public static void useFixedInstant(Instant instant) {
        clock = Clock.fixed(instant, ZoneId.systemDefault());
    }

    public static Instant currentInstant() {
        return Instant.now(clock);
    }

    public static LocalDate currentLocalDate() {
        return LocalDate.now(clock);
    }
}
