package queue;

import java.util.ArrayList;
import java.util.List;

public class ArrayListADT<T> {
    protected List<T> list;

    public ArrayListADT() {
        this.list = new ArrayList<>();
    }

    public void add(T item) {
        list.add(item);
    }

    public T get(int index) {
        return list.get(index);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public List<T> getAll() {
        return new ArrayList<>(list); // Return a copy to prevent direct modifications
    }

    // Modify the remove method to return the removed item
    public T remove(int index) {
        if (index >= 0 && index < list.size()) {
            return list.remove(index); // Returns the element that was removed
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    public void clear() {
        list.clear();
    }
}
