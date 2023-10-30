package com.programmers.vouchermanagement.voucher.application;

import com.programmers.vouchermanagement.voucher.domain.*;
import com.programmers.vouchermanagement.voucher.dto.VoucherRequestDto;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import com.programmers.vouchermanagement.voucher.exception.VoucherNotFoundException;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

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

        voucherRepository.save(new Voucher(UUID.randomUUID(), discount, voucherType, voucherType.getVoucherPolicy()));
    }

    public List<VoucherResponseDto> readAllVoucher() {

        List<Voucher> vouchers = voucherRepository.findAll();

        List<VoucherResponseDto> voucherResponseDtos = vouchers.stream()
                .map(voucher -> new VoucherResponseDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscount()))
                .toList();

        return voucherResponseDtos;
    }

    public VoucherResponseDto readVoucherById(UUID voucherId) {

        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(VoucherNotFoundException::new);

        VoucherResponseDto voucherResponseDto = new VoucherResponseDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscount());

        return voucherResponseDto;
    }

    public void updateVoucher(UUID voucherId, VoucherRequestDto voucherRequestDto) {

        voucherRepository.findById(voucherId)
                .orElseThrow(VoucherNotFoundException::new);

        Long discount = voucherRequestDto.getDiscount();
        VoucherType voucherType = VoucherType.getVoucherTypeByName(voucherRequestDto.getVoucherType());

        voucherRepository.update(new Voucher(voucherId, discount, voucherType, voucherType.getVoucherPolicy()));
    }

    public void removeAllVoucher() {
        voucherRepository.deleteAll();
    }

    public void removeVoucherById(UUID voucherId) {

        voucherRepository.findById(voucherId)
                .orElseThrow(VoucherNotFoundException::new);

        voucherRepository.deleteById(voucherId);
    }
}
