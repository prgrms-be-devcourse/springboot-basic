package org.prgrms.springorder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springorder.domain.FixedAmountVoucher;
import org.prgrms.springorder.domain.PercentDiscountVoucher;
import org.prgrms.springorder.domain.Voucher;
import org.prgrms.springorder.domain.VoucherType;
import org.prgrms.springorder.repository.VoucherRepository;
import org.prgrms.springorder.request.VoucherCreateRequest;

@TestInstance(Lifecycle.PER_CLASS)
class VoucherServiceTest {

    @BeforeAll
    public void beforeClass() {
        mockStatic(VoucherFactory.class);
    }

    @DisplayName("Voucher 생성 테스트 - Fixed 바우처가 정상적으로 생성된다. ")
    @Test
    void createFixedVoucherTest() {
        //given
        VoucherRepository voucherRepositoryMock = mock(VoucherRepository.class);
        VoucherService voucherService = new VoucherService(voucherRepositoryMock);
        long discountAmount = 100;
        VoucherType fixedVoucherType = VoucherType.FIXED;

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(),
            discountAmount);

        VoucherCreateRequest voucherCreateRequest =
            VoucherCreateRequest.of(fixedVoucherType, discountAmount);

        given(VoucherFactory.create(voucherCreateRequest))
            .willReturn(fixedAmountVoucher);

        when(voucherRepositoryMock.insert(any()))
            .thenReturn(fixedAmountVoucher);

        //when
        Voucher createdVoucher = voucherService.createVoucher(voucherCreateRequest);

        //then
        assertNotNull(createdVoucher);
        assertEquals(fixedAmountVoucher.getVoucherId(), createdVoucher.getVoucherId());

        assertEquals(fixedVoucherType, createdVoucher.getVoucherType());
        assertEquals(FixedAmountVoucher.class, createdVoucher.getClass());
        assertEquals(10, createdVoucher.discount(110));

        verify(voucherRepositoryMock).insert(fixedAmountVoucher);
    }

    @DisplayName("Voucher 생성 테스트 - Percent 바우처가 정상적으로 생성된다. ")
    @Test
    void createPercentVoucherTest() {
        //given
        VoucherRepository voucherRepositoryMock = mock(VoucherRepository.class);
        VoucherService voucherService = new VoucherService(voucherRepositoryMock);

        long discountAmount = 50;
        VoucherType percentVoucherType = VoucherType.PERCENT;

        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(
            UUID.randomUUID(),
            discountAmount);

        VoucherCreateRequest voucherCreateRequest =
            VoucherCreateRequest.of(percentVoucherType, discountAmount);

        given(VoucherFactory.create(voucherCreateRequest))
            .willReturn(percentDiscountVoucher);

        when(voucherRepositoryMock.insert(any()))
            .thenReturn(percentDiscountVoucher);

        //when
        Voucher createdVoucher = voucherService.createVoucher(voucherCreateRequest);

        //then
        assertNotNull(createdVoucher);
        assertEquals(percentDiscountVoucher.getVoucherId(), createdVoucher.getVoucherId());

        assertEquals(percentVoucherType, createdVoucher.getVoucherType());
        assertEquals(PercentDiscountVoucher.class, createdVoucher.getClass());
        assertEquals(50, createdVoucher.discount(100));

        verify(voucherRepositoryMock).insert(percentDiscountVoucher);
    }

    @Test
    @DisplayName("Voucher findAll() 테스트 - 비어있지 않으면, 저장된 모든 바우처 정보가 조회된다.")
    void findAllNotEmptyListTest() {
        //given
        VoucherRepository voucherRepositoryMock = mock(VoucherRepository.class);
        VoucherService voucherService = new VoucherService(voucherRepositoryMock);
        int size = 5;

        when(voucherRepositoryMock.findAll()).thenReturn(
            IntStream.range(0, size)
                .mapToObj(i -> i % 2 == 0
                    ? new FixedAmountVoucher(UUID.randomUUID(), i)
                    : new PercentDiscountVoucher(UUID.randomUUID(), i))
                .collect(Collectors.toList())
        );

        //when
        List<Voucher> vouchers = voucherService.findAll();

        //then
        assertNotNull(vouchers);
        assertEquals(size, vouchers.size());

        verify(voucherRepositoryMock).findAll();
    }

    @Test
    @DisplayName("Voucher findAll() 테스트 - 비어있으면 빈 리스트가 리턴된다.")
    void findAllReturnEmptyListTest() {
        //given
        VoucherRepository voucherRepositoryMock = mock(VoucherRepository.class);
        VoucherService voucherService = new VoucherService(voucherRepositoryMock);

        when(voucherRepositoryMock.findAll())
            .thenReturn(new ArrayList<>());

        //when
        List<Voucher> vouchers = voucherService.findAll();

        //then
        assertNotNull(vouchers);
        assertTrue(vouchers.isEmpty());

        verify(voucherRepositoryMock).findAll();
    }


}