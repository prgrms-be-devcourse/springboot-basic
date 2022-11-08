package com.programmers.voucher.repository;

import com.programmers.voucher.voucher.Voucher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.UUID;

import static com.programmers.voucher.voucher.VoucherList.FixedAmount;
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

        Voucher voucher1 = FixedAmount.createVoucher( 3000);
        repository.registerVoucher(voucher1);

        assertThat(store.size()).isEqualTo(1);

        Voucher voucher2 = FixedAmount.createVoucher(20000);
        repository.registerVoucher(voucher2);

        assertThat(store.size()).isEqualTo(2);
    }

    @Test
    void 맵에서Id로바우처조회() {
        Voucher voucher = FixedAmount.createVoucher( 100000L);
        repository.registerVoucher(voucher);

        Voucher findOne = store.get(voucher.getVoucherId());

        assertSame(findOne, voucher);
        assertThat(findOne.getValue()).isEqualTo(voucher.getValue());
    }

    @Test
    void 맵의모든바우처조회() {
        assertThat(store.size()).isEqualTo(0);

        Voucher voucher1 = FixedAmount.createVoucher(3000);
        Voucher voucher2 = FixedAmount.createVoucher( 3000);

        repository.registerVoucher(voucher1);
        assertThat(store.size()).isEqualTo(1);

        repository.registerVoucher(voucher2);
        assertThat(store.size()).isEqualTo(2);
    }

}