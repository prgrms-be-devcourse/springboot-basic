package org.prgrms.kdt.util;

import java.util.*;
import java.util.function.Function;
import org.prgrms.kdt.app.command.CommandType;
import org.prgrms.kdt.model.voucher.VoucherType;

public class EnumUtils {
    private static final Function<String, CommandType> commandTypeLookupFunction =
        EnumUtils.lookupMap(CommandType.values(), CommandType::getNum);
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

    public static Optional<CommandType> getCommnadType(String typeNum) {
        return Optional.ofNullable(commandTypeLookupFunction.apply(typeNum));
    }

    public static Optional<VoucherType> getVoucherType(String typeNum) {
        return Optional.ofNullable(voucherTypeLookupFunc.apply(typeNum));
    }
}