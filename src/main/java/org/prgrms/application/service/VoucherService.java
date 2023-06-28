package org.prgrms.application.service;

import org.prgrms.application.domain.Voucher;
import org.prgrms.application.domain.VoucherFactory;
import org.prgrms.application.domain.VoucherType;
import org.prgrms.application.repository.VoucherRepository;
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

    public void createVoucher(VoucherType voucherType, double voucherDetail) {
        Voucher voucher = voucherFactory.create(voucherType, voucherDetail);
        voucherRepository.insert(voucher);
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.findAll();
    }

}
