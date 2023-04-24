package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 8;
    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    SimpleEntry<K, V>[] entries;

    private int size;

    // You may add extra fields or helper methods though!

    /**
     * Constructs a new ArrayMap with default initial capacity.
     */
    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Constructs a new ArrayMap with the given initial capacity (i.e., the initial
     * size of the internal array).
     *
     * @param initialCapacity the initial capacity of the ArrayMap. Must be > 0.
     */
    public ArrayMap(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException();
        }
        this.entries = this.createArrayOfEntries(initialCapacity);
        size = 0;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code Entry<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     */
    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        /*
        It turns out that creating arrays of generic objects in Java is complicated due to something
        known as "type erasure."

        We've given you this helper method to help simplify this part of your assignment. Use this
        helper method as appropriate when implementing the rest of this class.

        You are not required to understand how this method works, what type erasure is, or how
        arrays and generics interact.
        */
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    @Override
    public V get(Object key) {
        for (int i = 0; i < size; i++) {
            SimpleEntry<K, V> entry = entries[i];
            if (entry != null) {
                if (entry.getKey() == key) {
                    return entry.getValue();
                } else if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (size == entries.length) {
            SimpleEntry<K, V>[] newEntries = this.createArrayOfEntries(entries.length * 2);
            for (int i = 0; i < entries.length; i++) {
                newEntries[i] = entries[i];
            }
            entries = newEntries;
        }

        if (!containsKey(key)) {
            entries[size] = new SimpleEntry<>(key, value);
            size++;
            return null;
        }
        for (int i = 0; i < size + 1; i++) {
            if (entries[i] != null) {
                if (entries[i].getKey().equals(key)) {
                    V ret = entries[i].getValue();
                    entries[i] = new SimpleEntry<>(key, value);
                    size++;
                    return ret;
                }
            }
        }

        return null;
    }

    @Override
    public V remove(Object key) {
        if (!containsKey(key)) {
            return null;
        }

        for (int i = 0; i < size; i++) {
            if (entries[i] == null) {
                if (key == null) {
                    entries[i] = new SimpleEntry<>(entries[size - 1].getKey(), entries[size - 1].getValue());
                    entries[size - 1] = null;
                    size--;
                    return null;
                }
            } else if (entries[i].getKey().equals(key)) {
                V ret = entries[i].getValue();
                if (entries[size - 1] == null) {
                    entries[i] = null;
                    size--;
                    return ret;
                }
                entries[i] = new SimpleEntry<>(entries[size - 1].getKey(), entries[size - 1].getValue());
                size--;
                return ret;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        size = 0;
        this.entries = this.createArrayOfEntries(entries.length);
    }

    @Override
    public boolean containsKey(Object key) {
        for (int i = 0; i < size; i++) {
            SimpleEntry<K, V> entry = entries[i];
            if (entry != null) {
                if (entry.getKey() == key) {
                    return true;
                } else if (entry.getKey().equals(key)) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: You may or may not need to change this method, depending on whether you
        // add any parameters to the ArrayMapIterator constructor.
        return new ArrayMapIterator<>(this.entries, size);
    }


    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        private final int size;
        private int curr;
        // You may add more fields and constructor parameters

        public ArrayMapIterator(SimpleEntry<K, V>[] entries, int size) {
            this.entries = entries;
            this.size = size;
            curr = 0;
        }

        @Override
        public boolean hasNext() {
            return curr < size;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (curr >= size) {
                throw new NoSuchElementException();
            }
            curr++;
            return entries[curr - 1];
        }
    }
}
