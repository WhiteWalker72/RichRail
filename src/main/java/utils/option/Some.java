package utils.option;

import java.util.function.Function;
import java.util.function.Supplier;

public class Some<T> implements IOption<T> {

    private final T value;

    public Some(T value) {
        this.value = value;
    }

    @Override
    public <U> U visit(Supplier<U> onNone, Function<T, U> onSome) {
        return onSome.apply(value);
    }

    @Override
    public boolean isNone() {
        return false;
    }

}
