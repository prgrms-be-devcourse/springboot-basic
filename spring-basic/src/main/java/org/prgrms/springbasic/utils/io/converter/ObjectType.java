package org.prgrms.springbasic.utils.io.converter;

import org.prgrms.springbasic.domain.customer.Customer;
import org.prgrms.springbasic.domain.customer.CustomerType;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.domain.voucher.VoucherType;

import java.time.LocalDateTime;
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
            LocalDateTime createdAt = LocalDateTime.parse(arguments[3]);
            LocalDateTime modifiedAt = (arguments[4].equals("null")) ? null : LocalDateTime.parse(arguments[4]);
            UUID customerId = UUID.fromString(arguments[5]);

            return new Voucher(voucherId, voucherType, discount, createdAt, modifiedAt, customerId);
        }
    },
    CUSTOMER(Customer.class) {
        @Override
        public Object toObject(String[] arguments) {
            UUID customerId = UUID.fromString(arguments[0]);
            CustomerType customerType = CustomerType.valueOf(arguments[1].toUpperCase());
            String name = arguments[2];
            LocalDateTime createdAt = LocalDateTime.parse(arguments[3]);
            LocalDateTime modifiedAt = (arguments[4].equals("null")) ? null : LocalDateTime.parse(arguments[4]);

            return new Customer(customerId, customerType, name, createdAt, modifiedAt);
        }
    };

    public abstract Object toObject(String[] arguments);

    private final Class<?> clazz;

    ObjectType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static Optional<ObjectType> getObjectType(Class<?> clazz) {
        return Arrays.stream(ObjectType.values())
                .filter(i -> i.clazz.isAssignableFrom(clazz))
                .findFirst();
    }
}
