package com.example.weeklymission3.services;

import com.example.weeklymission3.models.Voucher;
import com.example.weeklymission3.models.VoucherResponseDto;
import com.example.weeklymission3.models.VoucherType;
import com.example.weeklymission3.repositories.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class VoucherJdbcService implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherJdbcService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher createVoucher(String type, int discount) {
        Voucher voucher = new Voucher(UUID.randomUUID(), type, discount, LocalDateTime.now());
        return voucherRepository.insert(voucher);
    }

    @Override
    public List<VoucherResponseDto> getAllVouchers() {
        List<VoucherResponseDto> voucherDto = new ArrayList<>();
        voucherRepository.findAll().forEach((voucher -> voucherDto.add(toResponseDto(voucher))));
        return voucherDto;
    }

    private VoucherResponseDto toResponseDto(Voucher voucher) {
        UUID voucherId = voucher.getVoucherId();
        String type = voucher.getType();
        int discount = voucher.getDiscount();
        LocalDateTime createdAt = voucher.getCreatedAt();
        if (VoucherType.FIXED.toString().equals(type)) {
            type = VoucherType.transferVoucherType(VoucherType.FIXED);
        } else if (VoucherType.PERCENT.toString().equals(type)) {
            type = VoucherType.transferVoucherType(VoucherType.PERCENT);
        }
        return new VoucherResponseDto(voucherId, type, discount, createdAt);
    }

    @Override
    public Optional<Voucher> getVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    @Override
    public List<Voucher> getVouchersByTime(LocalDateTime startDate, LocalDateTime endDate) {
        return voucherRepository.findByTime(startDate, endDate);
    }

    @Override
    public List<Voucher> getVouchersByType(VoucherType type) {
        return voucherRepository.findByType(type);
    }

    @Override
    public void deleteAllVouchers() {
        voucherRepository.deleteAll();
    }
}
