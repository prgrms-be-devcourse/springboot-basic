package com.prgrms.devkdtorder.voucher.repository;

import com.prgrms.devkdtorder.voucher.domain.FixedAmountVoucher;
import com.prgrms.devkdtorder.voucher.domain.Voucher;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class VoucherRepositoryTest {

    private VoucherRepository voucherRepository = new MemVoucherRepository();

    @AfterEach
    void tearDown() {
        voucherRepository.deleteAll();
    }


    @Nested
    @DisplayName("공통 테스트")
    class CommonTest {

        @Test
        @DisplayName("Voucher를 저장할 수 있다")
        void testSave () {

            //given
            FixedAmountVoucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);

            //when
            Voucher savedVoucher = voucherRepository.insert(newVoucher);

            //then
            assertThat(savedVoucher, is(samePropertyValuesAs(newVoucher)));
        }

        @Test
        @DisplayName("저장된 Voucher를 ID로 단건 조회 할 수 있다")
        void testFindById () {

            //given
            FixedAmountVoucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
            voucherRepository.insert(newVoucher);

            //when
            Optional<Voucher> retrievedVoucher = voucherRepository.findById(newVoucher.getVoucherId());

            //then
            assertThat(retrievedVoucher.isPresent(), is(true));
            assertThat(retrievedVoucher.get(), is(samePropertyValuesAs(newVoucher)));
        }

        @Test
        @DisplayName("저장된 Voucher를 전부 조회 할 수 있다")
        void testFindAll () {

            //given
            FixedAmountVoucher newVoucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000);
            FixedAmountVoucher newVoucher2 = new FixedAmountVoucher(UUID.randomUUID(), 2000);
            voucherRepository.insert(newVoucher1);
            voucherRepository.insert(newVoucher2);

            //when
            List<Voucher> allVouchers = voucherRepository.findAll();

            //then
            assertThat(allVouchers, hasSize(2));
            assertThat(allVouchers, containsInAnyOrder(samePropertyValuesAs(newVoucher1), samePropertyValuesAs(newVoucher2)));
        }

        @Test
        @DisplayName("저장된 Voucher를 전부 제거할 수 있다.")
        void testDeleteAll () {

            //given
            FixedAmountVoucher newVoucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000);
            FixedAmountVoucher newVoucher2 = new FixedAmountVoucher(UUID.randomUUID(), 2000);
            voucherRepository.insert(newVoucher1);
            voucherRepository.insert(newVoucher2);
            List<Voucher> beforeDeleteVouchers = voucherRepository.findAll();
            assertThat(beforeDeleteVouchers, hasSize(2));

            //when
            voucherRepository.deleteAll();

            //then
            List<Voucher> afterDeleteVouchers = voucherRepository.findAll();
            assertThat(afterDeleteVouchers, hasSize(0));
        }
    }

    @Nested
    @DisplayName("MemVoucherRepository 전용 테스트")
    class MemVoucherRepositoryTest {

        @Test
        @DisplayName("MemVoucherRepository 이빈다")
        void testgg () {

        }
    }
}

