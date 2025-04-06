package queue;

import model.Order;

public class OrderQueue {
    private ArrayListADT<Order> queue = new ArrayListADT<>();

    public void enqueue(Order order) {
        queue.add(order);
    }

    public Order dequeue() {
        if (!queue.isEmpty()) {
            return queue.remove(0); // Now remove and return the first element
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
