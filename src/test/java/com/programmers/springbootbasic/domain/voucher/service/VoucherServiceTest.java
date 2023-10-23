package com.programmers.springbootbasic.domain.voucher.service;

import com.programmers.springbootbasic.common.utils.UUIDValueStrategy;
import com.programmers.springbootbasic.domain.voucher.dto.VoucherRequestDto;
import com.programmers.springbootbasic.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg;
import com.programmers.springbootbasic.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Voucher Service Test")
class VoucherServiceTest {
    private static final long VALUE = 50L;
    private static final UUID VOUCHER_ID = UUID.randomUUID();
    @InjectMocks
    private VoucherService voucherService;
    @Mock
    private VoucherRepository voucherRepository;
    private final UUIDValueStrategy uuidValueStrategy = () -> VOUCHER_ID;

    @BeforeEach
    void init() {
        voucherService = new VoucherService(voucherRepository, uuidValueStrategy);
    }

    @Test
    void testCreateVoucherSuccess() {
        // Arrange
        long expectedBeforeDiscount = 100L;
        VoucherRequestDto voucherRequestDto = VoucherRequestDto.builder()
                .voucherType(1)
                .value(VALUE)
                .build();
        when(voucherRepository.save(any(Voucher.class))).thenReturn(VoucherType.of(1, VOUCHER_ID, VALUE));
        // Act
        Voucher actualResult = voucherService.createVoucher(voucherRequestDto);
        // Assert
        assertThat(actualResult).isInstanceOf(FixedAmountVoucher.class);
        assertThat(actualResult.getVoucherId()).isEqualTo(VOUCHER_ID);
    }

    @Test
    void testFindVoucherByIdSuccess() {
        // Arrange
        int expectedVoucherType = 1;
        Voucher expectedResult = VoucherType.of(expectedVoucherType, VOUCHER_ID, VALUE);
        when(voucherRepository.findById(VOUCHER_ID)).thenReturn(Optional.of(expectedResult));
        VoucherRequestDto voucherRequestDto = VoucherRequestDto.builder()
                .voucherId(VOUCHER_ID)
                .build();
        // Act
        Voucher actualResult = voucherService.findVoucherById(voucherRequestDto);
        // Assert
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void testFindVoucherByIdFail() {
        // Arrange
        UUID expectedVoucherId = UUID.randomUUID();
        when(voucherRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        VoucherRequestDto voucherRequestDto = VoucherRequestDto.builder()
                .voucherId(expectedVoucherId)
                .build();
        // Act & Assert
        Throwable actualResult = assertThrows(RuntimeException.class, () -> voucherService.findVoucherById(voucherRequestDto));
        assertThat(actualResult.getMessage()).isEqualTo(ErrorMsg.VOUCHER_NOT_FOUND.getMessage());
    }

    @Test
    void testUpdateVoucherSuccess() {
        // Arrange
        int expectedVoucherType = 1;
        long expectedVoucherValue = 60L;
        Voucher expectedResult = VoucherType.of(expectedVoucherType, VOUCHER_ID, VALUE);
        when(voucherRepository.findById(VOUCHER_ID)).thenReturn(Optional.of(expectedResult));
        VoucherRequestDto voucherRequestDto = VoucherRequestDto.builder()
                .voucherId(VOUCHER_ID)
                .value(expectedVoucherValue)
                .build();
        // Act
        voucherService.updateVoucher(voucherRequestDto);
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
        List<Voucher> actualResult = voucherService.findAllVouchers();
        // Assert
        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(expectedResult);
    }

}