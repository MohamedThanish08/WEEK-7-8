import java.util.*;

abstract class Employee {
    protected String name;

    Employee(String name) {
        this.name = name;
    }

    abstract boolean canTakeLeave(int days);

    public String getName() {
        return name;
    }
}

class FullTimeEmployee extends Employee {

    FullTimeEmployee(String name) {
        super(name);
    }

    @Override
    boolean canTakeLeave(int days) {
        return days <= 30;
    }
}

class PartTimeEmployee extends Employee {

    PartTimeEmployee(String name) {
        super(name);
    }

    @Override
    boolean canTakeLeave(int days) {
        return days <= 10;
    }
}

class Contractor extends Employee {

    Contractor(String name) {
        super(name);
    }

    @Override
    boolean canTakeLeave(int days) {
        return days <= 5;
    }
}

enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED
}

class LeaveRequest {

    private Employee employee;
    private String startDate;
    private String endDate;
    private int days;
    private LeaveStatus status;

    LeaveRequest(
        Employee employee,
        String startDate,
        String endDate,
        int days
    ) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
        this.status = LeaveStatus.PENDING;
    }

    public void approve() {

        if (status != LeaveStatus.PENDING) {
            System.out.println(
                "Cannot approve a request that is already " + status
            );
            return;
        }

        if (!employee.canTakeLeave(days)) {
            System.out.println(
                "Leave policy does not allow " + days + " days."
            );
            return;
        }

        status = LeaveStatus.APPROVED;

        System.out.println(
            employee.getName()
            + "'s leave request ("
            + startDate + " - " + endDate
            + ") approved."
        );
    }

    public void reject() {

        if (status != LeaveStatus.PENDING) {
            System.out.println(
                "Cannot reject a request that is already " + status
            );
            return;
        }

        status = LeaveStatus.REJECTED;

        System.out.println(
            employee.getName()
            + "'s leave request ("
            + startDate + " - " + endDate
            + ") rejected."
        );
    }

    public void changeStatus(LeaveStatus newStatus) {

        if (status != LeaveStatus.PENDING) {
            System.out.println(
                "Cannot change leave request status from "
                + status + " to " + newStatus
            );
            return;
        }

        status = newStatus;
    }

    public LeaveStatus getStatus() {
        return status;
    }
}

public class EmployeeLeaveSystem {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.print("Enter full-time employee name: ");
        String name1 = sc.nextLine();

        Employee john = new FullTimeEmployee(name1);

        System.out.print("Enter leave start date: ");
        String start1 = sc.nextLine();

        System.out.print("Enter leave end date: ");
        String end1 = sc.nextLine();

        System.out.print("Enter number of leave days: ");
        int days1 = sc.nextInt();
        sc.nextLine();

        LeaveRequest request1 =
            new LeaveRequest(john, start1, end1, days1);

        System.out.println(
            "Leave request submitted for "
            + john.getName()
            + ". Status: " + request1.getStatus()
        );

        System.out.print("Approve or Reject? ");
        String decision = sc.nextLine();

        if (decision.equalsIgnoreCase("approve")) {
            request1.approve();
        } else if (decision.equalsIgnoreCase("reject")) {
            request1.reject();
        }

        System.out.println(
            "Status: " + request1.getStatus()
        );

        System.out.print(
            "Attempt to change status to Pending? (yes/no): "
        );

        String answer = sc.nextLine();

        if (answer.equalsIgnoreCase("yes")) {
            request1.changeStatus(LeaveStatus.PENDING);
        }

        sc.close();
    }
}