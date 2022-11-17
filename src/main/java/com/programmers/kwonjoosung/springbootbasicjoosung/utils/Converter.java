package com.programmers.kwonjoosung.springbootbasicjoosung.utils;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherFactory;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;

import java.util.UUID;

public class Converter {

    private static final String SPACE = " ";
    private static final String NEXT = System.lineSeparator();
    private static final String COMMA = ",";
    private static final String ERROR = "데이터 형식이 올바르지 않습니다.";

    private Converter() {

    }

    public static Voucher toVoucher(String text) {
        String[] textVoucher = text.split(SPACE);
        try {
            return VoucherFactory.createVoucher(
                    VoucherType.getVoucherType(textVoucher[0]),
                    UUID.fromString(textVoucher[1]),
                    Long.parseLong(textVoucher[2])
            );
        } catch (RuntimeException e){
            throw new RuntimeException(ERROR);
        }
    }

    public static Customer toCustomer(String text) {
        String[] textCustomer = text.split(COMMA);
        return new Customer(UUID.fromString(textCustomer[0]), textCustomer[1]);
    }

//    public static UUID toUUID(byte[] UUID) {
//        ByteBuffer byteBuffer = ByteBuffer.wrap(UUID);
//        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
//    }

    public static String convertText(Voucher voucher) {
        return voucher.getVoucherType().name() + SPACE +
                voucher.getVoucherId() + SPACE +
                voucher.getDiscount() + NEXT;
    }

    public static String convertCSV(Customer customer) {
        return customer.getCustomerId() + COMMA +
                customer.getName() + NEXT;
    }
}
