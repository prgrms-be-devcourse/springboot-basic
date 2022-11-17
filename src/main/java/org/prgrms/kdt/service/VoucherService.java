package org.prgrms.kdt.service;

import org.prgrms.kdt.dao.entity.voucher.Voucher;
import org.prgrms.kdt.dao.entity.voucher.VoucherFactory;
import org.prgrms.kdt.dao.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public VoucherService(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.getAllStoredVoucher();
    }

    public void save(Voucher voucher) {
        voucherRepository.insert(voucher);
    }

    public Voucher create(String voucherTypeNumber, String discountValue) {
        return voucherFactory.createVoucher(voucherTypeNumber, discountValue);
    }
}
