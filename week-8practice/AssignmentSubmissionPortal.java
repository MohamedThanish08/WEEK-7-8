import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

abstract class Assignment {
    private String title;
    private double maxMarks;
    private LocalDate dueDate;

    public Assignment(String title, double maxMarks, LocalDate dueDate) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public double getMaxMarks() {
        return maxMarks;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public abstract double calculateFinalMarks(double awardedMarks, long lateDays);
}

class CodingAssignment extends Assignment {

    public CodingAssignment(String title, double maxMarks, LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    @Override
    public double calculateFinalMarks(double awardedMarks, long lateDays) {
        double penalty = lateDays * 0.10;
        return awardedMarks * (1 - penalty);
    }
}

class WrittenAssignment extends Assignment {

    public WrittenAssignment(String title, double maxMarks, LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    @Override
    public double calculateFinalMarks(double awardedMarks, long lateDays) {
        double penalty = lateDays * 0.20;
        return awardedMarks * (1 - penalty);
    }
}

class AssignmentStudent {
    private String name;

    public AssignmentStudent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

enum SubmissionStatus {
    SUBMITTED,
    GRADED
}

class Submission {
    private AssignmentStudent student;
    private Assignment assignment;
    private LocalDate submissionDate;
    private SubmissionStatus status;
    private double finalMarks;

    public Submission(AssignmentStudent student, Assignment assignment,
                      LocalDate submissionDate) {

        this.student = student;
        this.assignment = assignment;
        this.submissionDate = submissionDate;
        this.status = SubmissionStatus.SUBMITTED;
    }

    public void grade(double awardedMarks) {

        if (status == SubmissionStatus.GRADED) {
            System.out.println("Cannot grade again.");
            return;
        }

        long lateDays = 0;

        if (submissionDate.isAfter(assignment.getDueDate())) {
            lateDays = ChronoUnit.DAYS.between(
                    assignment.getDueDate(),
                    submissionDate
            );
        }

        finalMarks = assignment.calculateFinalMarks(
                awardedMarks,
                lateDays
        );

        if (finalMarks < 0) {
            finalMarks = 0;
        }

        status = SubmissionStatus.GRADED;

        System.out.printf(
                "%s graded: %.2f/%.2f%n",
                student.getName(),
                finalMarks,
                assignment.getMaxMarks()
        );

        if (lateDays > 0) {
            System.out.println(
                    "Late by " + lateDays + " day(s)."
            );
        }

        System.out.println("Status: Graded.");
    }

    public void resubmit(LocalDate newDate) {

        if (status == SubmissionStatus.GRADED) {
            System.out.println(
                    "Cannot resubmit: '" +
                    assignment.getTitle() +
                    "' has already been graded."
            );
            return;
        }

        submissionDate = newDate;
        System.out.println("Submission updated.");
    }

    public void displaySubmission() {

        long lateDays = 0;

        if (submissionDate.isAfter(assignment.getDueDate())) {
            lateDays = ChronoUnit.DAYS.between(
                    assignment.getDueDate(),
                    submissionDate
            );
        }

        System.out.println(
                student.getName() +
                "'s submission for '" +
                assignment.getTitle() +
                "' received."
        );

        if (lateDays == 0) {
            System.out.println("(on time)");
        } else {
            System.out.println(
                    "(" + lateDays + " days late)"
            );
        }

        System.out.println("Status: " + status);
    }
}

public class AssignmentSubmissionPortal {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter student name: ");
        String studentName = sc.nextLine();

        AssignmentStudent student = new AssignmentStudent(studentName);

        System.out.print("Enter assignment title: ");
        String title = sc.nextLine();

        System.out.print("Enter assignment type (1-Coding, 2-Written): ");
        int type = sc.nextInt();

        System.out.print("Enter maximum marks: ");
        double maxMarks = sc.nextDouble();

        System.out.print("Enter due date (yyyy-mm-dd): ");
        LocalDate dueDate = LocalDate.parse(sc.next());

        Assignment assignment;

        if (type == 1) {
            assignment = new CodingAssignment(
                    title,
                    maxMarks,
                    dueDate
            );
        } else if (type == 2) {
            assignment = new WrittenAssignment(
                    title,
                    maxMarks,
                    dueDate
            );
        } else {
            System.out.println("Invalid assignment type.");
            sc.close();
            return;
        }

        System.out.print("Enter submission date (yyyy-mm-dd): ");
        LocalDate submissionDate = LocalDate.parse(sc.next());

        Submission submission = new Submission(
                student,
                assignment,
                submissionDate
        );

        submission.displaySubmission();

        System.out.print("Enter awarded marks: ");
        double marks = sc.nextDouble();

        submission.grade(marks);

        System.out.println("\nAttempting resubmission...");

        System.out.print("Enter new submission date (yyyy-mm-dd): ");
        LocalDate newDate = LocalDate.parse(sc.next());

        submission.resubmit(newDate);

        sc.close();
    }
}