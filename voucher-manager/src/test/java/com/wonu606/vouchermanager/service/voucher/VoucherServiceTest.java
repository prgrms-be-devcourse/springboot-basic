package com.wonu606.vouchermanager.service.voucher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.service.voucher.param.VoucherCreateParam;
import com.wonu606.vouchermanager.repository.voucher.VoucherRepository;
import com.wonu606.vouchermanager.service.voucher.factory.VoucherFactory;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("VoucherService 테스트")
public class VoucherServiceTest {

    private VoucherFactory factory;

    private VoucherRepository voucherRepository;

    private VoucherService voucherService;

    @BeforeEach
    public void setup() {
        this.factory = mock(VoucherFactory.class);
        this.voucherRepository = mock(LocalMemoryVoucherVoucherRepository.class);
        this.voucherService = new VoucherService(factory, voucherRepository);
    }

    @Test
    @DisplayName("VoucherDto가 주어지고_createVoucher하면_바우처를 생성한다.")
    public void GivenVoucherDto_WhenCreateVoucher_ThenReturnExpectedVoucher() {
        // Given
        VoucherCreateParam dto = mock(VoucherCreateParam.class);
        Voucher expectedVoucher = mock(Voucher.class);

        given(factory.create(dto)).willReturn(expectedVoucher);
        given(voucherRepository.save(expectedVoucher)).willReturn(expectedVoucher);

        // When
        Voucher actualVoucher = voucherService.createVoucher(dto);

        // Then
        then(factory).should(times(1)).create(dto);
        then(voucherRepository).should(times(1)).save(expectedVoucher);
        assertEquals(expectedVoucher, actualVoucher);
    }

    @Test
    @DisplayName("Voucher들을 저장한 뒤_getVoucherList하면_바우처들을 반환한다.")
    public void GivenSavedVouchers_WhenGetVoucherList_ThenReturnsExpectedVouchers() {
        // Given
        List<Voucher> expectedVouchers = Arrays.asList(mock(Voucher.class), mock(Voucher.class));
        given(voucherRepository.findAll()).willReturn(expectedVouchers);

        // When
        List<Voucher> actualVouchers = voucherService.getVoucherList();

        // Then
        then(voucherRepository).should(times(1)).findAll();
        assertEquals(expectedVouchers, actualVouchers);
    }
}
