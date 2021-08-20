package org.prgrms.kdt.factory;

import org.prgrms.kdt.domain.*;
import org.prgrms.kdt.io.IoConsole;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactoryImpl implements VoucherFactory{


    @Override
    public Voucher getDiscounterVoucher(VoucherType voucherType) {
        IoConsole ioConsole = new IoConsole();

        Voucher voucher = null;
        switch (voucherType){
            case FIXED:
                ioConsole.message("FixedAmountVoucher를 생성합니다.");
                String discount = ioConsole.inputText("할인하고자 하는 금액을 입력해 주세요");
                voucher = new FixedAmountVoucher(UUID.randomUUID(),Long.parseLong(discount));
                ioConsole.message("Fixed 바우처 생성이 완료되었습니다.");
                break;
            case PERCENT:
                ioConsole.message("PercentAmountVoucher를 생성합니다.");
                String percent = ioConsole.inputText("할인하고자 하는 %를 입력해 주세요");
                voucher = new PercentDiscountVoucher(UUID.randomUUID(),Long.parseLong(percent));
                ioConsole.message("Percent 바우처 생성이 완료되었습니다.");
                break;
            default:
                ioConsole.inputError();
                break;
        }
        return voucher;
    }
}
