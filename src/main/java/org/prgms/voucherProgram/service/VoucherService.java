package org.prgms.voucherProgram.service;

import java.util.List;
import java.util.UUID;

import org.prgms.voucherProgram.entity.voucher.FixedAmountVoucher;
import org.prgms.voucherProgram.entity.voucher.PercentDiscountVoucher;
import org.prgms.voucherProgram.entity.voucher.Voucher;
import org.prgms.voucherProgram.entity.voucher.VoucherType;
import org.prgms.voucherProgram.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository repository;

    public VoucherService(VoucherRepository repository) {
        this.repository = repository;
    }

    public Voucher create(VoucherType voucherType, long value) {
        Voucher voucher = createVoucher(voucherType, value);
        return repository.save(voucher);
    }

    private Voucher createVoucher(VoucherType voucherType, long value) {
        if (voucherType == VoucherType.FIXED_AMOUNT) {
            return new FixedAmountVoucher(UUID.randomUUID(), value);
        }

        return new PercentDiscountVoucher(UUID.randomUUID(), value);
    }

    public List<Voucher> findAllVoucher() {
        return repository.findAll();
    }
}
