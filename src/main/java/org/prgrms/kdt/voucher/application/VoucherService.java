package org.prgrms.kdt.voucher.application;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.NotFoundException;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import static org.prgrms.kdt.voucher.domain.VoucherType.*;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher findByVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND_VOUCHER_MESSAGE));
    }

    public Voucher insert(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public Map<UUID, Voucher> findByAllVoucher() {
        return voucherRepository.findByAllVoucher();
    }

    public Voucher createVoucher(VoucherType type, String value) {
        if (type == FIXED) {
            return new FixedAmountVoucher(UUID.randomUUID(), parseLong(value));
        }
        return new PercentDiscountVoucher(UUID.randomUUID(), parseLong(value));
    }

    private long parseLong(String value) {
        return Long.parseLong(value);
    }

}