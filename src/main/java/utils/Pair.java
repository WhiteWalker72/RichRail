package utils;

public class Pair<T, U> {

    private final T leftValue;
    private final U rightValue;

    public Pair(T leftValue, U rightValue) {
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    public T getLeftValue() {
        return leftValue;
    }

    public U getRightValue() {
        return rightValue;
    }

}
