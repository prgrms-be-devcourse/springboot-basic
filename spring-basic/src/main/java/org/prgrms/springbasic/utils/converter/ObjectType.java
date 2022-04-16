package org.prgrms.springbasic.utils.converter;

import org.prgrms.springbasic.domain.customer.Customer;
import org.prgrms.springbasic.domain.customer.CustomerType;
import org.prgrms.springbasic.domain.voucher.FixedAmountVoucher;
import org.prgrms.springbasic.domain.voucher.PercentDiscountVoucher;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.domain.voucher.VoucherType;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public enum ObjectType {

    VOUCHER(Voucher.class) {
        @Override
        public Object toObject(String[] arguments) {
            UUID voucherId = UUID.fromString(arguments[0]);
            VoucherType voucherType = VoucherType.valueOf(arguments[1].toUpperCase());
            long discount = Long.parseLong(arguments[2]);

            switch(voucherType) {
                case FIXED:
                    return new FixedAmountVoucher(voucherId, discount);
                case PERCENT:
                    return new PercentDiscountVoucher(voucherId, discount);
                default:
                    break;
            }

            return Optional.empty();
        }
    },
    CUSTOMER(Customer.class) {
        @Override
        public Object toObject(String[] arguments) {
            UUID customerId = UUID.fromString(arguments[0]);
            CustomerType customerType = CustomerType.valueOf(arguments[1].toUpperCase());
            String name = arguments[2];

            return new Customer(customerId, customerType, name);
        }
    };

    public abstract Object toObject(String[] arguments);

    private final Class<?> clazz;

    ObjectType(Class<?> clazz1) {
        this.clazz = clazz1;
    }

    public static Optional<ObjectType> getObjectType(Class<?> clazz) {
        return Arrays.stream(ObjectType.values())
                .filter(i -> i.clazz.isAssignableFrom(clazz))
                .findFirst();
    }
}
