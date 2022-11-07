package org.prgrms.springorder.repository;

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
import org.prgrms.springorder.domain.FixedAmountVoucher;
import org.prgrms.springorder.domain.PercentDiscountVoucher;
import org.prgrms.springorder.domain.Voucher;
import org.prgrms.springorder.exception.DuplicateIdException;

@TestInstance(Lifecycle.PER_CLASS)
class MemoryVoucherRepositoryTest {

    private MemoryVoucherRepository memoryVoucherRepository;

    @BeforeAll
    public void init() {
        memoryVoucherRepository = new MemoryVoucherRepository();
    }

    @BeforeEach
    void beforeEach() {
        memoryVoucherRepository.deleteAll();
    }

    @DisplayName("저장 테스트 - 저장되어있는 객체 중 Id 값이 중복이면 예외를 던진다.")
    @Test
    void insertFixedAmountVoucherFailTestThrowsDuplicateException() {
        //given
        UUID randomUUID = UUID.randomUUID();
        memoryVoucherRepository.insert(new FixedAmountVoucher(randomUUID, 100L));
        Voucher newVoucher = new FixedAmountVoucher(randomUUID, 50L);

        //when & then
        assertThrows(DuplicateIdException.class, () -> memoryVoucherRepository.insert(newVoucher));
    }

    @DisplayName("저장 테스트 - 저장되어있는 객체 중 Id 값이 중복이면 예외를 던진다.")
    @Test
    void insertPercentDiscountVoucherFailTestThrowsDuplicateException() {
        //given
        UUID randomUUID = UUID.randomUUID();
        memoryVoucherRepository.insert(new PercentDiscountVoucher(randomUUID, 100L));
        Voucher newVoucher = new FixedAmountVoucher(randomUUID, 50L);

        //when & then
        assertThrows(DuplicateIdException.class, () -> memoryVoucherRepository.insert(newVoucher));
    }

    @DisplayName("저장 테스트 - 객체가 성공적으로 저장된다.")
    @Test
    void insertPercentDiscountVoucherSuccessTest() {
        //given
        UUID randomUUID = UUID.randomUUID();
        Voucher voucher = new PercentDiscountVoucher(randomUUID, 50L);

        //when
        Voucher savedVoucher = assertDoesNotThrow(() -> memoryVoucherRepository.insert(voucher));

        //then
        Optional<Voucher> voucherOptional = memoryVoucherRepository.findById(randomUUID);
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
        Voucher savedVoucher = assertDoesNotThrow(() -> memoryVoucherRepository.insert(voucher));

        //then
        Optional<Voucher> voucherOptional = memoryVoucherRepository.findById(randomUUID);
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
        memoryVoucherRepository.insert(voucher);

        //when
        Optional<Voucher> optionalVoucher = memoryVoucherRepository.findById(randomUUID);

        //then
        assertTrue(optionalVoucher.isPresent());
        Voucher findVoucher = optionalVoucher.get();
        assertEquals(voucher, findVoucher);
        assertEquals(voucher.getVoucherId(), randomUUID);
    }

    @DisplayName("조회 테스트 - id로 객체를 정상적으로 찾는다.")
    @Test
    void findByIdPercentDiscountVoucher() {
        //given
        UUID randomUUID = UUID.randomUUID();
        long amount = 0;
        Voucher voucher = new PercentDiscountVoucher(randomUUID, amount);
        memoryVoucherRepository.insert(voucher);
        //when
        Optional<Voucher> optionalVoucher = memoryVoucherRepository.findById(randomUUID);
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

            memoryVoucherRepository.insert(voucher);
        });

        //when
        List<Voucher> vouchers = memoryVoucherRepository.findAll();

        //then
        assertNotNull(vouchers);
        assertFalse(vouchers.isEmpty());
        assertEquals(saveCount, vouchers.size());
    }

    @DisplayName("findAll 테스트 - 저장된 값이 없으면 빈 리스트를 반환한다.")
    @Test
    void findAllEmptyList() {
        //given & when
        List<Voucher> vouchers = memoryVoucherRepository.findAll();
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

            memoryVoucherRepository.insert(voucher);
        });

        //when
        memoryVoucherRepository.deleteAll();
        List<Voucher> vouchers = memoryVoucherRepository.findAll();

        //then
        assertNotNull(vouchers);
        assertTrue(vouchers.isEmpty());
    }

}