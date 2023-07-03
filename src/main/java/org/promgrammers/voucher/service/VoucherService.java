package org.promgrammers.voucher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.promgrammers.voucher.domain.Voucher;
import org.promgrammers.voucher.domain.dto.VoucherListResponseDto;
import org.promgrammers.voucher.domain.dto.VoucherCreateRequestDto;
import org.promgrammers.voucher.domain.dto.VoucherResponseDto;
import org.promgrammers.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public VoucherResponseDto createVoucher(VoucherCreateRequestDto voucherCreateRequestDto) {
        Voucher voucher = voucherFactory.createVoucher(voucherCreateRequestDto);
        voucherRepository.save(voucher);
        // 로그 메시지: Voucher 생성 로그
        log.info("Voucher 생성 - ID: {}, Amount: {}, VoucherType: {}", voucher.getId(), voucher.getAmount(), voucher.getVoucherType());
        log.info(voucher.toString());
        return new VoucherResponseDto(voucher.getId(), voucher.getAmount(), voucher.getVoucherType());
    }

    public VoucherListResponseDto findAll() {
        List<VoucherResponseDto> voucherResponseDtoList = voucherRepository.findAll()
                .stream()
                .map(voucher -> new VoucherResponseDto(voucher.getId(), voucher.getAmount(), voucher.getVoucherType()))
                .collect(Collectors.toList());

        // 로그 메시지: 모든 Voucher 조회
        log.info("모든 Voucher 조회 - Count: {}", voucherResponseDtoList.size());
        return new VoucherListResponseDto(voucherResponseDtoList);
    }
}
