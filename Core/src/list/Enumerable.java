package list;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This file has been created by:
 * pvaughn on
 * 3/18/2021 at
 * 1:58 PM
 */
@SuppressWarnings({"ForLoopReplaceableByForEach", "UnusedReturnValue", "SuspiciousSystemArraycopy", "unchecked"})
public class Enumerable<T> implements Collection<T>, Serializable {

    //region Constructors

    public Enumerable() {
        dataSize = 0;
        modifiedCount = 0;
        data = new Object[10];
    }

    public Enumerable(T... values) {
        dataSize = 0;
        modifiedCount = 0;
        data = new Object[values.length];
        addAll(values);
    }

    public Enumerable(Enumerable<T> value) {
        dataSize = 0;
        modifiedCount = 0;
        data = new Object[value.size()];
        addAll(value);
    }

    public Enumerable(Collection<? extends T> c) {
        Object[] a = c.toArray();
        if ((dataSize = a.length) != 0) {
            if (c.getClass() == ArrayList.class) {
                data = a;
            } else {
                data = Arrays.copyOf(a, dataSize, Object[].class);
            }
        } else {
            data = new Object[10];
        }
    }

    //endregion

    //region Addition

    public boolean add(T value) {
        if (value == null) return false;
        increaseSize();
        data[dataSize] = value;
        dataSize++;
        modifiedCount++;
        return true;
    }

    public boolean add(int index, T value) {
        if (value == null) return false;
        increaseSize();
        if (index >= data.length) {
            System.out.println("The index is out of bounds");
            System.exit(-1);
        }

        var temp = data[index];
        data[index] = value;
        modifiedCount++;

        Object temp2;
        for (var i = index; i < data.length - 1; i++) {
            temp2 = data[i + 1];
            data[i + 1] = temp;
            modifiedCount++;
            temp = temp2;
        }

        dataSize++;

        return true;
    }

    public boolean addAll(T... values) {
        for (var i = 0; i < values.length; i++) {
            if (values[i] == null) continue;
            add(values[i]);
        }

        return true;
    }

    public boolean addAll(Collection<? extends T> values) {
        for (var value : values) {
            if (value == null) continue;
            add(value);
        }

        return true;
    }

    //endregion

    //region Subtraction

    public boolean remove(Object value) {
        var index = -1;
        for (int i = 0; i < data.length; i++) {
            if (value.equals(data[i])) index = i;
        }

        if (index > -1) remove(index);
        return true;
    }

    public boolean remove(int index) {
        data[index] = null;
        dataSize--;
        decreaseSize();
        modifiedCount++;

        return true;
    }

    public boolean removeAll(T... values) {
        for (var i = 0; i < values.length; i++) {
            remove(values[i]);
        }

        return true;
    }

    public boolean removeAll(Collection<?> values) {
        for (var s : values) {
            remove(s);
        }

        return true;
    }

    public void clear() {
        Arrays.fill(data, null);
        data = new Object[10];
        dataSize = 0;
        modifiedCount = 0;
    }

    //endregion

    //region Getting

    public T get(int index) {
        return (T) data[index];
    }

    public boolean contains(Object value) {
        for (int i = 0; i < data.length; i++) {
            if (value.equals(data[i])) return true;
        }

        return false;
    }

    public int indexOf(T o) {
        return indexOfRange(o, dataSize);
    }

