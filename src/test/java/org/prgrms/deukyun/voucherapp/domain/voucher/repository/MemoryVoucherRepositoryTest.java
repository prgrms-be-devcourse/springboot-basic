package org.prgrms.deukyun.voucherapp.domain.voucher.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository memoryRepository;
    Voucher voucher;

    @BeforeEach
    void setup() {
        memoryRepository = new MemoryVoucherRepository();
        voucher = dummyVoucher();
    }

    @Nested
    class insertTest {

        @Test
        void givenVoucher_whenCallInsert_thenIdIsSetAndReturnsInsertedVoucher() {
            //when
            Voucher insertedVoucher = memoryRepository.insert(voucher);

            //assert
            assertVoucher(insertedVoucher);
            assertFADV(insertedVoucher, voucher);
        }
    }

    @Nested
    class findAllTest {

        @Test
        void givenTwoVoucherInsertion_whenCallFindAll_thenGivesTwoVouchers() {
            //setup
            Voucher voucher1 = dummyVoucher();
            Voucher voucher2 = dummyVoucher();
            memoryRepository.insert(voucher1);
            memoryRepository.insert(voucher2);

            //when
            List<Voucher> vouchers = memoryRepository.findAll();

            //assert
            assertThat(vouchers).extracting("id")
                    .containsExactlyInAnyOrder(voucher1.getId(), voucher2.getId());
        }
    }

    @Nested
    class findByIdTest {

        UUID id;

        @BeforeEach
        void setup() {
            memoryRepository.insert(voucher);
        }

        @Test
        void givenIdOfInsertedVoucher_whenCallFindById_thenReturnFoundVoucherInstance() {
            //setup
            id = voucher.getId();

            //when
            Optional<Voucher> foundVoucher = memoryRepository.findById(id);

            //assert
            assertThat(foundVoucher).isPresent();
            assertFADV(foundVoucher.get(), voucher);
        }

        @Test
        void givenInvalidId_whenCallFindById_thenReturnOptionalEmpty() {
            //setup
            id = UUID.randomUUID();

            //when
            Optional<Voucher> foundVoucher = memoryRepository.findById(id);

            //assert
            assertThat(foundVoucher).isNotPresent();
        }
    }

    @Nested
    class clearTest{

        @Test
        void givenTwoInsertion_whenCallClear_thenFindAllReturnsEmptyList(){
            //setup
            memoryRepository.insert(dummyVoucher());
            memoryRepository.insert(dummyVoucher());

            //action
            memoryRepository.clear();

            //assert
            assertThat(memoryRepository.findAll()).isEmpty();
        }
    }

    private Voucher dummyVoucher() {
        return new FixedAmountDiscountVoucher(2000L);
    }

    private void assertVoucher(Voucher actualVoucher) {
        assertThat(actualVoucher).isNotNull();
        assertThat(actualVoucher.getId()).isNotNull();
    }

    private void assertFADV(Voucher insertedVoucher, Voucher expectedVoucher) {
        FixedAmountDiscountVoucher actualFADV = (FixedAmountDiscountVoucher) insertedVoucher;
        FixedAmountDiscountVoucher expectedFADV = (FixedAmountDiscountVoucher) expectedVoucher;
        assertThat(actualFADV.getAmount()).isEqualTo(expectedFADV.getAmount());
    }
}