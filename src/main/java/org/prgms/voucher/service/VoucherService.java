package org.prgms.voucher.service;

import org.prgms.voucher.Voucher;
import org.prgms.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public List<Voucher> listVoucher() {
        return voucherRepository.findAll();
    }

    public void deleteAllVouchers() {
        voucherRepository.deleteAll();
    }
}
