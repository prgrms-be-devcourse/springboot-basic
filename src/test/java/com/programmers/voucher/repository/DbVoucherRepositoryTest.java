package com.programmers.voucher.repository;

import com.programmers.voucher.domain.VoucherEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DbVoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    VoucherEntity newVoucherEntity;

    @BeforeAll
    void setup() {
        newVoucherEntity = new VoucherEntity(UUID.randomUUID(), "FixedAmount", 3000, LocalDateTime.now(), LocalDateTime.now().plusMonths(6));
        voucherRepository.insert(newVoucherEntity);
    }

    @Test
    @Order(1)
    void insert() {
        VoucherEntity voucherEntity1 = new VoucherEntity(UUID.randomUUID(), "FixedAmount", 5000, LocalDateTime.now(), LocalDateTime.now().plusMonths(6));
        voucherRepository.insert(voucherEntity1);
        VoucherEntity voucherEntity2 = new VoucherEntity(UUID.randomUUID(), "PercentDiscount", 30, LocalDateTime.now(), LocalDateTime.now().plusMonths(3));
        voucherRepository.insert(voucherEntity2);

        var retrievedVoucherEntity1 = voucherRepository.findByID(voucherEntity1.getVoucherId());
        var retrievedVoucherEntity2 = voucherRepository.findByID(voucherEntity2.getVoucherId());

        assertThat(retrievedVoucherEntity1.isEmpty(), is(false));
        assertThat(retrievedVoucherEntity1.get(), samePropertyValuesAs(voucherEntity1));

        assertThat(retrievedVoucherEntity2.isEmpty(), is(false));
        assertThat(retrievedVoucherEntity2.get(), samePropertyValuesAs(voucherEntity2));
    }

    @Test
    @Order(2)
    void findAll() {
        var vouchers = voucherRepository.findAll();
        assertThat(vouchers.size(), is(3));
    }

    @Test
    @Order(3)
    void findByType() {
        var typedVouchers = voucherRepository.findByType("FixedAmount");
        assertThat(typedVouchers.size(), is(2));
    }


    @Test
    @Order(4)
    void findByID() {
        var retrievedVoucher = voucherRepository.findByID(newVoucherEntity.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(newVoucherEntity));
    }

    @Test
    @Order(5)
    void update() {
        newVoucherEntity.changeDiscount(5000);
        var voucher = voucherRepository.update(newVoucherEntity);
        assertThat(voucher, samePropertyValuesAs(newVoucherEntity));
    }



    @Test
    @Order(6)
    void deleteAll() {
        voucherRepository.deleteAll();
        var vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(true));
    }
}