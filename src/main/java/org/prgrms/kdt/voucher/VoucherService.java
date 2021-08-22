package org.prgrms.kdt.voucher;

import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 7:52 오후
 */
@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(@Qualifier("memory") VoucherRepository voucherRepository) {
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
        voucherRepository.insert(voucher);
    }

    public Map<UUID, Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }
}

