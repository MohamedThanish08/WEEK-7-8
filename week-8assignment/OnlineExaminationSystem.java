import java.util.*;

abstract class Question {

    protected String questionText;
    protected int marks;

    Question(String questionText, int marks) {
        this.questionText = questionText;
        this.marks = marks;
    }

    abstract boolean evaluate(String answer);

    public int getMarks() {
        return marks;
    }

    public String getQuestionText() {
        return questionText;
    }
}

class MultipleChoiceQuestion extends Question {

    private String correctAnswer;

    MultipleChoiceQuestion(
        String questionText,
        String correctAnswer,
        int marks
    ) {
        super(questionText, marks);
        this.correctAnswer = correctAnswer;
    }

    @Override
    boolean evaluate(String answer) {
        return answer.equalsIgnoreCase(correctAnswer);
    }
}

class TrueFalseQuestion extends Question {

    private boolean correctAnswer;

    TrueFalseQuestion(
        String questionText,
        boolean correctAnswer,
        int marks
    ) {
        super(questionText, marks);
        this.correctAnswer = correctAnswer;
    }

    @Override
    boolean evaluate(String answer) {

        boolean userAnswer =
            Boolean.parseBoolean(answer);

        return userAnswer == correctAnswer;
    }
}

class ShortAnswerQuestion extends Question {

    private String correctAnswer;

    ShortAnswerQuestion(
        String questionText,
        String correctAnswer,
        int marks
    ) {
        super(questionText, marks);
        this.correctAnswer = correctAnswer;
    }

    @Override
    boolean evaluate(String answer) {
        return answer.equalsIgnoreCase(correctAnswer);
    }
}

class Student {

    String name;

    Student(String name) {
        this.name = name;
    }
}

class Examination {

    String examName;
    ArrayList<Question> questions;

    Examination(String examName) {
        this.examName = examName;
        questions = new ArrayList<>();
    }

    void addQuestion(Question question) {
        questions.add(question);
    }
}

class Attempt {

    Student student;
    Examination exam;
    ArrayList<String> answers;
    boolean submitted;

    Attempt(Student student, Examination exam) {
        this.student = student;
        this.exam = exam;
        this.answers = new ArrayList<>();
        this.submitted = false;
    }

    void start() {
        System.out.println(
            exam.examName + " started by " + student.name
        );
    }

    void answerQuestion(int questionNumber, String answer) {

        if (submitted) {
            System.out.println(
                "Cannot change answers for a submitted examination."
            );
            return;
        }

        while (answers.size() < questionNumber) {
            answers.add("");
        }

        answers.set(questionNumber - 1, answer);

        System.out.println(
            "Answer recorded for Question "
            + questionNumber
        );
    }

    void submit() {

        if (submitted) {
            System.out.println("Examination already submitted.");
            return;
        }

        submitted = true;

        int totalScore = 0;
        int totalMarks = 0;

        System.out.println(
            exam.examName
            + " submitted by "
            + student.name
        );

        for (int i = 0; i < exam.questions.size(); i++) {

            Question q = exam.questions.get(i);

            String answer = "";

            if (i < answers.size()) {
                answer = answers.get(i);
            }

            boolean correct = q.evaluate(answer);

            totalMarks += q.getMarks();

            if (correct) {
                totalScore += q.getMarks();

                System.out.println(
                    "Question " + (i + 1)
                    + ": Correct ("
                    + q.getMarks() + " points)"
                );
            } else {
                System.out.println(
                    "Question " + (i + 1)
                    + ": Incorrect (0 points)"
                );
            }
        }

        System.out.println(
            "Total score: "
            + totalScore
            + "/"
            + totalMarks
        );
    }
}

public class OnlineExaminationSystem {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.print("Enter student name: ");
        Student student = new Student(sc.nextLine());

        System.out.print("Enter examination name: ");
        Examination exam =
            new Examination(sc.nextLine());

        exam.addQuestion(
            new MultipleChoiceQuestion(
                "Which is a programming language?",
                "C",
                5
            )
        );

        exam.addQuestion(
            new TrueFalseQuestion(
                "Java is an OOP language. Enter true/false:",
                true,
                5
            )
        );

        Attempt attempt =
            new Attempt(student, exam);

        attempt.start();

        System.out.println(
            "\nQuestion 1: "
            + exam.questions.get(0).getQuestionText()
        );

        System.out.print("Enter answer: ");
        String answer1 = sc.nextLine();

        attempt.answerQuestion(1, answer1);

        System.out.println(
            "\nQuestion 2: "
            + exam.questions.get(1).getQuestionText()
        );

        System.out.print("Enter answer: ");
        String answer2 = sc.nextLine();

        attempt.answerQuestion(2, answer2);

        attempt.submit();

        System.out.println(
            "\nAttempting to change Question 1..."
        );

        attempt.answerQuestion(1, "B");

        sc.close();
    }
}