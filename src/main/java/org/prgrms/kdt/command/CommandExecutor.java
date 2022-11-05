package org.prgrms.kdt.command;

import org.prgrms.kdt.voucher.*;
import org.springframework.stereotype.Component;

@Component
public class CommandExecutor {

    private final VoucherMapper vouchersMapper;
    private final VoucherValidator voucherValidator;
    private final VoucherManager voucherManager;

    public CommandExecutor(VoucherMapper vouchersMapper, VoucherValidator voucherValidator, VoucherManager voucherManager) {
        this.vouchersMapper = vouchersMapper;
        this.voucherValidator = voucherValidator;
        this.voucherManager = voucherManager;
    }

    public void createVoucher(VoucherInfo voucherInfo) {
        Voucher voucher = vouchersMapper.mapFrom(voucherInfo);

        voucherValidator.validate(voucher);

        voucherManager.addVoucher(voucher);
    }
}
