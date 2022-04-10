package org.programmer.kdtspringboot.voucher;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createFixedAmountVoucher(UUID voucherId, long amount) {
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherRepository.saveVoucher(voucher);
    }

    public void createPercentDiscountVoucher(UUID voucherId, long percent) {
        Voucher voucher = new PercentDiscountVoucher(voucherId, percent);
        voucherRepository.saveVoucher(voucher);
    }

    public List<Voucher> findAllVouchers() throws IOException {
        return voucherRepository.findAll();
    }
}
