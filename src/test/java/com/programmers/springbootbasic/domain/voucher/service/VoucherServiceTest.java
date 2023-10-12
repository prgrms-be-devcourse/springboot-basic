package com.programmers.springbootbasic.domain.voucher.service;

import com.programmers.springbootbasic.domain.voucher.dto.VoucherRequestDto;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.repository.VoucherMemoryRepository;
import com.programmers.springbootbasic.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Voucher Service Test")
class VoucherServiceTest {
    private VoucherService voucherService;
    private VoucherRepository voucherRepository;

    @BeforeEach
    void init() {
        voucherRepository = new VoucherMemoryRepository();
        voucherService = new VoucherService(voucherRepository);
    }

    @Test
    void testCreateVoucher() {
        // Arrange
        long expectedValue = 50L;
        VoucherRequestDto voucherRequestDto = VoucherRequestDto.builder()
                .voucherType(1)
                .value(expectedValue)
                .build();
        // Act
        Voucher actualResult = voucherService.createVoucher(voucherRequestDto);
        // Assert
        Voucher exceptedResult = voucherRepository.findAll().get(0);
        assertEquals(exceptedResult, actualResult);
    }

    @Test
    void testFindAllVoucher() {
        // Arrange
        long expectedValue = 50L;
        Voucher voucher1 = VoucherType.of(1, UUID.randomUUID(), expectedValue);
        Voucher voucher2 = VoucherType.of(2, UUID.randomUUID(), expectedValue);
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        // Act
        List<Voucher> actualResult = voucherService.findAllVoucher();
        // Assert
        assertEquals(2, actualResult.size());
    }

}