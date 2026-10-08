import java.util.*;

class Product {

    String name;
    double price;

    Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class OrderItem {

    Product product;
    int quantity;

    OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    double getTotal() {
        return product.price * quantity;
    }
}

interface PaymentMethod {

    boolean processPayment(double amount);
}

class CreditCardPayment implements PaymentMethod {

    @Override
    public boolean processPayment(double amount) {

        System.out.println(
            "Processing Credit Card payment of $"
            + amount
        );

        return true;
    }
}

class PayPalPayment implements PaymentMethod {

    @Override
    public boolean processPayment(double amount) {

        System.out.println(
            "Processing PayPal payment of $"
            + amount
        );

        // Simulating failed PayPal payment
        return false;
    }
}

class BankTransferPayment implements PaymentMethod {

    @Override
    public boolean processPayment(double amount) {

        System.out.println(
            "Processing Bank Transfer payment of $"
            + amount
        );

        return true;
    }
}

class Customer {

    String name;

    Customer(String name) {
        this.name = name;
    }
}

class Order {

    private Customer customer;
    private ArrayList<OrderItem> items;
    private String status;

    Order(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = "Pending";
    }

    void addProduct(Product product, int quantity) {

        if (quantity <= 0) {
            System.out.println(
                "Quantity must be greater than zero."
            );
            return;
        }

        items.add(
            new OrderItem(product, quantity)
        );

        System.out.println(
            product.name
            + " added to order."
        );
    }

    double calculateTotal() {

        double total = 0;

        for (OrderItem item : items) {
            total += item.getTotal();
        }

        return total;
    }

    boolean isEmpty() {
        return items.isEmpty();
    }

    void pay(PaymentMethod paymentMethod) {

        if (isEmpty()) {

            System.out.println(
                "Cannot process payment for an empty order."
            );

            return;
        }

        double amount = calculateTotal();

        System.out.println(
            "Payment initiated for "
            + customer.name
        );

        boolean success =
            paymentMethod.processPayment(amount);

        if (success) {

            status = "Paid";

            System.out.println(
                "Payment successful."
            );

        } else {

            System.out.println(
                "Payment failed."
            );
        }

        System.out.println(
            "Order status: " + status
        );
    }
}

public class PaymentProcessingSystem {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.print("Enter customer name: ");
        Customer customer =
            new Customer(sc.nextLine());

        Order order =
            new Order(customer);

        System.out.print("Enter number of products: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {

            sc.nextLine();

            System.out.print(
                "Enter product name: "
            );

            String productName =
                sc.nextLine();

            System.out.print(
                "Enter product price: "
            );

            double price =
                sc.nextDouble();

            System.out.print(
                "Enter quantity: "
            );

            int quantity =
                sc.nextInt();

            Product product =
                new Product(
                    productName,
                    price
                );

            order.addProduct(
                product,
                quantity
            );
        }

        System.out.println(
            "\nOrder total: $"
            + order.calculateTotal()
        );

        if (order.isEmpty()) {
            System.out.println(
                "Cannot process payment for an empty order."
            );

            sc.close();
            return;
        }

        System.out.println(
            "\nSelect payment method:"
        );

        System.out.println(
            "1. Credit Card"
        );

        System.out.println(
            "2. PayPal"
        );

        System.out.println(
            "3. Bank Transfer"
        );

        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        PaymentMethod paymentMethod;

        switch (choice) {

            case 1:
                paymentMethod =
                    new CreditCardPayment();
                break;

            case 2:
                paymentMethod =
                    new PayPalPayment();
                break;

            case 3:
                paymentMethod =
                    new BankTransferPayment();
                break;

            default:
                System.out.println(
                    "Invalid payment method."
                );

                sc.close();
                return;
        }

        order.pay(paymentMethod);

        sc.close();
    }
}