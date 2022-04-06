package org.prgms.voucher.service;

import java.util.List;
import java.util.UUID;

import org.prgms.voucher.entity.FixedAmountVoucher;
import org.prgms.voucher.entity.PercentDiscountVoucher;
import org.prgms.voucher.entity.Voucher;
import org.prgms.voucher.entity.VoucherType;
import org.prgms.voucher.exception.WrongDiscountAmountException;
import org.prgms.voucher.exception.WrongDiscountPercentException;
import org.prgms.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository repository;

    public VoucherService(VoucherRepository repository) {
        this.repository = repository;
    }

    public Voucher create(VoucherType voucherType, long value) throws
        WrongDiscountAmountException,
        WrongDiscountPercentException {
        Voucher voucher = getVoucher(voucherType, value);
        return repository.insert(voucher);
    }

    private Voucher getVoucher(VoucherType voucherType, long value) throws
        WrongDiscountAmountException,
        WrongDiscountPercentException {
        if (voucherType == VoucherType.FIXED_AMOUNT) {
            return new FixedAmountVoucher(UUID.randomUUID(), value);
        }

        return new PercentDiscountVoucher(UUID.randomUUID(), value);
    }

    public List<Voucher> findAllVoucher() {
        return repository.findAll();
    }
}
