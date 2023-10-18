package com.weeklyMission.voucher.service;

import com.weeklyMission.voucher.domain.Voucher;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.voucher.repository.VoucherRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponse createVoucher(Voucher voucher){
        Voucher createVoucher = voucherRepository.createVoucher(voucher);
        return new VoucherResponse(createVoucher);
    }

    public List<VoucherResponse> getVoucherList(){
        return voucherRepository.getVoucherList().stream()
            .map(VoucherResponse::new)
            .toList();
    }
}
