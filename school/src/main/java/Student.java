/**
 * Represents a student, containing information about their first and last name, grade, and student number.
 */
public class Student {
    private static int studentNumberCounter = 1;

    private String firstName;
    private String lastName;
    private int grade;
    private final int studentNumber;

    /**
     * Creates a new student.
     *
     * @param firstName First name of the student.
     * @param lastName  Last name of the student.
     * @param grade     The grade the student is in.
     */
    public Student(String firstName, String lastName, int grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade = grade;
        this.studentNumber = Student.studentNumberCounter++;
    }

    public int getStudentNumber() {
        return studentNumber;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    /**
     * Prints information about this student to standard output, using the format <code>Name: [full name] Grade: [grade]</code>.
     */
    public void print() {
        System.out.println("Name: " + firstName + " " + lastName + " Grade: " + grade);
    }
}