    private int indexOfRange(T o, int end) {
        var es = (T[]) data;
        if (o == null) {
            for (int i = 0; i < end; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < end; i++) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    //endregion

    //region Methods

    public int size() {
        return dataSize;
    }

    public boolean isEmpty() {
        return dataSize <= 0;
    }

    private void increaseSize() {
        var temp = new Object[dataSize + 1];

        var index = 0;
        for (var i = 0; i < data.length; i++) {
            var value = data[i];
            if (value == null) continue;
            temp[index] = value;
            index++;
        }

        data = temp;
    }

    private void decreaseSize() {
        var temp = new Object[dataSize];

        var index = 0;
        for (var i = 0; i < data.length; i++) {
            var value = data[i];
            if (value == null) continue;
            temp[index] = value;
            index++;
        }

        data = temp;
    }

    private Enumerable<T> sortCollection(Comparator<T> comparator) {
        return ((Stream<T>) Arrays.stream(data)).sorted(comparator).collect(Collectors.toCollection(Enumerable::new));
    }

    private Enumerable<T> sortCollectionReverseMapper(Comparator<? super T> comparator) {
        return ((Stream<T>) Arrays.stream(data)).sorted(comparator).collect(Collectors.toCollection(Enumerable::new));
    }

    //endregion

    //region Overrides

    public Iterator<T> iterator() {
        return new Iterator<>() {

            private int currentIndex = 0;

            public boolean hasNext() {
                return currentIndex < dataSize && data[currentIndex] != null;
            }

            public T next() {
                return (T) data[currentIndex++];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        final int expectedModCount = modifiedCount;
        final Object[] es = data;
        final int size = size();
        for (var i = 0; modifiedCount == expectedModCount && i < size; i++) {
            action.accept((T) es[i]);
        }
        if (modifiedCount != expectedModCount) throw new ConcurrentModificationException();
    }

    public Spliterator<T> spliterator() {
        return Spliterators.spliterator(this, 0);
    }

    public Object[] toArray() {
        return Arrays.copyOf(data, dataSize);
    }

    public <E> E[] toArray(E[] a) {
        if (a.length < dataSize) return (E[]) Arrays.copyOf(data, dataSize, a.getClass());
        System.arraycopy(data, 0, a, 0, dataSize);
        if (a.length > dataSize) a[dataSize] = null;
        return a;
    }

    public boolean removeIf(Predicate<? super T> filter) {
        return false;
    }

    public Stream<T> stream() {
        return null;
    }

    public Stream<T> parallelStream() {
        return null;
    }

    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public String toString() {
        return flattenToString(',');
    }

    //endregion

    //region Linq Statements

    public Enumerable<T> where(Predicate<? super T> predicate) {
        var result = new Enumerable<T>();
        for (var i = 0; i < data.length; i++) {
            var value = (T) data[i];
            if (value == null) continue;
            if (!predicate.test(value)) continue;
            result.add(value);
        }

        return result;
    }

    public <R> Enumerable<R> select(Function<? super T, ? extends R> mapper) {
        var result = new Enumerable<R>();
        for (var i = 0; i < data.length; i++) {
            var value = (T) data[i];
            if (value == null) continue;
            var nv = mapper.apply(value);
            if (nv == null) continue;
            result.add(nv);
        }

        return result;
    }

    public <R> Enumerable<R> selectMany(Function<? super T, Collection<? extends R>> mapper) {
        var result = new Enumerable<R>();
        for (var i = 0; i < data.length; i++) {
            var value = (T) data[i];
            if (value == null) continue;
            var t = mapper.apply(value);
            if (t == null) continue;
            result.addAll(t);
        }

        return result;
    }

    public <R extends Object & Comparable<? super R>> R min(Function<? super T, ? extends R> mapper) {
        var list = new Enumerable<R>();
        for (var i = 0; i < data.length; i++) {
            var value = (T) data[i];
            if (value == null) continue;
            var nv = mapper.apply(value);
            if (nv == null) continue;
            list.add(nv);
        }

        return Collections.min(list);
    }

    public <R extends Object & Comparable<? super R>> R max(Function<? super T, ? extends R> mapper) {
        var list = new Enumerable<R>();
        for (var i = 0; i < data.length; i++) {
            var value = (T) data[i];
            if (value == null) continue;
            var nv = mapper.apply(value);
            if (nv == null) continue;
            list.add(nv);
        }

        return Collections.max(list);
    }

    public boolean any() {
        return dataSize > 0;
    }

    public boolean any(Predicate<? super T> predicate) {
        var result = false;
        for (var i = 0; i < data.length; i++) {
            var value = (T) data[i];
            if (value == null) continue;
            if (!predicate.test(value)) continue;
            result = true;
        }

        return result;
    }

    public boolean all(Predicate<? super T> predicate) {
        var result = true;
        for (var i = 0; i < data.length; i++) {
            var value = (T) data[i];
            if (value == null) continue;
            if (predicate.test(value)) continue;
            result = false;
        }

        return result;
    }

    public T firstOrDefault() {
        if (this.isEmpty()) return null;
        return this.get(0);
    }

    public T firstOrDefault(Predicate<? super T> predicate) {
        for (var i = 0; i < data.length; i++) {
            var value = (T) data[i];
            if (value == null) continue;
            if (!predicate.test(value)) continue;
            return value;
        }

        return null;
    }

    public <R> Map<R, Enumerable<T>> groupBy(Function<? super T, ? extends R> mapper) {
        var result = new HashMap<R, Enumerable<T>>();
        for (var i = 0; i < data.length; i++) {
            var value = (T) data[i];
            if (value == null) continue;
            var nv = mapper.apply(value);
            if (nv == null) continue;
            result.putIfAbsent(nv, new Enumerable<>());
            result.get(nv).add(value);
        }

        return result;
    }

    public Enumerable<T> orderBy(Comparator<T> comparator) {
        if (dataSize <= 0) return this;
        return sortCollection(comparator);
    }

    public <R extends Comparable<? super R>> Enumerable<T> orderBy(Function<? super T, ? extends R> mapper) {
        if (dataSize <= 0) return this;
        return sortCollection(Comparator.comparing(mapper));
    }

    public Enumerable<T> orderByDescending(Comparator<T> comparator) {
        if (dataSize <= 0) return this;
        return sortCollection(comparator.reversed());
    }

    private Enumerable<T> sortCollectionTest(Comparator<? super T> comparator) {
        if (dataSize <= 0) return this;
        return ((Stream<T>) Arrays.stream(data)).sorted(comparator).collect(Collectors.toCollection(Enumerable::new));
    }

    public <R extends Comparable<? super R>> Enumerable<T> orderByDescending(Function<? super T, ? extends R> mapper) {
        if (dataSize <= 0) return this;
        return sortCollectionReverseMapper(Comparator.comparing(mapper).reversed());
    }

    public boolean intersects(Enumerable<T> checkList) {
        return this.any(checkList::contains);
    }

    //endregion

    //region Some Utilities

    public String flattenToString(char delimiter) {
        var builder = new StringBuilder("[");
        for (var i = 0; i < data.length; i++) {
            var value = data[i];
            if (value == null) continue;
            if (i > 0) builder.append(" ");
            builder.append(value.toString());
            if (i < data.length - 1) builder.append(delimiter);
        }

        builder.append("]");
        return builder.toString();
    }

    //endregion

    //region Fields

    private Object[] data;
    private int dataSize;
    private int modifiedCount;

    private static final long serialVersionUID = 88185441447785L;

    //endregion

}
