package org.promgrammers.springbootbasic.util;

import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.model.CustomerType;
import org.promgrammers.springbootbasic.domain.voucher.model.FixedAmountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.PercentDiscountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FileConverter {

    private static final Logger logger = LoggerFactory.getLogger(FileConverter.class);
    private static final String DELIMITER = ",";

    private FileConverter() throws InstantiationException {
        logger.error("Cannot instantiate.");
        throw new InstantiationException("인스턴스화 할 수 없습니다.");
    }

    public static Voucher parseVoucherFromLine(String line) {
        String[] parts = line.split(DELIMITER);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid line format: " + line);
        }

        UUID voucherId = UUID.fromString(parts[0]);
        long amount = Long.parseLong(parts[1]);
        VoucherType voucherType = VoucherType.valueOf(parts[2]);

        switch (voucherType) {
            case FIXED:
                return new FixedAmountVoucher(voucherId, amount);
            case PERCENT:
                return new PercentDiscountVoucher(voucherId, amount);
            default:
                logger.error("Invalid Voucher Type => {}", voucherType);
                throw new IllegalArgumentException("잘못된 Voucher Type 입니다 : " + voucherType);
        }
    }

    public static Customer parseCustomerFromLine(String line) {
        String[] parts = line.split(DELIMITER);

        UUID customerId = UUID.fromString(parts[0]);
        CustomerType voucherType = CustomerType.valueOf(parts[1]);

        return new Customer(customerId, voucherType);
    }

    public static String voucherToLine(Voucher voucher) {
        StringBuilder sb = new StringBuilder();
        sb.append(voucher.getVoucherId()).append(",");
        sb.append(voucher.getAmount()).append(",");
        sb.append(voucher.getVoucherType());

        return sb.toString();
    }
}
