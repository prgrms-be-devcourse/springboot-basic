package com.programmers.voucher.service;

import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.model.voucher.VoucherType;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(VoucherType voucherType, long discountValue) {
        Voucher newVoucher = voucherType.convertToVoucher(UUID.randomUUID(), discountValue);
        logger.info("voucher create => {}", newVoucher);
        return voucherRepository.save(newVoucher, voucherType);
    }

    public List<Voucher> findAllVoucher() {
        List<Voucher> vouchers = voucherRepository.findAll();
        logger.info("voucher findAll at repository => {}", vouchers);
        return vouchers;
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
