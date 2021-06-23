package liberryan;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

// Sometimes, we want to control the time that the time API uses in tests.
// The proper way to do this is to 1) use a third-party library like Mockito - not feasible for this project; or 2)
// use dependency injection to supply a clock which is passed to Instant.now() and so on. That's also not possible, so
// we do the next best thing: create a proxy over the time API we need that uses a clock we can configure.
public class Time {
    private static Clock clock = Clock.systemDefaultZone();

    // Requires: Instant instant.
    // Modifies: clock.
    // Effects: Sets the clock to use internally.
    public static void useFixedInstant(Instant instant) {
        clock = Clock.fixed(instant, ZoneId.systemDefault());
    }

    // Requires: Nothing.
    // Modifies: clock.
    // Effects: Resets the clock used internally to the system default time zone.
    // Useful for teardown after tests.
    public static void resetInternalClock() {
        clock = Clock.systemDefaultZone();
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the current instant using the clock set internally. Unless modified via Time.useFixedInstant(),
    // the clock used will be the system default time.
    public static Instant currentInstant() {
        return Instant.now(clock);
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the current local date using the clock set internally. Unless modified via Time.useFixedInstant(),
    // the clock used will be the system default time.
    public static LocalDate currentLocalDate() {
        return LocalDate.now(clock);
    }
}
