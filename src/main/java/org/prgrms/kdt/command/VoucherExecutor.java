package org.prgrms.kdt.command;

import org.prgrms.kdt.voucher.utils.VoucherMapper;
import org.prgrms.kdt.voucher.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VoucherExecutor {

    private final VoucherManager voucherManager;
    private final VoucherMapper voucherMapper;

    public VoucherExecutor(VoucherManager voucherManager, VoucherMapper voucherMapper) {
        this.voucherManager = voucherManager;
        this.voucherMapper = voucherMapper;
    }

    public Voucher create(String type, String amount) {
        Voucher voucher = voucherMapper.fromMetadata(type, amount);

        return voucherManager.save(voucher);
    }

    public List<Voucher> list() {
        return voucherManager.findAll();
    }

    public Voucher findVoucher(Long voucherId) {
        Optional<Voucher> maybeVoucher = voucherManager.findById(voucherId);
        if (maybeVoucher.isEmpty()) {
            throw new IllegalArgumentException("Cannot find Voucher. Please Check Voucher Id.");
        }
        return maybeVoucher.get();
    }

    public void deleteVoucher(long voucherId) {
        voucherManager.deleteById(voucherId);
    }
}
