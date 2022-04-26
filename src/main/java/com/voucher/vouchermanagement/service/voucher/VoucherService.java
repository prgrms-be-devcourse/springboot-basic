package com.voucher.vouchermanagement.service.voucher;

import com.voucher.vouchermanagement.dto.voucher.VoucherDto;
import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import com.voucher.vouchermanagement.repository.voucher.VoucherRepository;

import java.util.stream.Collectors;

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

    //@Transactional
    public UUID create(VoucherType voucherType, Long value) {
        Voucher voucher = voucherType.create(UUID.randomUUID(), value, LocalDateTime.now());

        this.voucherRepository.insert(voucher);

        return voucher.getVoucherId();

    }

    public VoucherDto findById(UUID id) {
        return VoucherDto.of(
                this.voucherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Voucher 정보를 찾을 수 없습니다."))
        );
    }

    //@Transactional(readOnly = true)
    public List<VoucherDto> findAll() {
        return this.voucherRepository.findAll()
                .stream()
                .map(VoucherDto::of)
                .collect(Collectors.toList());
    }

}
