package org.prgrms.springorder.domain.voucher.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;

@TestInstance(Lifecycle.PER_CLASS)
class VoucherMemoryRepositoryTest {

    private VoucherMemoryRepository voucherMemoryRepository;

    @BeforeAll
    public void init() {
        voucherMemoryRepository = new VoucherMemoryRepository();
    }

    @BeforeEach
    void beforeEach() {
        voucherMemoryRepository.deleteAll();
    }

    @DisplayName("저장 테스트 - 객체가 성공적으로 저장된다.")
    @Test
    void insertPercentDiscountVoucherSuccessTest() {
        //given
        UUID randomUUID = UUID.randomUUID();
        Voucher voucher = new PercentDiscountVoucher(randomUUID, 50L);

        //when
        Voucher savedVoucher = assertDoesNotThrow(() -> voucherMemoryRepository.insert(voucher));

        //then
        Optional<Voucher> voucherOptional = voucherMemoryRepository.findById(randomUUID);
        assertTrue(voucherOptional.isPresent());

        Voucher findVoucher = voucherOptional.get();
        assertEquals(savedVoucher, findVoucher);
    }

    @DisplayName("저장 테스트 - 객체가 성공적으로 저장된다.")
    @Test
    void insertFixedAmountVoucherSuccessTest() {
        //given
        UUID randomUUID = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(randomUUID, 50L);

        //when
        Voucher savedVoucher = assertDoesNotThrow(() -> voucherMemoryRepository.insert(voucher));

        //then
        Optional<Voucher> voucherOptional = voucherMemoryRepository.findById(randomUUID);
        assertTrue(voucherOptional.isPresent());

        Voucher findVoucher = voucherOptional.get();
        assertEquals(savedVoucher, findVoucher);
    }

    @DisplayName("조회 테스트 - id로 객체를 정상적으로 찾는다.")
    @Test
    void findByIdFixedAmountVoucher() {
        //given
        UUID randomUUID = UUID.randomUUID();
        long amount = 0;
        Voucher voucher = new FixedAmountVoucher(randomUUID, amount);
        voucherMemoryRepository.insert(voucher);

        //when
        Optional<Voucher> optionalVoucher = voucherMemoryRepository.findById(randomUUID);

        //then
        assertTrue(optionalVoucher.isPresent());
        Voucher findVoucher = optionalVoucher.get();
        assertEquals(voucher, findVoucher);
        assertEquals(voucher.getVoucherId(), randomUUID);
    }

    @DisplayName("조회 테스트 - id로 객체를 찾지만 없으면 빈 옵셔널을 반환한다. ")
    @Test
    void findByIdReturnEmptyOptional() {
        //given
        UUID randomUUID = UUID.randomUUID();

        //when
        Optional<Voucher> optionalVoucher = voucherMemoryRepository.findById(randomUUID);

        //then
        assertTrue(optionalVoucher.isEmpty());
    }

    @DisplayName("조회 테스트 - id로 객체를 정상적으로 찾는다.")
    @Test
    void findByIdPercentDiscountVoucher() {
        //given
        UUID randomUUID = UUID.randomUUID();
        long amount = 0;
        Voucher voucher = new PercentDiscountVoucher(randomUUID, amount);
        voucherMemoryRepository.insert(voucher);
        //when
        Optional<Voucher> optionalVoucher = voucherMemoryRepository.findById(randomUUID);
        //then
        assertTrue(optionalVoucher.isPresent());
        Voucher findVoucher = optionalVoucher.get();
        assertEquals(voucher, findVoucher);
        assertEquals(voucher.getVoucherId(), randomUUID);
    }

