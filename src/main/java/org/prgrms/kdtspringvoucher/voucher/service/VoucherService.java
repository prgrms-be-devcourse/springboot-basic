package org.prgrms.kdtspringvoucher.voucher.service;

import org.prgrms.kdtspringvoucher.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    public VoucherService(VoucherRepository vr) {
        this.voucherRepository = vr;
    }

    public List<Voucher> getAllVouchers(){
        return voucherRepository.findAll();
    }

    public Voucher saveVoucher(Voucher voucher) {
        voucher.resetVoucherId();
        try {
            return voucherRepository.save(voucher);
        } catch (Exception e) {
            return null;
        }
    }
}
