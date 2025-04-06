package system;

import model.Book;
import model.Order;
import model.PreOrder;
import queue.ArrayListADT;
import queue.OrderQueue;

import java.util.*;
import java.util.stream.Collectors;

public class OrderProcessingSystem {
    private static List<Book> availableBooks = new ArrayList<>(Arrays.asList(
            new Book("The Great Gatsby", "F. Scott Fitzgerald", 15.99, 10),
            new Book("Ulysses", "James Joyce", 9.99, 8),
            new Book("In Search of Lost Time", "Marcel Proust", 12.50, 5),
            new Book("The Catcher in the Rye", "J. D. Salinger", 20.00, 7)
    ));

    private static Map<Integer, Order> ordersMap = new HashMap<>();
    private static OrderQueue orderQueue = new OrderQueue();
    private static int orderCounter = 1;
    private static ArrayListADT<PreOrder> preOrders = new ArrayListADT<>();

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Browse Books\n2. Place Order\n3. Process Orders\n4. Search Order");
            System.out.println("5. Pre-order Book\n6. Cancel Pre-order\n7. Exit");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> browseBooks();
                case "2" -> placeOrder(scanner);
                case "3" -> processOrders();
                case "4" -> searchOrder(scanner);
                case "5" -> preOrderBook(scanner);
                case "6" -> cancelPreOrder(scanner);
                case "7" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void browseBooks() {
        System.out.println("Available Books:");
        availableBooks.forEach(System.out::println);
    }

    private static void placeOrder(Scanner scanner) {
        System.out.print("Customer Name: ");
        String customer = scanner.nextLine();
        System.out.print("Shipping Address: ");
        String address = scanner.nextLine();

        List<Book> orderBooks = new ArrayList<>();

        while (true) {
            System.out.println("Search by: 1. Title 2. Author 3. Cancel");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 3) return;

            System.out.print("Enter search term: ");
            String term = scanner.nextLine().toLowerCase();
            List<Book> results = searchBooks(choice, term);

            if (results.isEmpty()) {
                System.out.println("No books found.");
                continue;
            }

            results.forEach(System.out::println);
            System.out.print("Enter exact book title: ");
            String title = scanner.nextLine();

            int qty = 0;
            while (true) {
                System.out.print("Enter quantity: ");
                try {
                    qty = Integer.parseInt(scanner.nextLine());
                    if (qty <= 0) {
                        System.out.println("Invalid quantity. Quantity must be more than 0.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number for quantity.");
                }
            }

            Optional<Book> match = availableBooks.stream()
                    .filter(b -> b.getTitle().equalsIgnoreCase(title))
                    .findFirst();

            if (match.isPresent()) {
                Book b = match.get();
                if (b.getQuantity() >= qty) {
                    b.reduceQuantity(qty);
                    orderBooks.add(new Book(b.getTitle(), b.getAuthor(), b.getPrice(), qty));
                    break;
                } else {
                    System.out.println("Not enough stock. Available quantity: " + b.getQuantity());
                }
            } else {
                System.out.println("Book not found.");
            }
        }

        Order newOrder = new Order(orderCounter++, customer, address, orderBooks, 1);
        ordersMap.put(newOrder.getOrderId(), newOrder);
        orderQueue.enqueue(newOrder);
        System.out.println("Order placed: " + newOrder);
    }

    private static List<Book> searchBooks(int type, String term) {
        return availableBooks.stream()
                .filter(b -> (type == 1 ? b.getTitle() : b.getAuthor()).toLowerCase().contains(term))
                .collect(Collectors.toList());
    }

    private static void processOrders() {
        if (orderQueue.isEmpty()) {
            System.out.println("No orders to process.");
            return;
        }

        // Loop through each order in the queue and process it
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.dequeue(); // Get the first order in the queue
            System.out.println("Processing Order: " + order.getOrderId());

            // Display the books in the order in the correct order
            System.out.println("Books in this order:");
            for (Book book : order.getBooks()) {
                System.out.println(book);
            }

            // Display the total order value
            double totalValue = order.getTotalOrderValue();
            System.out.println("Total Order Value: $" + totalValue);

            // Perform processing (e.g., updating inventory, etc.)
            // (You can add more logic here if necessary)
            System.out.println("Order " + order.getOrderId() + " has been processed.\n");
        }

        System.out.println("All orders have been processed.");
    }



    private static void searchOrder(Scanner scanner) {
        System.out.print("Enter Order ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Order o = ordersMap.get(id);
        System.out.println(o != null ? "Order Found: " + o : "Order not found.");
    }

    private static void preOrderBook(Scanner scanner) {
        System.out.print("Customer Name: ");
        String customer = scanner.nextLine();
        System.out.print("Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        int qty = 0;

        while (true) {
            System.out.print("Enter quantity: ");
            try {
                qty = Integer.parseInt(scanner.nextLine());
                if (qty <= 0) {
                    System.out.println("Invalid quantity. Quantity must be more than 0.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for quantity.");
            }
        }

        PreOrder newPreOrder = new PreOrder(customer, title, author, qty);
        preOrders.add(newPreOrder);
        System.out.println("Pre-order placed: " + newPreOrder);
    }

    private static void cancelPreOrder(Scanner scanner) {
        System.out.print("Enter Customer Name to cancel Pre-order: ");
        String customerName = scanner.nextLine();
        boolean found = false;

        for (int i = 0; i < preOrders.size(); i++) {
            PreOrder preOrder = preOrders.get(i);
            if (preOrder.getCustomerName().equalsIgnoreCase(customerName)) {
                preOrders.remove(i);
                System.out.println("Pre-order cancelled: " + preOrder);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No pre-order found for customer: " + customerName);
        }
    }
}