    @DisplayName("findAll 테스트 - 저장된 값을 전부 반환한다.")
    @Test
    void findAll() {
        //given
        int saveCount = 5;

        IntStream.range(0, saveCount).forEach(index -> {
            Voucher voucher;

            if (index % 2 == 0) {
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), index);
            } else {
                voucher = new FixedAmountVoucher(UUID.randomUUID(), index);
            }

            voucherMemoryRepository.insert(voucher);
        });

        //when
        List<Voucher> vouchers = voucherMemoryRepository.findAll();

        //then
        assertNotNull(vouchers);
        assertFalse(vouchers.isEmpty());
        assertEquals(saveCount, vouchers.size());
    }

    @DisplayName("findAll 테스트 - 저장된 값이 없으면 빈 리스트를 반환한다.")
    @Test
    void findAllEmptyList() {
        //given & when
        List<Voucher> vouchers = voucherMemoryRepository.findAll();
        //then
        assertNotNull(vouchers);
        assertTrue(vouchers.isEmpty());
    }

    @DisplayName("deleteAll 테스트 - 저장된 값을 모두 삭제한다.")
    @Test
    public void deleteAll() {
        //given
        int saveCount = 5;

        IntStream.range(0, saveCount).forEach(index -> {
            Voucher voucher;

            if (index % 2 == 0) {
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), index);
            } else {
                voucher = new FixedAmountVoucher(UUID.randomUUID(), index);
            }

            voucherMemoryRepository.insert(voucher);
        });

        //when
        voucherMemoryRepository.deleteAll();
        List<Voucher> vouchers = voucherMemoryRepository.findAll();

        //then
        assertNotNull(vouchers);
        assertTrue(vouchers.isEmpty());
    }

    @DisplayName("update 테스트 - 저장된 Voucher의 Type과 amount가 바뀐다. ")
    @Test
    void updateVoucherTypeAndAmount() {
        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherMemoryRepository.insert(voucher);

        long updateAmount = 10L;
        VoucherType updateVoucherType = VoucherType.PERCENT;
        Voucher updateVoucher = new PercentDiscountVoucher(voucherId, updateAmount);

        //when
        Voucher updatedVoucher = voucherMemoryRepository.update(updateVoucher);

        Optional<Voucher> voucherOptional = voucherMemoryRepository.findById(voucherId);

        //then
        assertEquals(updatedVoucher, updatedVoucher);
        assertTrue(voucherOptional.isPresent());
        Voucher findUpdatedVoucher = voucherOptional.get();
        assertEquals(updatedVoucher, findUpdatedVoucher);

        assertEquals(voucherId, findUpdatedVoucher.getVoucherId());
        assertEquals(updateAmount, findUpdatedVoucher.getAmount());
        assertEquals(updateVoucherType, findUpdatedVoucher.getVoucherType());

    }

    @DisplayName("update 테스트 - 저장된 Voucher의 amount가 바뀐다. ")
    @Test
    void updateVoucherAmount() {
        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherMemoryRepository.insert(voucher);

        long updateAmount = 10L;
        Voucher updateVoucher = new FixedAmountVoucher(voucherId, updateAmount);

        //when
        Voucher updatedVoucher = voucherMemoryRepository.update(updateVoucher);

        Optional<Voucher> voucherOptional = voucherMemoryRepository.findById(voucherId);

        //then
        assertEquals(updatedVoucher, updatedVoucher);
        assertTrue(voucherOptional.isPresent());
        Voucher findUpdatedVoucher = voucherOptional.get();
        assertEquals(updatedVoucher, findUpdatedVoucher);

        assertEquals(voucherId, findUpdatedVoucher.getVoucherId());
        assertEquals(updateAmount, findUpdatedVoucher.getAmount());
    }

    @DisplayName("update 테스트 - 저장된 Voucher의 Type이 바뀐다. ")
    @Test
    void updateVoucherType() {
        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherMemoryRepository.insert(voucher);

        VoucherType updateVoucherType = VoucherType.PERCENT;
        Voucher updateVoucher = new PercentDiscountVoucher(voucherId, amount);

        //when
        Voucher updatedVoucher = voucherMemoryRepository.update(updateVoucher);

        Optional<Voucher> voucherOptional = voucherMemoryRepository.findById(voucherId);

        //then
        assertEquals(updatedVoucher, updatedVoucher);
        assertTrue(voucherOptional.isPresent());
        Voucher findUpdatedVoucher = voucherOptional.get();
        assertEquals(updatedVoucher, findUpdatedVoucher);

        assertEquals(voucherId, findUpdatedVoucher.getVoucherId());
        assertEquals(updateVoucherType, findUpdatedVoucher.getVoucherType());
    }

    @DisplayName("findByIdWithCustomer Join 테스트 - 메모리 레포지토리를 지원하지 않으므로 예외를 던진다.")
    @Test
    void findByIdWithCustomerThrowException() {
        //given
        UUID voucherId = UUID.randomUUID();
        //when & then
        assertThrows(RuntimeException.class,
            () -> voucherMemoryRepository.findByIdWithCustomer(voucherId));
    }

    @DisplayName("deleteById 테스트 - voucherId로 저장된 Voucher가 제거된다.")
    @Test
    void deleteByIdTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherMemoryRepository.insert(voucher);

        //when
        voucherMemoryRepository.deleteById(voucherId);

        //then
        Optional<Voucher> voucherOptional = voucherMemoryRepository.findById(voucherId);

        assertTrue(voucherOptional.isEmpty());
    }

}