package com.wonu606.vouchermanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherDto;
import com.wonu606.vouchermanager.repository.VoucherRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("VoucherService 테스트")
public class VoucherServiceTest {

    private VoucherFactory factory;

    private VoucherRepository repository;

    private VoucherService voucherService;

    @BeforeEach
    public void setup() {
        this.factory = mock(VoucherFactory.class);
        this.repository = mock(VoucherRepository.class);
        this.voucherService = new VoucherService(factory, repository);
    }

    @Test
    @DisplayName("VoucherDto가 주어지고_createVoucher하면_바우처를 생성한다.")
    public void GivenVoucherDto_WhenCreateVoucher_ThenReturnExpectedVoucher() {
        // Given
        VoucherDto dto = mock(VoucherDto.class);
        Voucher expectedVoucher = mock(Voucher.class);

        given(factory.create(dto)).willReturn(expectedVoucher);
        given(repository.save(expectedVoucher)).willReturn(expectedVoucher);

        // When
        Voucher actualVoucher = voucherService.createVoucher(dto);

        // Then
        then(factory).should(times(1)).create(dto);
        then(repository).should(times(1)).save(expectedVoucher);
        assertEquals(expectedVoucher, actualVoucher);
    }

    @Test
    @DisplayName("Voucher들을 저장한 뒤_getVoucherList하면_바우처들을 반환한다.")
    public void GivenSavedVouchers_WhenGetVoucherList_ThenReturnsExpectedVouchers() {
        // Given
        List<Voucher> expectedVouchers = Arrays.asList(mock(Voucher.class), mock(Voucher.class));
        given(repository.findAll()).willReturn(expectedVouchers);

        // When
        List<Voucher> actualVouchers = voucherService.getVoucherList();

        // Then
        then(repository).should(times(1)).findAll();
        assertEquals(expectedVouchers, actualVouchers);
    }
}
