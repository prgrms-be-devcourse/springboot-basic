package org.prgrms.deukyun.voucherapp.domain.voucher.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.prgrms.deukyun.voucherapp.domain.testutil.Fixture.customer;
import static org.prgrms.deukyun.voucherapp.domain.testutil.Fixture.voucher;

class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository memoryRepository;
    Voucher voucher;

    @BeforeEach
    void setUp() {
        memoryRepository = new MemoryVoucherRepository();
        voucher = voucher();
    }

    @Test
    void 성공_삽입() {
        //when
        Voucher insertedVoucher = memoryRepository.insert(voucher);

        //then
        assertVoucher(insertedVoucher);
        assertFADV(insertedVoucher, voucher);
    }

    @Test
    void 성공_전체조회() {
        //given
        Voucher voucher1 = voucher();
        Voucher voucher2 = voucher();
        memoryRepository.insert(voucher1);
        memoryRepository.insert(voucher2);

        //when
        List<Voucher> vouchers = memoryRepository.findAll();

        //then
        assertThat(vouchers).extracting("id")
                .containsExactlyInAnyOrder(voucher1.getId(), voucher2.getId());
    }

    @Test
    void 성공_고객의_아이디로_전체_조회() {
        //given
        Customer customer = customer();
        UUID customerId = customer.getId();

        Voucher voucher1 = voucher();
        voucher1.setOwnerId(customerId);
        memoryRepository.insert(voucher1);

        Voucher voucher2 = voucher();
        voucher2.setOwnerId(customerId);
        memoryRepository.insert(voucher2);

        Voucher voucher3 = voucher();
        memoryRepository.insert(voucher3);

        //when
        List<Voucher> foundVouchers = memoryRepository.findByCustomerId(customerId);

        //then
        assertThat(foundVouchers).hasSize(2);
        assertThat(foundVouchers).extracting("id")
                .containsExactlyInAnyOrder(voucher1.getId(), voucher2.getId());
    }

    @Test
    void 실패_고객의_아이디로_전체_조회() {
        //given
        UUID customerId = null;

        //then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> memoryRepository.findByCustomerId(customerId));
    }


    @Test
    void 성공_단건조회() {
        //given
        UUID id = voucher.getId();
        memoryRepository.insert(voucher);
        //when
        Optional<Voucher> foundVoucher = memoryRepository.findById(id);

        //then
        assertThat(foundVoucher).isPresent();
        assertFADV(foundVoucher.get(), voucher);
    }

    @Test
    void 성공_단건조회_아이디가_없을경우_OptionalEmpty_반환() {
        //given
        UUID id = UUID.randomUUID();
        memoryRepository.insert(voucher);

        //when
        Optional<Voucher> foundVoucher = memoryRepository.findById(id);

        //then
        assertThat(foundVoucher).isNotPresent();
    }


    @Test
    void 성공_전체_삭제() {
        //given
        memoryRepository.insert(voucher());
        memoryRepository.insert(voucher());

        //when
        memoryRepository.deleteAll();

        //then
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