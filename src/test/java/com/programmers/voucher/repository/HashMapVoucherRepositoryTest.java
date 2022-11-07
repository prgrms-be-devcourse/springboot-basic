package com.programmers.voucher.repository;

import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherFactory;
import com.programmers.voucher.voucher.VoucherList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
class HashMapVoucherRepositoryTest {

    @Autowired
    HashMapVoucherRepository repository;
    Map<UUID, Voucher> store;

    @BeforeEach
    void setting() {
        store = repository.map;
    }

    @AfterEach
    void clear() {
        store.clear();
    }

    @Test
    void 맵에바우처저장() {

        assertThat(store.size()).isEqualTo(0);

        Voucher voucher1 = VoucherFactory.createVoucher(VoucherList.FixedAmount, 3000);
        repository.registerVoucher(voucher1);

        assertThat(store.size()).isEqualTo(1);

        Voucher voucher2 = VoucherFactory.createVoucher(VoucherList.FixedAmount, 3000);
        repository.registerVoucher(voucher2);

        assertThat(store.size()).isEqualTo(2);
    }

    @Test
    void 맵에서Id로바우처조회() {
        Voucher voucher = VoucherFactory.createVoucher(VoucherList.FixedAmount, 3000);
        repository.registerVoucher(voucher);

        Voucher findOne = store.get(voucher.getVoucherId());

        assertSame(voucher, findOne);
        assertThat(voucher.getValue()).isEqualTo(3000L);
    }

    @Test
    void 맵의모든바우처조회() {
        assertThat(store.size()).isEqualTo(0);

        Voucher voucher1 = VoucherFactory.createVoucher(VoucherList.FixedAmount, 3000);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherList.FixedAmount, 3000);

        repository.registerVoucher(voucher1);
        assertThat(store.size()).isEqualTo(1);

        repository.registerVoucher(voucher2);
        assertThat(store.size()).isEqualTo(2);
    }

}