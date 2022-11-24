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
    @DisplayName("voucher 발급하고 voucher 를 조회하여 정상적으로 insert 되었는지 검증하자")
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
    @DisplayName("voucher 생성하고 update를 통해서 discount를 update 하자 그리고 discount가 변경되었는지 검증하자")
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
    @DisplayName("바우처를 생성하고 생성된 바우처의 숫자를 검증하자")
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
    @DisplayName("바우처를 생성하고 모든 바우처를 조회하자 그리고 조회된 바우처와 생성할때 바우처를 비교해서 검증하자")
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
    @DisplayName("바우처를 생성하고 생성할때 사용한 ID를 활용하여 findById를 검증하자")
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
    @DisplayName("바우처를 생성하고 deleteAll 매서드를 이용해서 모두 삭제하자 그리고 findAll해서 조회된 voucher의 크기를 검증하자")
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