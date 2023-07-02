package org.prgrms.application.service;

import org.prgrms.application.domain.FixedAmountVoucher;
import org.prgrms.application.domain.Voucher;
import org.prgrms.application.domain.VoucherFactory;
import org.prgrms.application.domain.VoucherType;
import org.prgrms.application.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {

    private VoucherRepository voucherRepository;
    private VoucherFactory voucherFactory;

    public VoucherService(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public void createVoucher(VoucherType voucherType, Double voucherDetail) {
        Voucher voucher = voucherFactory.create(voucherType, voucherDetail);
        voucherRepository.insert(voucher);
    }

    public Map<UUID, Voucher> getVoucherList() {
        return voucherRepository.findAll();
    }

}
