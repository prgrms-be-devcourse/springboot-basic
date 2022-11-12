package org.prgrms.voucherapplication.service;

import org.prgrms.voucherapplication.entity.Voucher;
import org.prgrms.voucherapplication.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void create(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public String getList() {
        return voucherRepository.findAll();
    }
}
