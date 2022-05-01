package org.prgms.management.voucher.repository;

import org.junit.jupiter.api.*;
import org.prgms.management.model.voucher.Voucher;
import org.prgms.management.model.voucher.VoucherCreator;
import org.prgms.management.repository.voucher.VoucherJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ActiveProfiles(value = "test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VoucherJdbcRepositoryTest {
    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    List<Voucher> newVouchers = new ArrayList<>();

    @BeforeAll
    void setup() {
        voucherJdbcRepository.deleteAll();
        newVouchers.add(VoucherCreator.createVoucher(UUID.randomUUID(), 1000, "voucher-test1", "fixed", LocalDateTime.now()));
        newVouchers.add(VoucherCreator.createVoucher(UUID.randomUUID(), 500, "voucher-test2", "fixed", LocalDateTime.now()));
        newVouchers.add(VoucherCreator.createVoucher(UUID.randomUUID(), 99, "voucher-test3", "percent", LocalDateTime.now()));
        newVouchers.add(VoucherCreator.createVoucher(UUID.randomUUID(), 50, "voucher-test4", "percent", LocalDateTime.now()));
    }

    @Test
    @Order(1)
    @DisplayName("바우처를 추가 할 수 있다.")
    void insert() {
        newVouchers.forEach(v -> {
            var voucher = voucherJdbcRepository.insert(v);
            assertThat(voucher == null, is(false));
            assertThat(voucher.getVoucherId(), is(v.getVoucherId()));
        });
    }

    @Test
    @Order(2)
    @DisplayName("중복된 바우처를 추가 할 수 없다.")
    void insertDuplicateVoucher() {
        newVouchers.forEach(v -> {
            var voucher = voucherJdbcRepository.insert(v);
            assertThat(voucher == null, is(true));
        });
    }

    @Test
    @Order(3)
    @DisplayName("전체 바우처를 조회 할 수 있다.")
    void findAll() {
        var customers = voucherJdbcRepository.findAll();
        assertThat(customers.size(), is(newVouchers.size()));
    }

    @Test
    @Order(4)
    @DisplayName("아이디로 바우처를 조회 할 수 있다.")
    void findById() {
        newVouchers.forEach(v -> {
            System.out.println("voucher id -> " + v.getVoucherId());
            var voucher = voucherJdbcRepository.findById(
                    v.getVoucherId());
            assertThat(voucher == null, is(false));
        });
    }

    @Test
    @Order(5)
    @DisplayName("시간으로 바우처를 조회 할 수 있다.")
    void findByCreatedAt() {
        newVouchers.forEach(v -> {
            var customer = voucherJdbcRepository.findByCreatedAt(
                    v.getCreatedAt());
            assertThat(customer == null, is(false));
        });
    }

    @Test
    @Order(6)
    @DisplayName("타입으로 바우처를 조회 할 수 있다.")
    void findByType() {
        newVouchers.forEach(v -> {
            var customer = voucherJdbcRepository.findByType(
                    v.getType());
            assertThat(customer == null, is(false));
        });
    }

    @Test
    @Order(7)
    @DisplayName("바우처를 수정 할 수 있다.")
    void update() {
        var name = "updated-voucher";
        newVouchers.get(0).changeName(name);
        var voucher = voucherJdbcRepository.update(
                newVouchers.get(0));

        assertThat(voucher == null, is(false));
        assertThat(voucher.getName(), is(name));
    }

    @Test
    @Order(8)
    @DisplayName("바우처를 삭제 할 수 있다.")
    void delete() {
        var voucher = voucherJdbcRepository.delete(
                newVouchers.get(0));
        assertThat(voucher == null, is(false));
    }

    @Test
    @Order(9)
    @DisplayName("모든 바우처를 삭제 할 수 있다.")
    void deleteAll() {
        voucherJdbcRepository.deleteAll();
        var vouchers = voucherJdbcRepository.findAll();
        assertThat(vouchers == null, is(true));
    }

    @Test
    @Order(10)
    @DisplayName("삭제한 바우처는 검색 할 수 없다.")
    void findDeleted() {
        newVouchers.forEach(v -> {
            var voucher = voucherJdbcRepository.findById(v.getVoucherId());
            assertThat(voucher == null, is(true));
            voucher = voucherJdbcRepository.findByName(v.getName());
            assertThat(voucher == null, is(true));
        });
    }
}