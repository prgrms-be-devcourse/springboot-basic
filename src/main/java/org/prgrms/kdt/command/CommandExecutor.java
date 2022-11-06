package org.prgrms.kdt.command;

import org.prgrms.kdt.voucher.utils.VoucherMapper;
import org.prgrms.kdt.voucher.utils.VoucherValidator;
import org.prgrms.kdt.voucher.*;
import org.springframework.stereotype.Component;

@Component
public class CommandExecutor {

    private final VoucherManager voucherManager;
    private final VoucherValidator voucherValidator;
    private final VoucherMapper voucherMapper;

    public CommandExecutor(VoucherManager voucherManager, VoucherValidator voucherValidator, VoucherMapper voucherMapper) {
        this.voucherManager = voucherManager;
        this.voucherValidator = voucherValidator;
        this.voucherMapper = voucherMapper;
    }

    public void create(VoucherMetaData voucherMetaData) {
        Voucher voucher = voucherMapper.MetaDataToVoucher(voucherMetaData);

        voucher.validate(voucherValidator);

        voucherManager.save(voucher);
    }
}
