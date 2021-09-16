package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.factory.VoucherFactory;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 자신이 직접 어떠한 VoucherRepository 를 쓸지 선택하지 않고 VoucherService 또한 직접 생성하지 않음.
 */
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(VoucherType type, UUID voucherId, long policyValue) {
        Voucher voucher = VoucherFactory.getInstance().createVoucher(type, voucherId, policyValue);
        voucherRepository.insert(voucher);
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new NullPointerException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.find();
    }

    public void useVoucher(Voucher voucher) {
    } // 다음 강의에서 사용
}
