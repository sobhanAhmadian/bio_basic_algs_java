package dataStructures;

public class Pair<T, E> {
    T t;
    E e;

    public Pair(T t, E e) {
        this.t = t;
        this.e = e;
    }

    public T getT() {
        return t;
    }

    public E getE() {
        return e;
    }
}
