package org.prgrms.voucherprgrms.voucher;

import org.prgrms.voucherprgrms.voucher.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherCreator {
    private static final Logger logger = LoggerFactory.getLogger(VoucherCreator.class);

    public Voucher create(VoucherDTO voucherDTO) {

        VoucherType type = VoucherType.getType(voucherDTO.getVoucherType());
        long value = voucherDTO.getValue();

        UUID voucherId = UUID.randomUUID();
        switch (type) {
            case FIXEDAMOUNT:
                return new FixedAmountVoucher(voucherId, value);
            case PERCENTDISCOUNT:
                return new PercentDiscountVoucher(voucherId, value);
            default:
                //Exception
                logger.error("유효하지 않은 Voucher type -> {}", type);
                throw new IllegalArgumentException("Voucher type error");
        }
    }

}
