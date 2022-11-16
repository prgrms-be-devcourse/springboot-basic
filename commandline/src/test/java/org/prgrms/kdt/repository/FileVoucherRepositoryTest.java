package org.prgrms.kdt.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;

class FileVoucherRepositoryTest {

    VoucherRepository fileVoucherRepository = new FileVoucherRepository("./VoucherList.csv");
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
        assertThat(fileVoucherRepository.insert(vouchers.get(0)), samePropertyValuesAs(vouchers.get(0)));
        assertThat(fileVoucherRepository.insert(vouchers.get(1)), samePropertyValuesAs(vouchers.get(1)));
    }

    @Test
    @DisplayName("메모리 저장소에서 데이터를 가져올 수 있다.")
    void FindAllTest() {
        fileVoucherRepository.insert(vouchers.get(0));
        fileVoucherRepository.insert(vouchers.get(1));

        List<Voucher> results = fileVoucherRepository.findAll();
        assertThat(results.size(), is(2));
        assertThat(results, containsInAnyOrder(samePropertyValuesAs(vouchers.get(0)), samePropertyValuesAs(vouchers.get(1))));
    }
}