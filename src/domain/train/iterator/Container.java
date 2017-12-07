package domain.train.iterator;

public interface Container<T> {

    void addItem(T item);

    void removeItem(T item);

    Iterator getIterator();

}
