package utils;

import utils.option.IOption;
import utils.option.None;
import utils.option.Some;

public class MathUtils {

    public static IOption<Integer> parseInt(String input) {
        try {
            return new Some<>(Integer.parseInt(input));
        } catch (Exception e) {
            return new None<>();
        }
    }

}
