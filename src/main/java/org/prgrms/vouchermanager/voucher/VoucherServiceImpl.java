package org.prgrms.vouchermanager.voucher;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public void createVoucher(VoucherType type, Long amount) {
        if(type.equals(VoucherType.INVALID)) return;
        Voucher voucher = VoucherFactory.getVoucher(type, amount);
        voucherRepository.insert(voucher);
    }

    @Override
    public String allVouchersToString() {
        StringBuilder sb = new StringBuilder();
        voucherRepository.getAll().forEach(v -> sb.append(sb).append("\n"));
        return sb.toString();
    }

    @Override
    public Voucher findVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow();
    }

    // 아직 구현하지 않은 기능입니다.
    @Override
    public void useVoucher(Voucher voucher) {

    }
}
