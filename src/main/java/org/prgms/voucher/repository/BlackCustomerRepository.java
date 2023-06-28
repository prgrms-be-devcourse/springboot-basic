package org.prgms.voucher.repository;

import org.prgms.voucher.dto.BlackCustomerResponseDto;

import java.util.List;

public interface BlackCustomerRepository {
    List<BlackCustomerResponseDto> findAll();
}
