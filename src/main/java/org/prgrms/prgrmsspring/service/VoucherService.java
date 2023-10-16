package org.prgrms.prgrmsspring.service;

import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void create(Voucher voucher) {
        voucherRepository.insert(voucher);
    }

    public List<Voucher> list() {
        return voucherRepository.findAll();
    }

    public void exit() {
        System.exit(0);
    }
}
