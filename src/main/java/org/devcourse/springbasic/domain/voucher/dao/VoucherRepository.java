package org.devcourse.springbasic.domain.voucher.dao;

import org.devcourse.springbasic.domain.voucher.dto.VoucherDto;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    VoucherDto.ResponseDto findById(UUID voucherId);
    List<VoucherDto.ResponseDto> findAll();
    UUID save(VoucherDto.SaveRequestDto voucherDto);
}