package com.voucher.vouchermanagement.service.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import com.voucher.vouchermanagement.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    //@Transactional
    public void createVoucher(VoucherType voucherType, Long value) {
        //this.voucherRepository.insert(voucherType.create(UUID.randomUUID(), value, LocalDateTime.now()));
    }

    //@Transactional
    public void createVouchers(List<Voucher> vouchers) {
        //vouchers.forEach(voucherRepository::insert);
    }

    //@Transactional(readOnly = true)
    public List<Voucher> findAll() {
        //return this.voucherRepository.findAll();
        return Collections.emptyList();
    }

}
