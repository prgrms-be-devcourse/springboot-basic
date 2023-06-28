package org.prgrms.vouchermission;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(VoucherType type, long value, LocalDate createdDate, LocalDate expirationDate) {

        Voucher newVoucher = null;

        if (type == VoucherType.PERCENT) {
            newVoucher = PercentDiscountVoucher.createPercentDiscountVoucher(value, createdDate, expirationDate);
        } else if (type == VoucherType.AMOUNT) {
            FixedAmountVoucher.createPercentDiscountVoucher(value, createdDate, expirationDate);
        }

        if (newVoucher == null) {
           // 오류던질 예정
        }

        return voucherRepository.insert(newVoucher);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

}
