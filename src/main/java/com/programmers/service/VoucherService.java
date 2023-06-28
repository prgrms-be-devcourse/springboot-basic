package com.programmers.service;

import com.programmers.domain.Voucher;
import com.programmers.repository.MemoryVoucherRepository;
import com.programmers.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private static final Logger log = LoggerFactory.getLogger(MemoryVoucherRepository.class);

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public String reformatVoucherType(String voucherTypeInput) {
        return voucherTypeInput.trim().replace(" ", "").toLowerCase();
    }

    public void save(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public Long changeDiscountValueToNumber(String discountValue) {
        try {
            Long.parseLong(discountValue);
        } catch (NumberFormatException e) {
            log.error("The discount value input is not in numeric format. input value = {}", discountValue);
            throw new IllegalArgumentException();
        }
        return Long.parseLong(discountValue);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
