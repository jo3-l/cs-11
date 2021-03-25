/**
 * Represents a teacher, containing information about their first and last name in addition to the subject they teach.
 */
public class Teacher {
    private String firstName;
    private String lastName;
    private String subject;

    /**
     * Creates a new teacher.
     *
     * @param firstName First name of the teacher.
     * @param lastName  Last name of the subject.
     * @param subject   The subject taught by this teacher.
     */
    public Teacher(String firstName, String lastName, String subject) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Prints information about this teacher to standard output, using the format <code>Name: [full name] Subject: [subject]</code>.
     */
    public void print() {
        System.out.println("Name: " + this.firstName + " " + this.lastName + " Subject: " + subject);
    }
}
