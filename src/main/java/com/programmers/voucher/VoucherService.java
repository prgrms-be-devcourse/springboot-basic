package com.programmers.voucher;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }


    public void saveVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);
    }

    public Map<UUID, Voucher> findAll() {

        return voucherRepository.findAll();
    }
}
