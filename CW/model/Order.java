package model;

import java.util.List;

public class Order implements Comparable<Order> {
    private int orderId;
    private String customerName;
    private String shippingAddress;
    private List<Book> books;
    private int priority;

    public Order(int orderId, String customerName, String shippingAddress, List<Book> books, int priority) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.books = books;
        this.priority = priority;
    }

    public int getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public String getShippingAddress() { return shippingAddress; }
    public List<Book> getBooks() { return books; }
    public int getPriority() { return priority; }

    public double getTotalOrderValue() {
        return books.stream()
                .mapToDouble(book -> book.getPrice() * book.getQuantity())
                .sum();
    }

    @Override
    public String toString() {
        return "Order(" + orderId + ", " + customerName + ", " + shippingAddress + ", Books: " + books + ", Priority: " + priority + ")";
    }

    @Override
    public int compareTo(Order other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(orderId);
    }
}
