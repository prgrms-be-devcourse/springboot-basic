package org.promgrammers.springbootbasic.domain.voucher.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.promgrammers.springbootbasic.domain.voucher.model.FixedAmountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.PercentDiscountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.exception.DuplicateIDException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(Lifecycle.PER_CLASS)
class MemoryVoucherRepositoryTest {

    private MemoryVoucherRepository memoryVoucherRepository;

    @BeforeAll
    void beforeAll() {
        memoryVoucherRepository = new MemoryVoucherRepository();
    }

    @BeforeEach
    void beforeEach() {
        memoryVoucherRepository.deleteAll();
    }

    @Test
    @DisplayName("저장 성공 - fixedVoucher")
    void fixedVoucherInsertSuccessTest() throws Exception {

        //given
        UUID uuid = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(uuid, 10L);

        //when
        Voucher savedVoucher = assertDoesNotThrow(() -> memoryVoucherRepository.insert(voucher));
        Voucher findVoucher = memoryVoucherRepository.findById(uuid).get();

        //then
        assertTrue(memoryVoucherRepository.findById(uuid).isPresent());
        assertThat(findVoucher).isEqualTo(savedVoucher);
        assertEquals(savedVoucher, findVoucher);
    }

    @Test
    @DisplayName("저장 성공 - percentVoucher")
    void percentVoucherInsertSuccessTest() throws Exception {

        //given
        UUID uuid = UUID.randomUUID();
        Voucher voucher = new PercentDiscountVoucher(uuid, 10L);

        //when
        Voucher savedVoucher = assertDoesNotThrow(() -> memoryVoucherRepository.insert(voucher));
        Voucher findVoucher = memoryVoucherRepository.findById(uuid).get();

        //then
        assertTrue(memoryVoucherRepository.findById(uuid).isPresent());
        assertThat(findVoucher).isEqualTo(savedVoucher);
        assertEquals(savedVoucher, findVoucher);
    }

    @Test
    @DisplayName("저장 실패 - 중복된 Voucher Id(fixed)")
    void duplicateFixedVoucherIdTest() throws Exception {

        //given
        UUID uuid = UUID.randomUUID();
        Voucher voucher1 = new FixedAmountVoucher(uuid, 10L);
        Voucher voucher2 = new FixedAmountVoucher(uuid, 100L);
        memoryVoucherRepository.insert(voucher1);

        //when -> then
        assertThrows(DuplicateIDException.class, () -> memoryVoucherRepository.insert(voucher2));
    }

    @Test
    @DisplayName("저장 실패 - 중복된 Voucher Id(percent)")
    void duplicatePercentVoucherIdTest() throws Exception {

        //given
        UUID uuid = UUID.randomUUID();
        Voucher voucher1 = new PercentDiscountVoucher(uuid, 10L);
        Voucher voucher2 = new PercentDiscountVoucher(uuid, 100L);
        memoryVoucherRepository.insert(voucher1);

        //when -> then
        assertThrows(DuplicateIDException.class, () -> memoryVoucherRepository.insert(voucher2));
    }

    @Test
    @DisplayName("조회 테스트 - 성공(fixed)")
    void fixedVoucherFindByIdTest() throws Exception {

        //given
        UUID uuid = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(uuid, 10L);

        //when
        Voucher savedVoucher = assertDoesNotThrow(() -> memoryVoucherRepository.insert(voucher));
        Optional<Voucher> repositoryById = memoryVoucherRepository.findById(uuid);
        Voucher foundVoucherID = repositoryById.get();

        //then
        Assertions.assertTrue(repositoryById.isPresent());
        assertThat(foundVoucherID.getVoucherId()).isEqualTo(uuid);
        assertThat(foundVoucherID).isEqualTo(savedVoucher);
    }

    @Test
    @DisplayName("조회 테스트 - 성공(percent)")
    void percentVoucherFindByIdTest() throws Exception {

        //given
        UUID uuid = UUID.randomUUID();
        Voucher voucher = new PercentDiscountVoucher(uuid, 10L);

        //when
        Voucher savedVoucher = assertDoesNotThrow(() -> memoryVoucherRepository.insert(voucher));
        Optional<Voucher> repositoryById = memoryVoucherRepository.findById(uuid);
        Voucher foundVoucherID = repositoryById.get();

        //then
        Assertions.assertTrue(repositoryById.isPresent());
        assertThat(foundVoucherID.getVoucherId()).isEqualTo(uuid);
        assertThat(foundVoucherID).isEqualTo(savedVoucher);
    }

    @Test
    @DisplayName("전체 조회 성공 - 값이 있을시")
    void findAllSuccessTest() throws Exception {

        //given
        int saveCount = 5;
        for (int i = 0; i < saveCount; i++) {
            Voucher voucher;
            if (i % 2 == 0) {
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), i);
                memoryVoucherRepository.insert(voucher);
            } else {
                voucher = new FixedAmountVoucher(UUID.randomUUID(), i);
                memoryVoucherRepository.insert(voucher);
            }
        }

        //when
        List<Voucher> findAll = memoryVoucherRepository.findAll();

        //then
        assertNotNull(findAll);
        assertFalse(findAll.isEmpty());
        assertThat(findAll.size()).isEqualTo(saveCount);
    }

    @Test
    @DisplayName("전체 조회 실패 - 저장된 값이 없을 때")
    void findAllFailTest() throws Exception {

        //given -> when
        List<Voucher> emptyList = memoryVoucherRepository.findAll();

        //then
        assertTrue(emptyList.isEmpty());
        assertNotNull(emptyList);
        assertThat(emptyList.size()).isEqualTo(0);
    }
}