package org.prgrms.vouchermanager.service;

import org.prgrms.vouchermanager.Repository.VoucherRepository;
import org.prgrms.vouchermanager.domain.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getAllVoucher(){
        return voucherRepository.findAll();
    }
    public Voucher createVoucher(Voucher voucher){
        return voucherRepository.save(voucher);
    }
}
