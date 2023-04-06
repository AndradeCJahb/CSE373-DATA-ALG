package deques;

/**
 * @see Deque
 */
public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back;
    // Feel free to add any additional fields you may need, though.

    public LinkedDeque() {
        size = 0;
        front = new Node<>(null);
        back = new Node<>(null);
        front.next = back;
        back.prev = front;
    }

    public void addFirst(T item) {
        if (size == 0) {
            Node<T> curr = new Node<>(item, front, back);
            back.prev = curr;
            front.next = curr;
        } else {
            Node<T> curr = new Node<>(item, front, front.next);
            front.next.prev = curr;
            front.next = curr;
        }
        size += 1;

    }

    public void addLast(T item) {
        if (size == 0) {
            Node<T> curr = new Node<>(item, front, back);
            back.prev = curr;
            front.next = curr;
        } else {
            Node<T> curr = new Node<>(item, back.prev, back);
            back.prev.next = curr;
            back.prev = curr;
        }
        size += 1;

    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T ret = front.next.value;
        front.next = front.next.next;
        front.next.prev = front;
        return ret;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T ret = back.prev.value;
        back.prev = back.prev.prev;
        back.prev.next = back;
        return ret;
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }

        if (size / 2 < index) {
            index = Math.abs(index - size);
            Node<T> curr = back;
            while (index > 0) {
                curr = curr.prev;
                index--;
            }
            return curr.value;
        } else {
            Node<T> curr = front.next;
            while (index > 0) {
                curr = curr.next;
                index--;
            }
            return curr.value;
        }


    }

    public int size() {
        return size;
    }
}
