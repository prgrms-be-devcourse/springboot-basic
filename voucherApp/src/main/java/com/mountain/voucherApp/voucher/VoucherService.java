package com.mountain.voucherApp.voucher;

import com.mountain.voucherApp.repository.VoucherRepository;
import com.mountain.voucherApp.utils.DiscountPolicyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(int seq, long amount) {
        Voucher voucher = DiscountPolicyUtil.getVoucher(seq, amount);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}

