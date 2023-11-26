package com.weeklyMission.voucher.service;

import com.weeklyMission.client.VoucherType;
import com.weeklyMission.exception.ExceptionMessage;
import com.weeklyMission.exception.NotFoundException;
import com.weeklyMission.voucher.domain.Voucher;
import com.weeklyMission.voucher.dto.VoucherRequest;
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
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_VOUCHER.getMessage()));
        return VoucherResponse.of(voucher);
    }

    public List<VoucherResponse> findByType(String type){
        VoucherType voucherType = VoucherType.of(type);

        return voucherRepository.findByType(voucherType)
            .stream()
            .map(VoucherResponse::of)
            .toList();
    }

    public void deleteById(String id){
        voucherRepository.deleteById(id);
    }
}
