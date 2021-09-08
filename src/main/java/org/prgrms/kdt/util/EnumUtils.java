package org.prgrms.kdt.util;

import java.util.*;
import java.util.function.Function;

public class EnumUtils {

    private EnumUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static <T, E extends Enum<E>> Function<T, E> lookupMap(E[] values,
        Function<E, T> mapper) {
        Map<T, E> index = new HashMap<>(values.length);
        for (E value : values) {
            index.put(mapper.apply(value), value);
        }
        return index::get;
    }
}