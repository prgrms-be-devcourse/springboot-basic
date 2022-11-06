package com.programmers.commandLine.global.factory;

import com.programmers.commandLine.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.commandLine.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.commandLine.domain.voucher.entity.Voucher;
import com.programmers.commandLine.domain.voucher.entity.VoucherMenu;
import com.programmers.commandLine.global.io.Message;
import com.programmers.commandLine.global.util.Verification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {

    public Voucher createVoucher(VoucherMenu voucherMenu, String discountInput) {
        LoggerFactory.getLogger().info("VoucherFactory createVoucher 실행");

        Integer discount = Verification.integerVerification(discountInput);

        Voucher voucher = switch (voucherMenu) {
            case FIXEDAMOUNTVOUCHER -> new FixedAmountVoucher(UUID.randomUUID(), discount);
            case PERCENTDISCOUNTVOUCHER -> new PercentDiscountVoucher(UUID.randomUUID(), discount);
            case ERROR -> error();
        };
        return voucher;
    }

    private Voucher error() {
        LoggerFactory.getLogger().error("VoucherMenu 오류발생");
        throw new IllegalArgumentException(Message.VOUCHER_MENU_ERROR.getMessage());
    }
}
