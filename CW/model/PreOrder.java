package model;

public class PreOrder {
    private String customerName;
    private String bookTitle;
    private String author;
    private int quantity;

    public PreOrder(String customerName, String bookTitle, String author, int quantity) {
        this.customerName = customerName;
        this.bookTitle = bookTitle;
        this.author = author;
        this.quantity = quantity;
    }

    public String getCustomerName() { return customerName; }
    public String getBookTitle() { return bookTitle; }
    public String getAuthor() { return author; }
    public int getQuantity() { return quantity; }

    // Thêm phương thức để kiểm tra khách hàng
    public boolean matchesCustomer(String customerName) {
        return this.customerName.equalsIgnoreCase(customerName);
    }

    @Override
    public String toString() {
        return "PreOrder{" +
                "customerName='" + customerName + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
