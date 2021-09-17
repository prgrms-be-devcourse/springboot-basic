package org.prgrms.kdt.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.factory.VoucherFactory;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class VoucherServiceTest {
    private static Logger logger = LoggerFactory.getLogger(VoucherServiceTest.class);

    @Test
    @DisplayName("voucher가 생성되어야 한다.")
    void testCreateVoucher() {
        // given
        VoucherRepository voucherRepositoryMock = mock(VoucherRepository.class);
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);

        // 1. static mock 객체를 만들어 줘야하고요
        var voucherFactoryMockStatic = mockStatic(VoucherFactory.class);
        // 2. 기본적인 mock 객체 만들기
        var voucherFactoryMock = mock(VoucherFactory.class);

        when(voucherFactoryMock.createVoucher(
                VoucherType.FIXED, fixedAmountVoucher.getVoucherId(), 100
        )).thenReturn(fixedAmountVoucher);
        voucherFactoryMockStatic.when((MockedStatic.Verification) VoucherFactory.getInstance()).thenReturn(voucherFactoryMock);

        when(voucherRepositoryMock.insert(fixedAmountVoucher))
                .thenReturn(fixedAmountVoucher);

        VoucherService voucherService = new VoucherService(voucherRepositoryMock);

        // when
        Voucher returnVoucher = voucherService.createVoucher(
                VoucherType.FIXED,
                fixedAmountVoucher.getVoucherId(),
                100);
        logger.info(returnVoucher.toString());

        // THEN
        logger.info("given: {}", fixedAmountVoucher.toString());
        logger.info("when: {}", returnVoucher.toString());
        assertThat(returnVoucher).isEqualTo(fixedAmountVoucher);

        // Verify
        var inOrder = Mockito.inOrder(voucherRepositoryMock, voucherFactoryMock);
        voucherFactoryMockStatic.verify(VoucherFactory::getInstance);
        inOrder.verify(voucherFactoryMock).createVoucher(
                VoucherType.FIXED, fixedAmountVoucher.getVoucherId(), 100
        );
        inOrder.verify(voucherRepositoryMock).insert(fixedAmountVoucher);
    }

    @Test
    @DisplayName("uuid로 부터 voucher를 얻을 수 있어야 한다.")
    void testGetVoucher() {
        // GIVEN
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);

        VoucherRepository voucherRepositoryMock = mock(VoucherRepository.class);
        when(voucherRepositoryMock.findById(fixedAmountVoucher.getVoucherId())).thenReturn(Optional.of(fixedAmountVoucher));

        // WHEN
        VoucherService voucherService = new VoucherService(voucherRepositoryMock);
        Voucher returnValue = voucherService.getVoucher(fixedAmountVoucher.getVoucherId());

        // THEN
        assertThat(returnValue).isEqualTo(fixedAmountVoucher);
        // VERIFY
        var inOrder = Mockito.inOrder(voucherRepositoryMock);
        inOrder.verify(voucherRepositoryMock).findById(fixedAmountVoucher.getVoucherId());
    }

    @Test
    @DisplayName("voucher가 없는 경우 예외를 뱉는다. 퉤")
    void testGetVoucherNullPointerException() {
        // GIVEN
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        VoucherRepository voucherRepositoryMock = mock(VoucherRepository.class);
        when(voucherRepositoryMock.findById(fixedAmountVoucher.getVoucherId())).thenReturn(Optional.empty());

        // WHEN
        VoucherService voucherService = new VoucherService(voucherRepositoryMock);


        // THEN
        assertThatThrownBy(() -> {
            Voucher returnValue = voucherService.getVoucher(fixedAmountVoucher.getVoucherId());
        }).isInstanceOf(NullPointerException.class);

        // VERIFY
        var inOrder = Mockito.inOrder(voucherRepositoryMock);
        inOrder.verify(voucherRepositoryMock).findById(fixedAmountVoucher.getVoucherId());
    }

    @Test
    @DisplayName("voucher 리스트를 반환할 수 있어야 한다.")
    void testGetAllVoucher() {
        // GIVEN
        var voucherRepositoryMock = mock(VoucherRepository.class);
        List<Voucher> givenList = new ArrayList<>();
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        givenList.add(fixedAmountVoucher);
        when(voucherRepositoryMock.find()).thenReturn(givenList);
        var voucherService = new VoucherService(voucherRepositoryMock);

        // WHEN
        var returnValue = voucherService.getAllVoucher();

        // THEN
        assertThat(returnValue).isEqualTo(givenList);
        Mockito.inOrder(voucherRepositoryMock).verify(voucherRepositoryMock).find();
    }
}