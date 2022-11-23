package com.programmers.assignment.voucher.engine.repository;

import com.programmers.assignment.voucher.engine.model.Voucher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class JdbcVoucherRepositoryTest {

    @Autowired JdbcVoucherRepository voucherRepository;

    @Test
    void findById() {

    }

    @Test
    void findAll() {
    }

    @Test
    void insert() {
        //given
        Voucher fixedVoucher = new Voucher(UUID.randomUUID(), "FIXED", 1000, 139L);

        //when
        voucherRepository.insert(fixedVoucher);

        //then
        assertThat(fixedVoucher, samePropertyValuesAs(voucherRepository.findByDiscountWay("FIXED").get(0)));
    }

    @Test
    void update() {
        //given
        Voucher fixedVoucher = voucherRepository.insert(new Voucher(UUID.randomUUID(), "FIXED", 1000, 139L));
        Voucher updateVoucher = new Voucher(fixedVoucher.getVoucherId(), "FIXED", 500, 139L);

        //when
        Voucher updatedVoucher = voucherRepository.update(updateVoucher);

        //then
        assertThat(updateVoucher, is(updatedVoucher));
    }

    @Test
    void findByDiscountWay() {
        //given
        Voucher voucher = voucherRepository.insert(new Voucher(UUID.randomUUID(), "FIXED", 1000, 139L));
        Voucher voucher2 = voucherRepository.insert(new Voucher(UUID.randomUUID(), "FIXED", 2000, 139L));

        //when
        List<Voucher> vouchers = voucherRepository.findByDiscountWay("FIXED");

        //then
        assertThat(vouchers.get(0), samePropertyValuesAs(voucher));
        assertThat(vouchers.get(1), samePropertyValuesAs(voucher2));
    }

    @Test
    void deleteAll() {
        //given
        Voucher voucher = voucherRepository.insert(new Voucher(UUID.randomUUID(), "FIXED", 1000, 139L));
        Voucher voucher2 = voucherRepository.insert(new Voucher(UUID.randomUUID(), "FIXED", 2000, 139L));

        //when
        voucherRepository.deleteAll();
        //then
        assertThat(0, is(voucherRepository.findAll().size()));
    }
}