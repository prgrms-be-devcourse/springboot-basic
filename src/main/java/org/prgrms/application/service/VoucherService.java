package org.prgrms.application.service;

import org.prgrms.application.domain.voucher.Voucher;
import org.prgrms.application.domain.voucher.VoucherType;
import org.prgrms.application.repository.voucher.VoucherRepository;
import java.util.List;

public abstract class VoucherService {

    protected VoucherRepository voucherRepository;

    protected VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public abstract void createVoucher(VoucherType voucherType, double voucherDetail);

    public List<Voucher> getVoucherList() {
        return voucherRepository.findAll();
    }

}
