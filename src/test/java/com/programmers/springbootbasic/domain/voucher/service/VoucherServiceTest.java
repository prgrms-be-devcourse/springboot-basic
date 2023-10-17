package com.programmers.springbootbasic.domain.voucher.service;

import com.programmers.springbootbasic.domain.voucher.dto.VoucherRequestDto;
import com.programmers.springbootbasic.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Voucher Service Test")
class VoucherServiceTest {
    @InjectMocks
    private VoucherService voucherService;
    @Mock
    private VoucherRepository voucherRepository;

    @Test
    void testCreateVoucher() {
        // Arrange
        long expectedValue = 50L;
        long expectedBeforeDiscount = 100L;
        VoucherRequestDto voucherRequestDto = VoucherRequestDto.builder()
                .voucherType(1)
                .value(expectedValue)
                .build();
        when(voucherRepository.save(any(Voucher.class))).thenReturn(VoucherType.of(1, UUID.randomUUID(), expectedValue));
        // Act
        Voucher actualResult = voucherService.createVoucher(voucherRequestDto);
        // Assert
        assertInstanceOf(FixedAmountVoucher.class, actualResult);
        assertEquals(expectedBeforeDiscount - expectedValue, actualResult.discount(expectedBeforeDiscount));
    }

    @Test
    void testFindAllVoucher() {
        // Arrange
        long expectedValue = 50L;
        Voucher voucher1 = VoucherType.of(1, UUID.randomUUID(), expectedValue);
        Voucher voucher2 = VoucherType.of(2, UUID.randomUUID(), expectedValue);
        List<Voucher> expectedResult = List.of(voucher1, voucher2);
        when(voucherRepository.findAll()).thenReturn(expectedResult);
        // Act
        List<Voucher> actualResult = voucherService.findAllVoucher();
        // Assert
        assertNotNull(actualResult);
        assertEquals(expectedResult.size(), actualResult.size());
    }

}