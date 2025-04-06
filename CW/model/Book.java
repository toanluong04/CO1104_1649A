package model;

public class Book {
    private String title;
    private String author;
    private double price;
    private int quantity;

    public Book(String title, String author, double price, int quantity) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void reduceQuantity(int qty) {
        if (qty <= quantity) quantity -= qty;
        else System.out.println("Not enough stock.");
    }

    public boolean inStock(int qty) {
        return qty <= quantity;
    }

    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    @Override
    public String toString() {
        return "(" + title + ", " + author + ", $" + price + ", Quantity: " + quantity + ")";
    }
}
