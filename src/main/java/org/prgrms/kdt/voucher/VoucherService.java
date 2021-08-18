package org.prgrms.kdt.voucher;

import java.util.Map;
import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 7:52 오후
 */
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher %s".formatted(voucherId)));
    }

    public void useVoucher(Voucher voucher) {

    }

    public void createVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public Map<UUID, Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }
}

