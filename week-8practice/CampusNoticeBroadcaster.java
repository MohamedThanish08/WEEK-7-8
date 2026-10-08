import java.util.*;

class CampusStudent {
    private String name;
    private String department;
    private List<NotificationChannel> channels;

    public CampusStudent(String name, String department) {
        this.name = name;
        this.department = department;
        this.channels = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public void addChannel(NotificationChannel channel) {
        channels.add(channel);
    }

    public List<NotificationChannel> getChannels() {
        return channels;
    }
}

interface NotificationChannel {
    void send(CampusStudent student, Notice notice);
}

class EmailChannel implements NotificationChannel {

    @Override
    public void send(CampusStudent student, Notice notice) {

        System.out.println(
                "[Email → " +
                student.getName() +
                "] " +
                notice.getTitle()
        );
    }
}

class SmsChannel implements NotificationChannel {

    @Override
    public void send(CampusStudent student, Notice notice) {

        System.out.println(
                "[SMS → " +
                student.getName() +
                "] " +
                notice.getTitle()
        );
    }
}

class AppChannel implements NotificationChannel {

    @Override
    public void send(CampusStudent student, Notice notice) {

        System.out.println(
                "[App → " +
                student.getName() +
                "] " +
                notice.getTitle()
        );
    }
}

class Notice {

    private String title;
    private Set<String> targetDepartments;

    public Notice(String title, Set<String> targetDepartments) {

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Notice title cannot be empty."
            );
        }

        if (targetDepartments == null
                || targetDepartments.isEmpty()) {

            throw new IllegalArgumentException(
                    "At least one target department is required."
            );
        }

        this.title = title;
        this.targetDepartments =
                new HashSet<>(targetDepartments);
    }

    public String getTitle() {
        return title;
    }

    public Set<String> getTargetDepartments() {
        return targetDepartments;
    }
}

class NoticeBoard {

    private List<CampusStudent> students;

    public NoticeBoard() {
        students = new ArrayList<>();
    }

    public void addStudent(CampusStudent student) {
        students.add(student);
    }

    public void postNotice(Notice notice) {

        System.out.println(
                "\nNotice '" +
                notice.getTitle() +
                "' posted to " +
                String.join(
                        ", ",
                        notice.getTargetDepartments()
                ) + "."
        );

        for (CampusStudent student : students) {

            if (notice.getTargetDepartments()
                    .contains(student.getDepartment())) {

                for (NotificationChannel channel
                        : student.getChannels()) {

                    channel.send(student, notice);
                }
            }
        }
    }
}

public class CampusNoticeBroadcaster {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        NoticeBoard board = new NoticeBoard();

        System.out.print("Enter number of students: ");
        int numberOfStudents = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < numberOfStudents; i++) {

            System.out.print("\nEnter student name: ");
            String name = sc.nextLine();

            System.out.print("Enter department: ");
            String department = sc.nextLine();

                CampusStudent student =
                    new CampusStudent(name, department);

            System.out.println(
                    "Choose preferred channels:"
            );

            System.out.println("1. Email");
            System.out.println("2. SMS");
            System.out.println("3. App");

            System.out.print(
                    "Enter number of channels: "
            );

            int channelCount = sc.nextInt();

            for (int j = 0; j < channelCount; j++) {

                System.out.print(
                        "Enter channel choice: "
                );

                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        student.addChannel(
                                new EmailChannel()
                        );
                        break;

                    case 2:
                        student.addChannel(
                                new SmsChannel()
                        );
                        break;

                    case 3:
                        student.addChannel(
                                new AppChannel()
                        );
                        break;

                    default:
                        System.out.println(
                                "Invalid channel."
                        );
                }
            }

            sc.nextLine();
            board.addStudent(student);
        }

        System.out.print(
                "\nEnter notice title: "
        );

        String title = sc.nextLine();

        System.out.print(
                "Enter number of target departments: "
        );

        int departmentCount = sc.nextInt();
        sc.nextLine();

        Set<String> departments = new HashSet<>();

        for (int i = 0; i < departmentCount; i++) {

            System.out.print(
                    "Enter department: "
            );

            departments.add(sc.nextLine());
        }

        try {

            Notice notice =
                    new Notice(title, departments);

            board.postNotice(notice);

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Cannot post notice: " +
                    e.getMessage()
            );
        }

        sc.close();
    }
}