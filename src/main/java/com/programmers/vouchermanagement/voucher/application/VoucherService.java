package com.programmers.vouchermanagement.voucher.application;

import com.programmers.vouchermanagement.voucher.domain.*;
import com.programmers.vouchermanagement.voucher.dto.VoucherRequestDto;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import com.programmers.vouchermanagement.voucher.exception.VoucherNotFoundException;
import com.programmers.vouchermanagement.voucher.mapper.VoucherPolicyMapper;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(VoucherRequestDto voucherRequestDto) {

        Long discount = voucherRequestDto.getDiscount();
        VoucherType voucherType = VoucherType.getVoucherTypeByName(voucherRequestDto.getVoucherType());
        VoucherPolicy voucherPolicy = VoucherPolicyMapper.toEntity(discount, voucherType);

        voucherRepository.save(new Voucher(UUID.randomUUID(), voucherType, voucherPolicy, LocalDateTime.now()));
    }

    public List<VoucherResponseDto> readAllVoucher() {

        List<Voucher> vouchers = voucherRepository.findAll();

        List<VoucherResponseDto> voucherResponseDtos = vouchers.stream()
                .map(voucher -> new VoucherResponseDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getVoucherPolicy().getDiscount(), voucher.getCreatedAt()))
                .toList();

        return voucherResponseDtos;
    }

    public VoucherResponseDto readVoucherById(UUID voucherId) {

        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherNotFoundException(voucherId));

        VoucherResponseDto voucherResponseDto = new VoucherResponseDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getVoucherPolicy().getDiscount(), voucher.getCreatedAt());

        return voucherResponseDto;
    }

    public void updateVoucher(UUID voucherId, VoucherRequestDto voucherRequestDto) {

        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherNotFoundException(voucherId));

        Long discount = voucherRequestDto.getDiscount();
        VoucherType voucherType = VoucherType.getVoucherTypeByName(voucherRequestDto.getVoucherType());
        VoucherPolicy voucherPolicy = VoucherPolicyMapper.toEntity(discount, voucherType);

        voucherRepository.update(new Voucher(voucherId, voucherType, voucherPolicy, voucher.getCreatedAt()));
    }

    public void removeAllVoucher() {
        voucherRepository.deleteAll();
    }

    public void removeVoucherById(UUID voucherId) {

        voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherNotFoundException(voucherId));

        voucherRepository.deleteById(voucherId);
    }

    public List<VoucherResponseDto> readAllVoucherByCreatedAtAndType(LocalDateTime createdAt, VoucherType voucherType) {

        List<Voucher> vouchers = voucherRepository.findAllByCreatedAtAndVoucherType(createdAt, voucherType);

        List<VoucherResponseDto> voucherResponseDtos = vouchers.stream()
                .map(voucher -> new VoucherResponseDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getVoucherPolicy().getDiscount(), voucher.getCreatedAt()))
                .toList();

        return voucherResponseDtos;
    }
}
