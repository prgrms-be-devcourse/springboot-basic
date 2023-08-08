package com.prgms.voucher.voucherproject.service;

import com.prgms.voucher.voucherproject.domain.voucher.Voucher;
import com.prgms.voucher.voucherproject.domain.voucher.VoucherType;
import com.prgms.voucher.voucherproject.repository.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.findAll();
    }

    public void createVoucher(VoucherType voucherType, long discount) {

        Voucher voucher = voucherType.createVoucher(discount);

        try {
            voucherRepository.save(voucher);
        } catch (IllegalArgumentException e) {
            logger.error("{} IllegalArgumentException -> {}", voucherType, discount);
        }
    }

}
