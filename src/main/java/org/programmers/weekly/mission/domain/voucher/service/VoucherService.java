package org.programmers.weekly.mission.domain.voucher.service;

import org.programmers.weekly.mission.domain.voucher.model.FixedAmountVoucher;
import org.programmers.weekly.mission.domain.voucher.model.PercentDiscountVoucher;
import org.programmers.weekly.mission.domain.voucher.model.Voucher;
import org.programmers.weekly.mission.domain.voucher.repository.VoucherRepository;
import org.programmers.weekly.mission.util.type.VoucherType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }


    public Voucher createVoucher(VoucherType voucherType, Long discount) {
        Optional<Voucher> voucher = makeVoucher(voucherType, discount);

        if (voucher.isEmpty()) {
            throw new IllegalArgumentException("잘못된 바우처 타입");
        }

        return voucherRepository.insert(voucher.get());
    }

    private Optional<Voucher> makeVoucher(VoucherType voucherType, Long discount) {
        switch (voucherType) {
            case FIXED -> {
                return Optional.of(new FixedAmountVoucher(UUID.randomUUID(), discount));
            }
            case PERCENT -> {
                return Optional.of(new PercentDiscountVoucher(UUID.randomUUID(), discount));
            }
        }
        return Optional.empty();
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.findAllVouchers();
    }
}
