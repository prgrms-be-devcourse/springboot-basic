package org.prgrms.kdt.service;

import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.voucher.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public VoucherService(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public List<Voucher> getAllVouchers() throws IOException {
        return voucherRepository.getAllStoredVoucher();
    }

    public void save(Voucher voucher) throws IOException {
        voucherRepository.insert(voucher);
    }

    public Voucher create(String voucherType,String discountValue) {
        VoucherStatus vocherType = VoucherStatus.of(voucherType);

        return voucherFactory.createVoucher(voucherType, discountValue);
    }
}
