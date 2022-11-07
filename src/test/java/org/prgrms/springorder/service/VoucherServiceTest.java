package org.prgrms.springorder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springorder.domain.FixedAmountVoucher;
import org.prgrms.springorder.domain.PercentDiscountVoucher;
import org.prgrms.springorder.domain.Voucher;
import org.prgrms.springorder.domain.VoucherType;
import org.prgrms.springorder.repository.VoucherRepository;
import org.prgrms.springorder.request.VoucherCreateRequest;
import org.prgrms.springorder.response.VoucherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VoucherServiceTest {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherRepository voucherRepository;

    @BeforeEach
    void beforeEach() {
        voucherRepository.deleteAll();
    }

    @DisplayName("Voucher 생성 테스트 - Fixed 바우처가 정상적으로 생성되어 저장된다. ")
    @Test
    void createFixedVoucherTest() {
        //given
        long discountAmount = 100;
        VoucherType fixedVoucherType = VoucherType.FIXED;
        VoucherCreateRequest voucherCreateRequest =
            VoucherCreateRequest.of(fixedVoucherType, discountAmount);

        //when
        UUID createdVoucherId = voucherService.createVoucher(voucherCreateRequest);

        Optional<Voucher> optionalVoucher = voucherRepository.findById(createdVoucherId);

        //then
        assertTrue(optionalVoucher.isPresent());
        Voucher findFixedVoucher = optionalVoucher.get();
        assertNotNull(createdVoucherId);
        assertEquals(FixedAmountVoucher.class, findFixedVoucher.getClass());
        assertEquals(fixedVoucherType, findFixedVoucher.getVoucherType());
        assertEquals(FixedAmountVoucher.class, findFixedVoucher.getClass());
        assertEquals(fixedVoucherType, findFixedVoucher.getVoucherType());

    }

    @DisplayName("Voucher 생성 테스트 - Percent 바우처가 정상적으로 생성되어 저장된다. ")
    @Test
    void createPercentVoucherTest() {
        //given
        long discountAmount = 10;
        VoucherType percentVoucherType = VoucherType.PERCENT;
        VoucherCreateRequest voucherCreateRequest =
            VoucherCreateRequest.of(percentVoucherType, discountAmount);

        //when
        UUID createdVoucherId = voucherService.createVoucher(voucherCreateRequest);

        Optional<Voucher> optionalVoucher = voucherRepository.findById(createdVoucherId);

        //then
        assertTrue(optionalVoucher.isPresent());
        Voucher findPercentVoucher = optionalVoucher.get();
        assertNotNull(createdVoucherId);
        assertEquals(PercentDiscountVoucher.class, findPercentVoucher.getClass());
        assertEquals(percentVoucherType, findPercentVoucher.getVoucherType());
        assertEquals(PercentDiscountVoucher.class, findPercentVoucher.getClass());
        assertEquals(percentVoucherType, findPercentVoucher.getVoucherType());
    }

    @Test
    @DisplayName("Voucher findAll() 테스트 - 비어있지 않으면, 저장된 모든 바우처 정보가 조회된다.")
    void findAllNotEmptyListTest() {
        //given
        int saveCount = 5;

        IntStream.range(0, saveCount).forEach(index -> {
            Voucher voucher;

            if (index % 2 == 0) {
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), index);
            } else {
                voucher = new FixedAmountVoucher(UUID.randomUUID(), index);
            }

            voucherRepository.insert(voucher);
        });

        //when
        List<VoucherResponse> vouchers = voucherService.findAll();

        //then
        assertNotNull(vouchers);
        assertEquals(saveCount, vouchers.size());
    }

    @Test
    @DisplayName("Voucher findAll() 테스트 - 비어있으면 빈 리스트가 리턴된다.")
    void findAllReturnEmptyListTest() {
        //given & when
        List<VoucherResponse> vouchers = voucherService.findAll();

        //then
        assertNotNull(vouchers);
        assertTrue(vouchers.isEmpty());
    }

}