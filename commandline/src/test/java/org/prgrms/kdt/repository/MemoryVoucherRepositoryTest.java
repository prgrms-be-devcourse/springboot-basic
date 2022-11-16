package org.prgrms.kdt.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.List;
import java.util.UUID;

class MemoryVoucherRepositoryTest {

    VoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();
    static List<Voucher> vouchers;

    @BeforeAll
    static void FillList() {
        vouchers = List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 20),
                new PercentDiscountVoucher(UUID.randomUUID(), 50)
        );
    }

    @Test
    @DisplayName("메모리 저장소에 저장할 수 있다.")
    void insertTest() {
        assertThat(memoryVoucherRepository.insert(vouchers.get(0)), samePropertyValuesAs(vouchers.get(0)));
        assertThat(memoryVoucherRepository.insert(vouchers.get(1)), samePropertyValuesAs(vouchers.get(1)));
    }

    @Test
    @DisplayName("메모리 저장소에서 데이터를 가져올 수 있다.")
    void FindAllTest() {
        memoryVoucherRepository.insert(vouchers.get(0));
        memoryVoucherRepository.insert(vouchers.get(1));

        List<Voucher> results = memoryVoucherRepository.findAll();
        assertThat(results.size(), is(2));
        assertThat(results, containsInAnyOrder(samePropertyValuesAs(vouchers.get(0)), samePropertyValuesAs(vouchers.get(1))));
    }
}