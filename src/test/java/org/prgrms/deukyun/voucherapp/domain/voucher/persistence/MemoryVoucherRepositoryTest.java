package org.prgrms.deukyun.voucherapp.domain.voucher.persistence;

import org.junit.jupiter.api.BeforeEach;
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

    @Test
    void 성공_삽입() {
        //when
        Voucher insertedVoucher = memoryRepository.insert(voucher);

        //assert
        assertVoucher(insertedVoucher);
        assertFADV(insertedVoucher, voucher);
    }

    @Test
    void 성공_전체조회() {
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


    @Test
    void 성공_단건조회() {
        //setup
        UUID id = voucher.getId();
        memoryRepository.insert(voucher);
        //when
        Optional<Voucher> foundVoucher = memoryRepository.findById(id);

        //assert
        assertThat(foundVoucher).isPresent();
        assertFADV(foundVoucher.get(), voucher);
    }

    @Test
    void 성공_단건조회_아이디가_없을경우_OptionalEmpty_반환() {
        //setup
        UUID id = UUID.randomUUID();
        memoryRepository.insert(voucher);

        //when
        Optional<Voucher> foundVoucher = memoryRepository.findById(id);

        //assert
        assertThat(foundVoucher).isNotPresent();
    }


    @Test
    void 성공_전체_삭제() {
        //setup
        memoryRepository.insert(voucher());
        memoryRepository.insert(voucher());

        //action
        memoryRepository.deleteAll();

        //assert
        assertThat(memoryRepository.findAll()).isEmpty();
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