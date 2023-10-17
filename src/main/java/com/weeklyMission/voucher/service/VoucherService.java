package com.weeklyMission.voucher.service;

import com.weeklyMission.voucher.domain.Voucher;
import com.weeklyMission.voucher.repository.VoucherRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(Voucher voucher){
        return voucherRepository.createVoucher(voucher);
    }

    public List<Voucher> getVoucherList(){
        return voucherRepository.getVoucherList();
    }
}
