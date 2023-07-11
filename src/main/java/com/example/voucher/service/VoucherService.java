package com.example.voucher.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.repository.VoucherRepository;

@Service
@Transactional
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherDTO createVoucher(Voucher.Type voucherType, long discountValue) {
        Voucher createdVoucher = new Voucher(voucherType, discountValue);

        return voucherRepository.save(createdVoucher).toDTO();
    }

    public List<VoucherDTO> getVouchers() {
        return voucherRepository.findAll()
            .stream()
            .map(o -> o.toDTO())
            .toList();
    }

    public void removeVouchers() {
        voucherRepository.deleteAll();
    }

    public VoucherDTO getVoucherById(UUID voucherId){
        return voucherRepository.findById(voucherId).toDTO();
    }

}
