public class Student {
    private static int studentNumberCounter = 1;

    private String firstName;
    private String lastName;
    private int grade;
    private final int studentNumber;

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

    public void print() {
        System.out.println("Name: " + firstName + " " + lastName + " | Grade: " + grade);
    }
}
