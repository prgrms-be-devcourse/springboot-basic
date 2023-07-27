package org.devcourse.springbasic.domain.voucher.service;

import org.devcourse.springbasic.domain.voucher.dao.VoucherRepository;
import org.devcourse.springbasic.domain.voucher.domain.Voucher;
import org.devcourse.springbasic.domain.voucher.domain.VoucherFactory;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;
import org.devcourse.springbasic.domain.voucher.dto.VoucherDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VoucherServiceTest {

    @Mock
    private VoucherRepository voucherRepository;

    @InjectMocks
    private VoucherService voucherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("바우처 추가")
    void save() {
        // given
        VoucherDto.SaveRequest saveRequest = new VoucherDto.SaveRequest(VoucherType.FIXED_AMOUNT, 500L);
        Voucher savedVoucher = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, 500L);
        // when
        when(voucherRepository.save(any(Voucher.class))).thenReturn(UUID.randomUUID());
        UUID generatedUUID = voucherService.save(saveRequest);
        // then
        assertThat(generatedUUID).isNotNull();
        verify(voucherRepository, times(1)).save(any());
    }


    @Test
    @DisplayName("바우처 삭제")
    void deleteById() {
        // given
        UUID voucherId = UUID.randomUUID();
        // when
        voucherService.deleteById(voucherId);
        // then
        verify(voucherRepository, times(1)).deleteById(voucherId);
    }

    @Test
    @DisplayName("바우처 검색 - 이름, 기간, 할인타입 모두 지정")
    void findByCriteria_AllParamsSpecified() {
        // Given
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, 500L);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT_DISCOUNT, 10L);
        when(voucherRepository.findByCriteria(any(), any(), any())).thenReturn(Collections.singletonList(voucher1));

        // When
        VoucherDto.Request request = new VoucherDto.Request(
                LocalDate.of(2023, 7, 1),
                LocalDate.of(2023, 7, 31 ),
                "FIXED_AMOUNT"
        );
        List<VoucherDto.Response> foundVouchers = voucherService.findByCriteria(request);

        // Then
        assertThat(foundVouchers).hasSize(1);
        assertThat(foundVouchers.get(0)).isEqualToComparingFieldByField(new VoucherDto.Response(
                voucher1.getVoucherId(),
                VoucherType.FIXED_AMOUNT.getVoucherName(),
                "500원"
        ));
    }

    @Test
    @DisplayName("바우처 검색 - 이름, 기간만 지정")
    void findByCriteria_NameAndPeriodSpecified() {
        // Given
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, 500L);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT_DISCOUNT, 10L);
        when(voucherRepository.findByCriteria(any(), any(), any())).thenReturn(Collections.singletonList(voucher1));

        // When
        VoucherDto.Request request = new VoucherDto.Request(
                LocalDate.of(2023, 7, 1),
                LocalDate.of(2023, 7, 31 ),
                null
        );
        List<VoucherDto.Response> foundVouchers = voucherService.findByCriteria(request);

        // Then
        assertThat(foundVouchers).hasSize(1);
        assertThat(foundVouchers.get(0)).isEqualToComparingFieldByField(new VoucherDto.Response(
                voucher1.getVoucherId(),
                VoucherType.FIXED_AMOUNT.getVoucherName(),
                "500원"
        ));
    }

    @Test
    @DisplayName("바우처 검색 - 이름만 지정")
    void findByCriteria_NameSpecified() {
        // Given
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, 500L);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT_DISCOUNT, 10L);
        when(voucherRepository.findByCriteria(any(), any(), any())).thenReturn(Collections.singletonList(voucher1));

        // When
        VoucherDto.Request request = new VoucherDto.Request(
                null,
                null,
                "FIXED_AMOUNT"
        );
        List<VoucherDto.Response> foundVouchers = voucherService.findByCriteria(request);

        // Then
        assertThat(foundVouchers).hasSize(1);
        assertThat(foundVouchers.get(0)).isEqualToComparingFieldByField(new VoucherDto.Response(
                voucher1.getVoucherId(),
                VoucherType.FIXED_AMOUNT.getVoucherName(),
                "500원"
        ));
    }

    @Test
    @DisplayName("바우처 검색 - 아무 필터 없이 전체 검색")
    void findByCriteria_NoFilterSpecified() {
        // Given
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, 500L);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT_DISCOUNT, 10L);
        when(voucherRepository
                .findByCriteria(null, null, null))
                .thenReturn(List.of(voucher1, voucher2)
        );

        // When
        VoucherDto.Request request = new VoucherDto.Request(null, null, null);
        List<VoucherDto.Response> foundVouchers = voucherService.findByCriteria(request);

        // Then
        assertThat(foundVouchers).hasSize(2);
        assertThat(foundVouchers.get(0)).isEqualToComparingFieldByField(new VoucherDto.Response(
                voucher1.getVoucherId(),
                VoucherType.FIXED_AMOUNT.getVoucherName(),
                "500원"
        ));
        assertThat(foundVouchers.get(1)).isEqualToComparingFieldByField(new VoucherDto.Response(
                voucher2.getVoucherId(),
                VoucherType.PERCENT_DISCOUNT.getVoucherName(),
                "10%"
        ));
    }

    @Test
    @DisplayName("바우처 조회 - 존재하는 ID로 조회")
    void findById_ExistingId() {
        // Given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, 500L);
        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));

        // When
        VoucherDto.Response foundVoucher = voucherService.findById(voucher.getVoucherId());

        // Then
        assertThat(foundVoucher).isEqualToComparingFieldByField(new VoucherDto.Response(
                voucher.getVoucherId(),
                VoucherType.FIXED_AMOUNT.getVoucherName(),
                "500원"
        ));
    }

    @Test
    @DisplayName("바우처 조회 - 존재하지 않는 ID로 조회")
    void findById_NonExistingId() {
        // Given
        UUID nonExistingId = UUID.randomUUID();
        when(voucherRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // When, Then
        assertThatThrownBy(() -> voucherService.findById(nonExistingId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("존재하지 않는 바우처입니다.");
    }
}
