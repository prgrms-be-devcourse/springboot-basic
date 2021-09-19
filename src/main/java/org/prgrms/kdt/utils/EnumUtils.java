package org.prgrms.kdt.utils;

import java.util.*;
import java.util.function.Function;
import org.prgrms.kdt.voucher.model.VoucherType;

public class EnumUtils {
    private static final Function<String, VoucherType> voucherTypeLookupFunc =
        EnumUtils.lookupMap(VoucherType.values(), VoucherType::getNum);


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

    public static Optional<VoucherType> getVoucherType(String typeNum) {
        return Optional.ofNullable(voucherTypeLookupFunc.apply(typeNum));
    }
}