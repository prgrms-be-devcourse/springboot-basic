package com.programmers.commandline.domain.voucher.repository.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherNamedJdbcRepositoryTest {

    @Autowired
    private VoucherNamedJdbcRepository voucherNamedJdbcRepository;

    @BeforeEach
    void setup() {
        voucherNamedJdbcRepository.deleteAll();
    }

    @Test
    @DisplayName("voucher 발급하고 검증하라")
    void insert() {
        //given
        long discount = 100L;
        LocalDateTime createdAt = LocalDateTime.now();
        Voucher voucher = VoucherType.FIXED_AMOUNT.createVoucher(UUID.randomUUID(), discount, createdAt);

        //when
        voucherNamedJdbcRepository.insert(voucher);
        Optional<Voucher> foundVoucher = voucherNamedJdbcRepository.findById(voucher.getId());

        //then
        assertThat(foundVoucher.isEmpty(), is(false));
    }

    @Test
    @DisplayName("voucher discount 를 업데이트 하고 검증하라")
    void update() {
        //given
        UUID id = UUID.randomUUID();
        long discount = 100L;
        LocalDateTime createdAt = LocalDateTime.now();

        long updateDiscount = 200L;
        Voucher voucher = VoucherType.FIXED_AMOUNT.createVoucher(id, discount, createdAt);

        //when
        voucherNamedJdbcRepository.insert(voucher);
        voucher.update(updateDiscount);
        Voucher updateVoucher = voucherNamedJdbcRepository.update(voucher);

        //then
        assertThat(updateVoucher.getDiscount(), is(updateDiscount));
    }

    @Test
    void count() {

        //given
        UUID id = UUID.randomUUID();
        long discount = 100L;
        LocalDateTime createdAt = LocalDateTime.now();

        Voucher voucher = VoucherType.FIXED_AMOUNT.createVoucher(id, discount, createdAt);

        //when
        voucherNamedJdbcRepository.insert(voucher);
        int count = voucherNamedJdbcRepository.count();

        //then
        assertThat(count, is(1));
    }

    @Test
    void findAll() {
        //given
        UUID id = UUID.randomUUID();
        long discount = 100L;
        LocalDateTime createdAt = LocalDateTime.now();

        Voucher voucher = VoucherType.FIXED_AMOUNT.createVoucher(id, discount, createdAt);

        //when
        voucherNamedJdbcRepository.insert(voucher);
        List<Voucher> vouchers = voucherNamedJdbcRepository.findAll();

        //then
        vouchers.forEach(foundVoucher -> assertThat(foundVoucher.getId(), is(voucher.getId())));
    }

    @Test
    void findById() {
        //given
        UUID id = UUID.randomUUID();
        long discount = 100L;
        LocalDateTime createdAt = LocalDateTime.now();

        Voucher voucher = VoucherType.FIXED_AMOUNT.createVoucher(id, discount, createdAt);

        //when
        voucherNamedJdbcRepository.insert(voucher);
        Optional<Voucher> foundVoucher = voucherNamedJdbcRepository.findById(id.toString());

        //then
        assertThat(foundVoucher.get().getId(), is(id.toString()));
    }

    @Test
    void deleteAll() {
        //given
        UUID id = UUID.randomUUID();
        long discount = 100L;
        LocalDateTime createdAt = LocalDateTime.now();

        Voucher voucher = VoucherType.FIXED_AMOUNT.createVoucher(id, discount, createdAt);

        //when
        voucherNamedJdbcRepository.insert(voucher);
        voucherNamedJdbcRepository.deleteAll();
        List<Voucher> vouchers = voucherNamedJdbcRepository.findAll();

        //then
        assertThat(vouchers.isEmpty(), is(true));

    }
}