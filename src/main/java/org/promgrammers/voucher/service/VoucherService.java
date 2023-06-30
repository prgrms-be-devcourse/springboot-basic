package org.promgrammers.voucher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public VoucherResponseDto createVoucher(VoucherRequestDto voucherRequestDto) {
        Voucher voucher = voucherFactory.createVoucher(voucherRequestDto);
        voucherRepository.save(voucher);
        // 로그 메시지: Voucher 생성 로그
        log.info("Voucher 생성 - ID: {}, Amount: {}, VoucherType: {}", voucher.getId(), voucher.getAmount(), voucher.getVoucherType());
        return new VoucherResponseDto(voucher.getId(), voucher.getAmount(), voucher.getVoucherType());
    }

    public Optional<VoucherResponseDto> findById(UUID id) {
        return Optional.ofNullable(voucherRepository.findById(id)
                .map(voucher -> new VoucherResponseDto(voucher.getId(), voucher.getAmount(), voucher.getVoucherType()))
                .orElseThrow(() -> {
                    // 로그 메시지: ID를 찾을 수 없음 에러
                    log.error("ID를 찾을 수 없음 - ID: {}", id);
                    return new NoSuchElementException("해당 id는 존재하지 않습니다");
                }));
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
