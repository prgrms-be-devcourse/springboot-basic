package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 자신이 직접 어떠한 VoucherRepository 를 쓸지 선택하지 않고 VoucherService 또한 직접 생성하지 않음.
 */
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    // TODO: Refactoring Factory Pattern
    public void createVoucher(VoucherType type, UUID voucherId, long policyValue /* TODO: RENAME argument name */) {
        if (type.equals(VoucherType.PERCENTAGE)) {
            voucherRepository.create(new PercentDiscountVoucher(voucherId, policyValue));
        } else if (type.equals(VoucherType.FIXED)) {
            voucherRepository.create(new FixedAmountVoucher(voucherId, policyValue));
        } else {
            // TODO: Exception
            System.out.println("Exception 처리하세요 제발");
        }
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.find();
    }

    public void useVoucher(Voucher voucher) {
    }
}
