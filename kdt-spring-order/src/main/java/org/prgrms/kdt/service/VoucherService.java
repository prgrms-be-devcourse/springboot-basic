package org.prgrms.kdt.service;

import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.getAllStoredVoucher();
    }

    public void save(Voucher voucher) {
        voucherRepository.insert(voucher);
    }

    public Voucher create(String voucherType,String discountValue) {
        VoucherStatus vocherType = VoucherStatus.of(voucherType);

        switch (vocherType) {
            case FIXED_AMOUNT:
                return new FixedAmountVoucher(UUID.randomUUID(), Long.parseLong(discountValue));
            case PERCENT_DISCOUNT:
                return new PercentDiscountVoucher(UUID.randomUUID(), Double.parseDouble(discountValue));
            default:
                throw new NoSuchElementException("존재하지 Voucher 형식입니다.");
        }
    }
}
