import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class School {
    private final List<Teacher> teachers = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();
    // Note: Mentioned in instructions but never used.
    private final List<String> courses = new ArrayList<>();

    private String name;
    private Year foundingYear;
    private String address;

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

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
    }

    public void listTeachers() {
        for (Teacher teacher : teachers) teacher.print();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public void listStudents() {
        for (Student student : students) student.print();
    }
}
