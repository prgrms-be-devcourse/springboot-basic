package com.prgms.vouchermanager.service.voucher;


import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void create(Voucher voucher) {
        voucherRepository.create(voucher);
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.getAllVouchers();
    }
}
