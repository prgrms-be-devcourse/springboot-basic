package org.prgrms.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.prgrms.application.domain.voucher.Voucher;
import org.prgrms.application.domain.voucher.VoucherDto;
import org.prgrms.application.domain.voucher.VoucherType;
import org.prgrms.application.entity.VoucherEntity;
import org.prgrms.application.repository.voucher.VoucherRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.prgrms.application.domain.voucher.VoucherType.FIXED;
import static org.prgrms.application.domain.voucher.VoucherType.PERCENT;

class VoucherServiceTest {

    @Mock
    private VoucherRepository voucherRepository;

    private VoucherService voucherService;
    private long voucherId = 1L;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        voucherService = new VoucherService(voucherRepository);
    }

    //TODO : 바우처서비스, 컨트롤러에 대한 실패 테스트도 만들어줘야 하는가..?

    @ParameterizedTest
    @DisplayName("바우처 생성에 성공한다.")
    @CsvSource({"FIXED,1000", "PERCENT,20"})
    public void createVoucherSuccessTest(String voucherTypeStr, double discountAmount) {
        //given
        VoucherType voucherType = VoucherType.findBySelection(voucherTypeStr);
        Voucher voucher = Voucher.of(voucherId, voucherType.applyPolicy(discountAmount));
        VoucherEntity voucherEntity = voucher.toEntity();

        when(voucherRepository.insert(any(VoucherEntity.class))).thenReturn(voucherEntity); // 저장된 바우처
        //TODO 문제가 발생하는 지점 : createVoucher의 리턴을 위한 메소드 실제 객체를 반환하려고 하는 메소드여서..?
        when(voucherEntity.toDomain()).thenReturn(voucher);

        //when
        Long voucher1 = voucherService.createVoucher(voucherType, discountAmount);

        //then
        Assertions.assertThat(voucher1).isNotNull();
        verify(voucherRepository, times(1)).insert(any(VoucherEntity.class));
    }

    @Test
    @DisplayName("바우처 조회 성공 테스트")
    public void getVouchersSuccess() {
        List<VoucherEntity> voucherEntities = new ArrayList<>();
        voucherEntities.add(new VoucherEntity(1L, FIXED.applyPolicy(1000)));
        voucherEntities.add(new VoucherEntity(1L, PERCENT.applyPolicy(33)));

        when(voucherRepository.findAll()).thenReturn(voucherEntities);

        List<VoucherDto> vouchers = voucherService.getVouchers();
        assertEquals(vouchers.size(), voucherEntities.size());
    }

    @ParameterizedTest
    @DisplayName("바우처 삭제 성공 테스트")
    @CsvSource({"1", "2", "3"})
    public void deleteVoucherSuccess(long voucherId) {
        voucherService.deleteVoucher(voucherId);

        verify(voucherRepository, times(1)).deleteById(voucherId);
    }

}