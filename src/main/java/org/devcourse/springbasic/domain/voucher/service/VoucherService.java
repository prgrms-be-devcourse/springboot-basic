package org.devcourse.springbasic.domain.voucher.service;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.voucher.dao.VoucherRepository;
import org.devcourse.springbasic.domain.voucher.dto.VoucherDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public void save(VoucherDto.SaveRequestDto voucherDto) {
        voucherRepository.save(voucherDto);
    }

    public List<VoucherDto.ResponseDto> findAll() {
        return voucherRepository.findAll();
    }

    public VoucherDto.ResponseDto findById(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }
}