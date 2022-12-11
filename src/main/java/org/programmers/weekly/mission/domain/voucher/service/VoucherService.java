package org.programmers.weekly.mission.domain.voucher.service;

import org.programmers.weekly.mission.util.type.VoucherType;
import org.programmers.weekly.mission.domain.voucher.model.FixedAmountVoucher;
import org.programmers.weekly.mission.domain.voucher.model.PercentDiscountVoucher;
import org.programmers.weekly.mission.domain.voucher.model.Voucher;
import org.programmers.weekly.mission.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType voucherType, Long discount) {
        switch (voucherType) {
            case FIXED -> {
                FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), discount);
                return voucherRepository.insert(fixedAmountVoucher);
            }
            case PERCENT -> {
                PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), discount);
                return voucherRepository.insert(percentDiscountVoucher);
            }
        }
        throw new IllegalArgumentException("생성할 수 없는 바우처 입니다.");
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.getVoucherList();
    }
}
