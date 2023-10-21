package org.prgrms.prgrmsspring.service;

import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void create(Voucher voucher) {
        voucherRepository.insert(voucher);
    }

    public void update(Voucher voucher) {
        voucherRepository.update(voucher);
    }

    public void delete(UUID voucherId) {
        voucherRepository.delete(voucherId);
    }

    public List<Voucher> list() {
        return voucherRepository.findAll();
    }

    public void exit() {
        System.exit(0);
    }
}
