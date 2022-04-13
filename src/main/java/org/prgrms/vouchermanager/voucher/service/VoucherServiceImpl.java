package org.prgrms.vouchermanager.voucher.service;

import org.prgrms.vouchermanager.voucher.domain.Voucher;
import org.prgrms.vouchermanager.voucher.domain.VoucherFactory;
import org.prgrms.vouchermanager.voucher.domain.VoucherType;
import org.prgrms.vouchermanager.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public void createVoucher(String type, Long amount) {
        VoucherType voucherType = VoucherType.findVoucherType(type)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 type 입력입니다."));
        Voucher voucher = VoucherFactory.getVoucher(voucherType, amount);
        voucherRepository.insert(voucher);
    }

    @Override
    public String allVouchersToString() {
        StringBuilder sb = new StringBuilder();
        voucherRepository.getAll().forEach(v -> sb.append(v).append("\n"));
        return sb.toString();
    }

    @Override
    public Voucher findVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 voucherId 입니다."));
    }

    // 아직 구현하지 않은 기능입니다.
    @Override
    public void useVoucher(Voucher voucher) {

    }
}
