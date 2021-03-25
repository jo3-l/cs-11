import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a school, containing a list of teachers, students, and courses, in addition to a name, founding year,
 * and address.
 */
public class School {
    private final List<Teacher> teachers = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();
    // Note: Mentioned in instructions but never used.
    private final List<String> courses = new ArrayList<>();

    private String name;
    private Year foundingYear;
    private String address;

    /**
     * Creates a new school.
     *
     * @param name         Name of the school,
     * @param foundingYear The year the school was founded.
     * @param address      The address of the school.
     */
    public School(String name, Year foundingYear, String address) {
        this.name = name;
        this.foundingYear = foundingYear;
        this.address = address;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public List<Student> getStudents() {
        return students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Year getFoundingYear() {
        return foundingYear;
    }

    public void setFoundingYear(Year foundingYear) {
        this.foundingYear = foundingYear;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Adds a teacher to the list of registered teachers.
     *
     * @param teacher Teacher to add.
     */
    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    /**
     * Removes a teacher from the list of registered teachers.
     *
     * @param teacher Teacher to remove.
     */
    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
    }

    /**
     * Prints information about all teachers to standard output.
     */
    public void listTeachers() {
        for (Teacher teacher : teachers) teacher.print();
    }

    /**
     * Adds a student to the list of registered students.
     *
     * @param student Student to add.
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Removes a student from the list of registered students.
     *
     * @param student Student to remove.
     */
    public void removeStudent(Student student) {
        students.remove(student);
    }

    /**
     * Prints information about all students to standard output.
     */
    public void listStudents() {
        for (Student student : students) student.print();
    }
}
