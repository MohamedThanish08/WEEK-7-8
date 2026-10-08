import java.util.Scanner;

interface MembershipPlan {
    String getPlanName();
    int getMonths();
    double calculateFee();
}

class MonthlyPlan implements MembershipPlan {

    public String getPlanName() {
        return "Monthly";
    }

    public int getMonths() {
        return 1;
    }

    public double calculateFee() {
        return 1000.0;
    }
}

class QuarterlyPlan implements MembershipPlan {

    public String getPlanName() {
        return "Quarterly";
    }

    public int getMonths() {
        return 3;
    }

    public double calculateFee() {
        return 1000.0 * 3 * 0.90;
    }
}

class AnnualPlan implements MembershipPlan {

    public String getPlanName() {
        return "Annual";
    }

    public int getMonths() {
        return 12;
    }

    public double calculateFee() {
        return 1000.0 * 12 * 0.75;
    }
}

class Member {
    private String name;

    public Member(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

enum MembershipStatus {
    ACTIVE,
    FROZEN,
    EXPIRED
}

class Membership {

    private Member member;
    private MembershipPlan plan;
    private MembershipStatus status;

    public Membership(Member member, MembershipPlan plan) {
        this.member = member;
        this.plan = plan;
        this.status = MembershipStatus.ACTIVE;
    }

    public void displayDetails() {

        System.out.println(
                plan.getPlanName() +
                " membership created for " +
                member.getName() + "."
        );

        System.out.printf(
                "Fee: ₹%.2f%n",
                plan.calculateFee()
        );

        System.out.println(
                "Status: " + status
        );
    }

    public void checkIn() {

        if (status == MembershipStatus.ACTIVE) {

            System.out.println(
                    member.getName() +
                    " checked in successfully."
            );

        } else {

            System.out.println(
                    "Check-in denied: " +
                    member.getName() +
                    "'s membership is " +
                    status + "."
            );
        }
    }

    public void freeze() {

        if (status == MembershipStatus.EXPIRED) {

            System.out.println(
                    "Cannot freeze an Expired membership."
            );

        } else if (status == MembershipStatus.FROZEN) {

            System.out.println(
                    "Membership is already Frozen."
            );

        } else {

            status = MembershipStatus.FROZEN;

            System.out.println(
                    member.getName() +
                    "'s membership frozen."
            );

            System.out.println(
                    "Status: " + status
            );
        }
    }

    public void unfreeze() {

        if (status == MembershipStatus.EXPIRED) {

            System.out.println(
                    "Cannot unfreeze an Expired membership."
            );

        } else if (status == MembershipStatus.ACTIVE) {

            System.out.println(
                    "Membership is already Active."
            );

        } else {

            status = MembershipStatus.ACTIVE;

            System.out.println(
                    member.getName() +
                    "'s membership unfrozen."
            );

            System.out.println(
                    "Status: " + status
            );
        }
    }

    public void expire() {

        status = MembershipStatus.EXPIRED;

        System.out.println(
                member.getName() +
                "'s membership expired."
        );

        System.out.println(
                "Status: " + status
        );
    }
}

public class FitZoneMembershipDesk {

    public static MembershipPlan choosePlan(int choice) {

        switch (choice) {

            case 1:
                return new MonthlyPlan();

            case 2:
                return new QuarterlyPlan();

            case 3:
                return new AnnualPlan();

            default:
                return null;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter member name: ");
        String name = sc.nextLine();

        Member member = new Member(name);

        System.out.println("\nChoose membership plan:");
        System.out.println("1. Monthly - ₹1000");
        System.out.println("2. Quarterly - ₹2700");
        System.out.println("3. Annual - ₹9000");

        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        MembershipPlan plan = choosePlan(choice);

        if (plan == null) {
            System.out.println("Invalid plan.");
            sc.close();
            return;
        }

        Membership membership =
                new Membership(member, plan);

        System.out.println();
        membership.displayDetails();

        System.out.println("\nChecking in...");
        membership.checkIn();

        System.out.println("\nFreezing membership...");
        membership.freeze();

        System.out.println("\nTrying to check in...");
        membership.checkIn();

        System.out.println("\nUnfreezing membership...");
        membership.unfreeze();

        System.out.println("\nChecking in again...");
        membership.checkIn();

        System.out.println("\nExpiring membership...");
        membership.expire();

        System.out.println("\nTrying to freeze expired membership...");
        membership.freeze();

        System.out.println("\nTrying to check in after expiry...");
        membership.checkIn();

        sc.close();
    }
}