package org.prgrms.springorder.domain.voucher.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.MockedStatic;
import org.prgrms.springorder.domain.voucher.api.request.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.domain.voucher.repository.VoucherRepository;
import org.prgrms.springorder.global.exception.EntityNotFoundException;

@TestInstance(Lifecycle.PER_CLASS)
class VoucherServiceTest {

    private MockedStatic<VoucherFactory> voucherFactoryMockedStatic;

    private VoucherRepository voucherRepositoryMock;
    private VoucherService voucherService;

    @BeforeAll
    public void beforeAll() {
        voucherFactoryMockedStatic = mockStatic(VoucherFactory.class);
        voucherService = new VoucherService(voucherRepositoryMock);
    }

    @BeforeEach
    void beforeEach() {
        voucherRepositoryMock = mock(VoucherRepository.class);
        voucherService = new VoucherService(voucherRepositoryMock);
    }

    @AfterAll
    public void afterAll() {
        voucherFactoryMockedStatic.close();
    }

    @DisplayName("Voucher 생성 테스트 - Fixed 바우처가 정상적으로 생성된다. ")
    @Test
    void createFixedVoucherTest() {
        //given
        long discountAmount = 100;
        VoucherType fixedVoucherType = VoucherType.FIXED;

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(),
            discountAmount);

        VoucherCreateRequest voucherCreateRequest =
            VoucherCreateRequest.of(fixedVoucherType, discountAmount);

        when(VoucherFactory.create(voucherCreateRequest))
            .thenReturn(fixedAmountVoucher);

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
        long discountAmount = 50;
        VoucherType percentVoucherType = VoucherType.PERCENT;

        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(
            UUID.randomUUID(),
            discountAmount);

        VoucherCreateRequest voucherCreateRequest =
            VoucherCreateRequest.of(percentVoucherType, discountAmount);

        when(VoucherFactory.create(voucherCreateRequest))
            .thenReturn(percentDiscountVoucher);

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
        int size = 5;

        when(voucherRepositoryMock.findAll()).thenReturn(
            getVoucherList(size)
        );

        //when
        List<Voucher> vouchers = voucherService.findAll();

        //then
        assertNotNull(vouchers);
        assertEquals(size, vouchers.size());

        verify(voucherRepositoryMock).findAll();
    }

    @Test
    @DisplayName("Voucher findAll() 테스트 - 비어있으면 예외를 발생시킨다.")
    void findAllReturnEmptyListTest() {
        //given
        when(voucherRepositoryMock.findAll())
            .thenReturn(new ArrayList<>());

        //when & then
        assertThrows(EntityNotFoundException.class, voucherService::findAll);
    }

    @Test
    @DisplayName("Voucher findAllConvertedToString() 테스트 - 비어있지 않으면, 저장된 모든 바우처 정보가 조회된다.")
    void findAllConvertedToStringNotEmptyListTest() {
        //given
        int size = 5;
        List<Voucher> voucherList = getVoucherList(size);

        List<String> convertedToStringVoucherList = voucherList.stream()
            .map(Objects::toString)
            .collect(Collectors.toList());

        when(voucherRepositoryMock.findAll()).thenReturn(
            voucherList
        );

        //when
        List<String> vouchers = voucherService.findAllConvertedToString();

        //then
        assertNotNull(vouchers);
        assertEquals(size, vouchers.size());
        assertLinesMatch(convertedToStringVoucherList, vouchers);

        verify(voucherRepositoryMock).findAll();
    }

    @Test
    @DisplayName("Voucher findAllConvertedToString() 테스트 - 비어있으면 예외를 발생시킨다.")
    void findAllConvertedToStringReturnEmptyListTest() {
        //given
        when(voucherRepositoryMock.findAll())
            .thenReturn(new ArrayList<>());

        //when & then
        assertThrows(EntityNotFoundException.class, voucherService::findAllConvertedToString);
    }


    @Test
    @DisplayName("Voucher existsVoucher() 테스트 - 있으면 true를 반환한다.")
    void existsByVoucherIdTrueTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(
            voucherId,
            100L);

        when(voucherRepositoryMock.findById(voucherId))
            .thenReturn(Optional.of(percentDiscountVoucher));

        //when & then
        assertTrue(voucherService.existsVoucher(voucherId));
    }

    @Test
    @DisplayName("Voucher existsVoucher() 테스트 - 없으면 false를  반환한다.")
    void existsByVoucherIdFalseTest() {
        //given
        UUID voucherId = UUID.randomUUID();

        when(voucherRepositoryMock.findById(voucherId))
            .thenReturn(Optional.empty());

        //when & then
        assertFalse(voucherService.existsVoucher(voucherId));
    }

    private List<Voucher> getVoucherList(int size) {
        return IntStream.range(0, size)
            .mapToObj(i -> i % 2 == 0
                ? new FixedAmountVoucher(UUID.randomUUID(), i)
                : new PercentDiscountVoucher(UUID.randomUUID(), i))
            .collect(Collectors.toList());
    }


}