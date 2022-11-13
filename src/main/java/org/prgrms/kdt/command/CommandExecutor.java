package org.prgrms.kdt.command;

import org.prgrms.kdt.voucher.utils.VoucherMapper;
import org.prgrms.kdt.voucher.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandExecutor {

    private final VoucherManager voucherManager;
    private final VoucherMapper voucherMapper;

    public CommandExecutor(VoucherManager voucherManager, VoucherMapper voucherMapper) {
        this.voucherManager = voucherManager;
        this.voucherMapper = voucherMapper;
    }

    public void create(String type, String amount) {
        Voucher voucher = voucherMapper.fromMetadata(type, amount);

        voucherManager.save(voucher);
    }

    public List<Voucher> list() {
        return voucherManager.findAll();
    }
}
