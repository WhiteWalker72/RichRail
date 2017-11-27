package utils.option;

import java.util.function.Function;
import java.util.function.Supplier;

// Null option design pattern
public interface IOption<T> {

    <U> U visit(Supplier<U> onNone, Function<T, U> onSome);

}
