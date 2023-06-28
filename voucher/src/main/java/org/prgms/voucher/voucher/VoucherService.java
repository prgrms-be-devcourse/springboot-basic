package org.prgms.voucher.voucher;

import org.prgms.voucher.voucher.repository.VoucherRepository;

import java.util.List;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    void createVoucher(VoucherCreateDto voucherCreateDto) {
    }

    List<Voucher> listVoucher() {

    }
}
