package com.programmers.vouchermanagement.voucher.application;

import com.programmers.vouchermanagement.voucher.domain.FixedAmountVoucher;
import com.programmers.vouchermanagement.voucher.domain.PercentDiscountVoucher;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
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
        VoucherType voucherType = voucherRequestDto.getVoucherType();

        switch (voucherType) {

            case FIXED -> voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), discount, voucherType));

            case PERCENT -> voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), discount, voucherType));
        }
    }

    public List<VoucherResponseDto> readAllVoucher() {

        List<Voucher> vouchers = voucherRepository.findAll();

        if (vouchers.isEmpty()) {
            throw new VoucherNotFoundException();
        }

        List<VoucherResponseDto> voucherResponseDtos = vouchers.stream()
                .map(voucher -> new VoucherResponseDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscount()))
                .toList();

        return voucherResponseDtos;
    }
}
