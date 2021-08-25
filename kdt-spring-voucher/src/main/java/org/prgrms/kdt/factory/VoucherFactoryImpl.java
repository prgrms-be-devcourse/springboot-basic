package org.prgrms.kdt.factory;

import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.enumType.VoucherStatus;
import org.prgrms.kdt.utill.IO.IoConsole;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactoryImpl implements VoucherFactory {

    @Override
    public Voucher getDiscounterVoucher(VoucherStatus voucherStatus) {
        IoConsole ioConsole = new IoConsole();

        Voucher voucher = null;
        switch (voucherStatus) {
            case FIXED:
                ioConsole.message("FixedAmountVoucher를 선택하셨습니다.");
                String discount = ioConsole.inputText("할인하고자 하는 금액을 입력해 주세요");
                voucher = new FixedAmountVoucher(UUID.randomUUID(), Long.parseLong(discount));
                ioConsole.message("Fixed 바우처 생성이 완료되었습니다.");
                break;
            case PERCENT:
                ioConsole.message("PercentAmountVoucher를 선택하셨습니다.");
                String percent = ioConsole.inputText("할인하고자 하는 %를 입력해 주세요");
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), Long.parseLong(percent));
                ioConsole.message("Percent 바우처 생성이 완료되었습니다.");
                break;
            default:
                ioConsole.inputError();
                break;
        }
        return voucher;
    }

}