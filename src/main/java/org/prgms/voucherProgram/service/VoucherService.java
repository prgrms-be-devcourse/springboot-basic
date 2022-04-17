package org.prgms.voucherProgram.service;

import java.util.List;
import java.util.UUID;

import org.prgms.voucherProgram.domain.voucher.Voucher;
import org.prgms.voucherProgram.domain.voucher.VoucherType;
import org.prgms.voucherProgram.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository repository;

    public VoucherService(VoucherRepository repository) {
        this.repository = repository;
    }

    public Voucher create(VoucherType voucherType, long discountValue) {
        Voucher voucher = voucherType.createVoucher(UUID.randomUUID(), null, discountValue);
        return repository.save(voucher);
    }

    public List<Voucher> findAllVoucher() {
        return repository.findAll();
    }
}
