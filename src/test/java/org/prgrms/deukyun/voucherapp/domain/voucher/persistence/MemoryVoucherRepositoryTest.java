package org.prgrms.deukyun.voucherapp.domain.voucher.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.prgrms.deukyun.voucherapp.domain.testutil.Fixture.voucher;

class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository memoryRepository;
    Voucher voucher;

    @BeforeEach
    void setup() {
        memoryRepository = new MemoryVoucherRepository();
        voucher = voucher();
    }

    @Nested
    @DisplayName("삽입")
    class insertTest {

        @Test
        void 성공() {
            //when
            Voucher insertedVoucher = memoryRepository.insert(voucher);

            //assert
            assertVoucher(insertedVoucher);
            assertFADV(insertedVoucher, voucher);
        }
    }

    @Nested
    @DisplayName("전체 조회")
    class findAllTest {

        @Test
        void 성공() {
            //setup
            Voucher voucher1 = voucher();
            Voucher voucher2 = voucher();
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
    @DisplayName("단건 조회")
    class findByIdTest {

        UUID id;

        @BeforeEach
        void setup() {
            memoryRepository.insert(voucher);
        }

        @Test
        void 성공() {
            //setup
            id = voucher.getId();

            //when
            Optional<Voucher> foundVoucher = memoryRepository.findById(id);

            //assert
            assertThat(foundVoucher).isPresent();
            assertFADV(foundVoucher.get(), voucher);
        }

        @Test
        void 성공_아이디가_없을경우_OptionalEmpty_반환() {
            //setup
            id = UUID.randomUUID();

            //when
            Optional<Voucher> foundVoucher = memoryRepository.findById(id);

            //assert
            assertThat(foundVoucher).isNotPresent();
        }
    }

    @Nested
    @DisplayName("전체 삭제")
    class deleteAllTest{

        @Test
        void 성공(){
            //setup
            memoryRepository.insert(voucher());
            memoryRepository.insert(voucher());

            //action
            memoryRepository.deleteAll();

            //assert
            assertThat(memoryRepository.findAll()).isEmpty();
        }
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