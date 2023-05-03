package priorityqueues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @see ExtrinsicMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 0;
    List<PriorityNode<T>> items;
    HashMap<T, Integer> map;


    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        map = new HashMap<>();
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.
    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {

        map.put(items.get(a).getItem(), b);
        map.put(items.get(b).getItem(), a);
        PriorityNode<T> toSwap = items.get(a);
        items.set(a, items.get(b));
        items.set(b, toSwap);
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        items.add(new PriorityNode<>(item, priority));
        map.put(item, items.size() - 1);
        percolateUp(items.size() - 1);
    }


    @Override
    public boolean contains(T item) {
        return map.containsKey(item);
    }

    @Override
    public T peekMin() {
        if (items.size() < 1) {
            throw new NoSuchElementException();
        }
        return items.get(0).getItem();
    }

    @Override
    public T removeMin() {
        if (items.size() < 1) {
            throw new NoSuchElementException();
        }
        if (items.size() == 1) {
            return items.remove(0).getItem();
        }
        swap(0, items.size() - 1);
        T ret = items.remove(items.size() - 1).getItem();
        map.remove(ret);
        percolateDown(0);
        return ret;
    }

    private void percolateUp(int i) {
        if (items.get(i).getPriority() < items.get((i - 1) / 2).getPriority() && i > 0) {
            swap(i, (i - 1) / 2);
            percolateUp((i - 1) / 2);
        }
    }

    private void percolateDown(int i) {
        if (i * 2 + 1 > items.size() - 1) {
            return;
        }
        if (i * 2 + 2 < items.size()) {
            if (items.get(i * 2 + 1).getPriority() > items.get(i).getPriority() &&
                items.get(i * 2 + 2).getPriority() > items.get(i).getPriority()) {
                return;
            }
            if (items.get(i * 2 + 1).getPriority() > items.get(i * 2 + 2).getPriority()) {
                swap(i * 2 + 2, i);
                percolateDown(i * 2 + 2);
            } else {
                swap(i * 2 + 1, i);
                percolateDown(i * 2 + 1);
            }
        } else if (items.get(i * 2 + 1).getPriority() < items.get(i).getPriority()) {
            swap(i * 2 + 1, i);
            percolateDown(i * 2 + 1);
        }
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int i = map.get(item);
        if (items.get(i).getPriority() == priority) {
            return;
        }
        if (items.get(i).getPriority() < priority) {
            items.get(i).setPriority(priority);
            percolateDown(i);
        } else {
            items.get(i).setPriority(priority);
            percolateUp(i);
        }
    }

    @Override
    public int size() {
        return items.size();
    }
}
