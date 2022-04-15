package com.voucher.vouchermanagement.service;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import com.voucher.vouchermanagement.repository.voucher.VoucherRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void insertVoucher(VoucherType voucherType, Long value) {
        CreateVoucherDto createVoucherDTO = new CreateVoucherDto(value);
        this.voucherRepository.insert(voucherType.create(createVoucherDTO));
    }

    public List<Voucher> findAll() {
        List<Voucher> foundVouchers = this.voucherRepository.findAll();

        return foundVouchers;
    }

}
