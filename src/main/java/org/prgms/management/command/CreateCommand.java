package org.prgms.management.command;

import org.prgms.management.blacklist.service.BlacklistService;
import org.prgms.management.io.Input;
import org.prgms.management.io.Output;
import org.prgms.management.voucher.entity.Voucher;
import org.prgms.management.voucher.entity.VoucherType;
import org.prgms.management.voucher.service.VoucherService;

import java.util.Optional;
import java.util.UUID;

public class CreateCommand implements Command {
    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService, BlacklistService blacklistService) {
        output.chooseVoucherType();

        String voucherType = input.getInput("바우처타입(fixed <- FixedAmountVoucher, " +
                "percent <- PercentAmountVoucher): ");

        String voucherName = input.getInput("\n바우처이름: ");

        int discountNum = input.getInputInteger("할인액(율): ");

        if (discountNum == 0) return true;

        Optional<Voucher> voucher = VoucherType.createVoucher(
                UUID.randomUUID(), discountNum, voucherName, voucherType);

        if (voucher.isPresent() && voucherService.createVoucher(voucher.get())) {
            output.voucherCreateSuccess();
            return true;
        }

        output.voucherCreateFail();
        return true;
    }
}
