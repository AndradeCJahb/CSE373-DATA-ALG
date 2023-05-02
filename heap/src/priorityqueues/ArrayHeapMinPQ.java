package priorityqueues;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @see ExtrinsicMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 0;
    List<PriorityNode<T>> items;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.
    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        PriorityNode<T> toSwap = items.get(a);
        items.set(a, items.get(b));
        items.set(b, toSwap);
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }

        PriorityNode<T> curr = new PriorityNode<>(item, priority);
        items.add(curr);

        if (items.size() > 1) {
            int i = items.size() - 1;
            while (items.get(i).getPriority() < items.get((i - 1) / 2).getPriority()) {
                if (i < 1) {
                    break;
                }
                swap(i, (i - 1) / 2);
                i = (i - 1) / 2;
            }
        }
    }

    @Override
    public boolean contains(T item) {
        for (PriorityNode<T> currItem : items) {
            if (Objects.equals(currItem.getItem(), item)) {
                return true;
            }
        }
        return false;
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
        removeMin(0);
        return ret;
    }

    private void removeMin(int i) {
        if (i * 2 + 1 >= items.size()) {
            return;
        }


        if (i * 2 + 2 < items.size()) {
            if (items.get(i * 2 + 1).getPriority() > items.get(i).getPriority() &&
                items.get(i * 2 + 2).getPriority() > items.get(i).getPriority()) {
                return;
            }
            if (items.get(i * 2 + 1).getPriority() > items.get(i * 2 + 2).getPriority() &&
                items.get(i * 2 + 2).getPriority() < items.get(i).getPriority()) {
                swap(i * 2 + 2, i);
                removeMin(i * 2 + 2);
            } else if (items.get(i * 2 + 1).getPriority() < items.get(i * 2 + 2).getPriority() &&
                items.get(i * 2 + 1).getPriority() < items.get(i).getPriority()) {
                swap(i * 2 + 1, i);
                removeMin(i * 2 + 1);
            }
        } else if (items.get(i * 2 + 1).getPriority() < items.get(i).getPriority()) {
            swap(i * 2 + 1, i);
            removeMin(i * 2 + 1);
        }
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }

        boolean increase = false;
        int i = 0;
        for (int j = 0; j < items.size(); j++) {
            PriorityNode<T> changeItem = items.get(j);
            if (Objects.equals(changeItem.getItem(), item)) {
                i = j;
                if (changeItem.getPriority() == priority) {
                    break;
                } else if (changeItem.getPriority() > priority) {
                    increase = true;
                } else if (changeItem.getPriority() < priority) {
                    increase = false;
                }
                changeItem.setPriority(priority);
            }
        }

        if (increase) {
            removeMin(i);
        } else {
            if (items.size() > 1) {
                while (items.get(i).getPriority() < items.get((i - 1) / 2).getPriority()) {
                    if (i < 1) {
                        break;
                    }
                    swap(i, (i - 1) / 2);
                    i = (i - 1) / 2;
                }
            }
        }
    }

    @Override
    public int size() {
        return items.size();
    }
}