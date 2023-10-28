package com.weeklyMission.voucher.service;

import com.weeklyMission.voucher.domain.Voucher;
import com.weeklyMission.voucher.dto.VoucherRequest;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.voucher.repository.VoucherRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponse save(VoucherRequest voucher){
        Voucher createVoucher = voucherRepository.save(voucher.toEntity());
        return VoucherResponse.of(createVoucher);
    }

    public List<VoucherResponse> findAll(){
        return voucherRepository.findAll().stream()
            .map(VoucherResponse::of)
            .toList();
    }

    public VoucherResponse findById(String id){
        Voucher voucher = voucherRepository.findById(id)
            .orElseThrow();
        return VoucherResponse.of(voucher);
    }

    public void deleteById(String id){
        voucherRepository.deleteById(id);
    }
}
