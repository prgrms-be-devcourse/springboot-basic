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
        return new VoucherResponse(createVoucher);
    }

    public List<VoucherResponse> findAll(){
        return voucherRepository.findAll().stream()
            .map(VoucherResponse::new)
            .toList();
    }

    public VoucherResponse findById(UUID id){
        Voucher voucher = voucherRepository.findById(id)
            .orElseThrow();
        return new VoucherResponse(voucher);
    }

    public void deleteById(UUID id){
        voucherRepository.deleteById(id);
    }
}
