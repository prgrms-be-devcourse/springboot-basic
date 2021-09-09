package org.prgrms.kdtspringhomework.command;

import org.prgrms.kdtspringhomework.io.Input;
import org.prgrms.kdtspringhomework.io.Output;
import org.prgrms.kdtspringhomework.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdtspringhomework.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdtspringhomework.voucher.service.VoucherService;

import java.util.UUID;

public class CreateCommand implements CommandStrategy {
    private final String FIXED = "fixed";
    private final String PERCENT = "percent";

    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {

        //if문을 없애기 위한 연구가 아직 더 필요한 것 같습니다...
        try {
            output.inputVoucherTypeMessage();
            String voucherType = input.receiveUserInput();
            long amount = 0L;
            if (voucherType.equals(FIXED)) {
                output.inputAmountMessage();
                amount = Long.parseLong(input.receiveUserInput());
                voucherService.addVoucher(new FixedAmountVoucher(UUID.randomUUID(), amount));
            } else if (voucherType.equals(PERCENT)) {
                output.inputAmountMessage();
                amount = Long.parseLong(input.receiveUserInput());
                voucherService.addVoucher(new PercentDiscountVoucher(UUID.randomUUID(), amount));
            }
        } catch (IllegalArgumentException e) {
            output.invalidVoucherType();
        }
        return false;
    }
}
