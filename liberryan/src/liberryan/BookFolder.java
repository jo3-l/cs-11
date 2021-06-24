package liberryan;

// An enumeration of book folders.
public enum BookFolder {
    WANT_TO_READ("Want to read"),
    CURRENTLY_READING("Currently reading"),
    ALREADY_READ("Already read");

    private final String name;

    BookFolder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
