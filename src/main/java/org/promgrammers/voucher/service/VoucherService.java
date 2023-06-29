package org.promgrammers.voucher.service;


import lombok.RequiredArgsConstructor;
import org.promgrammers.voucher.domain.Voucher;
import org.promgrammers.voucher.domain.dto.VoucherListResponseDto;
import org.promgrammers.voucher.domain.dto.VoucherRequestDto;
import org.promgrammers.voucher.domain.dto.VoucherResponseDto;
import org.promgrammers.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;


    public VoucherResponseDto createVoucher(VoucherRequestDto voucherRequestDto) {
        Voucher voucher = voucherFactory.createVoucher(voucherRequestDto);
        voucherRepository.save(voucher);
        return new VoucherResponseDto(voucher.getId(), voucher.getAmount(), voucher.getVoucherType());
    }

    public Optional<VoucherResponseDto> findById(UUID id) {
        return Optional.ofNullable(voucherRepository.findById(id)
                .map(voucher -> new VoucherResponseDto(voucher.getId(), voucher.getAmount(), voucher.getVoucherType()))
                .orElseThrow(() -> new NoSuchElementException("해당 id는 존재하지 않습니다")));
    }

    public VoucherListResponseDto findAll(){
        List<VoucherResponseDto> voucherResponseDtoList  = voucherRepository.findAll()
                .stream()
                .map(voucher -> new VoucherResponseDto(voucher.getId(), voucher.getAmount(), voucher.getVoucherType()))
                .collect(Collectors.toList());

        return new VoucherListResponseDto(voucherResponseDtoList);
    }
}
