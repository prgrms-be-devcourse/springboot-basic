package org.prgrms.part1.engine;

import org.prgrms.part1.exception.VoucherException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType type, int value) {
        if (type.equals(VoucherType.FixedAmount)) {
            return voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), value));
        } else if(type.equals(VoucherType.PercentDiscount)) {
            return voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), value));
        } else {
            throw new VoucherException();
        }
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }
}
