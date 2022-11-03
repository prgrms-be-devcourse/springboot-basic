package org.programmers.kdtspringdemo.voucher;

import org.programmers.kdtspringdemo.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }
}
