package org.prgrms.kdt.utils;

import java.util.*;
import java.util.function.Function;
import org.prgrms.kdt.customer.model.CustomerType;
import org.prgrms.kdt.voucher.model.VoucherType;

public class EnumUtils {

    private static final Function<String, VoucherType> voucherTypeLookupFunc =
        EnumUtils.lookupMap(VoucherType.values(), VoucherType::name);
    private static final Function<String, CustomerType> customerTypeLookupFunc =
        EnumUtils.lookupMap(CustomerType.values(), CustomerType::name);


    private EnumUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static <T, E extends Enum<E>> Function<T, E> lookupMap(
        E[] values,
        Function<E, T> mapper) {

        Map<T, E> index = new HashMap<>(values.length);
        for (E value : values) {
            index.put(mapper.apply(value), value);
        }
        return index::get;
    }

    public static Optional<VoucherType> getVoucherTypeByName(String name) {
        return Optional.ofNullable(voucherTypeLookupFunc.apply(name));
    }

    public static Optional<CustomerType> getCustomerTypeByName(String name) {
        return Optional.ofNullable(customerTypeLookupFunc.apply(name));
    }
}