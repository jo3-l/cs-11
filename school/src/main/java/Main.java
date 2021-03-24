import java.time.Year;

public class Main {
    public static void main(String[] args) {
        School churchill = new School("Sir Winston Churchill Secondary", Year.of(1956), "7055 Heather St., Vancouver, BC");

        Student chris = new Student("Christopher", "Finch", 9);
        Student james = new Student("James", "Wheat", 11);
        Student eleanor = new Student("Eleanor", "Thompson", 10);
        Student eric = new Student("Eric", "Gorbunov", 8);
        Student arthur = new Student("Arthur", "Abramov", 12);
        Student lucas = new Student("Lucas", "Trieux", 11);
        Student victoria = new Student("Victoria", "Ferreira", 10);
        Student emma = new Student("Emma", "Svendsen", 8);
        Student elise = new Student("Elise", "Stevens", 9);
        Student billy = new Student("Billy", "Coleman", 10);

        churchill.addStudent(chris);
        churchill.addStudent(james);
        churchill.addStudent(eleanor);
        churchill.addStudent(eric);
        churchill.addStudent(arthur);
        churchill.addStudent(lucas);
        churchill.addStudent(victoria);
        churchill.addStudent(emma);
        churchill.addStudent(elise);
        churchill.addStudent(billy);

        Teacher anthony = new Teacher("Anthony", "Hutchinson", "Physics");
        Teacher lucy = new Teacher("Lucy", "Kennedy", "Physical Health & Education");
        Teacher francesa = new Teacher("Francesa", "Hammond", "Language Arts");

        churchill.addTeacher(anthony);
        churchill.addTeacher(lucy);
        churchill.addTeacher(francesa);

        System.out.println("Teachers:");
        churchill.listTeachers();

        System.out.println("\nStudents:");
        churchill.listStudents();

        churchill.removeStudent(arthur);
        churchill.removeStudent(emma);

        churchill.removeTeacher(anthony);

        System.out.println("\nAfter removing Arthur & Emma (students) and Anthony (teacher):");
        System.out.println("Teachers:");
        churchill.listTeachers();

        System.out.println("\nStudents:");
        churchill.listStudents();
    }
}
