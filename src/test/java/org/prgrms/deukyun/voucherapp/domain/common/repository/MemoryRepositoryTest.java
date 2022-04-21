package org.prgrms.deukyun.voucherapp.domain.common.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.Voucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.repository.MemoryVoucherRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

class MemoryRepositoryTest {

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

        @Test
        void givenInsertedVoucher_whenCallInsertAgainWithSameVoucherInstance_thenThrowsIllegalStateException() {
            //setup
            memoryRepository.insert(voucher);

            //assert throws
            assertThatIllegalStateException()
                    .isThrownBy(() -> memoryRepository.insert(voucher));
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