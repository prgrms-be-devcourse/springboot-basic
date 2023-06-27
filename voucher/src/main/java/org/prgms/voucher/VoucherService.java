package org.prgms.voucher;

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
