package org.programmers.springbootbasic.voucher.repository;

import org.junit.jupiter.api.*;
import org.programmers.springbootbasic.config.DBConfig;
import org.programmers.springbootbasic.voucher.model.FixedAmountVoucher;
import org.programmers.springbootbasic.voucher.model.PercentDiscountVoucher;
import org.programmers.springbootbasic.voucher.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.programmers.springbootbasic.config.DBConfig.dbSetup;

@SpringJUnitConfig
@ContextConfiguration(classes = {DBConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @BeforeAll
    void setup() {
        dbSetup();
    }

    @AfterEach
    void cleanup() {
        jdbcVoucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처를 추가 할 수 있다.")
    void testInsert() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L ,LocalDateTime.now());

        //when
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        var retrievedVoucher = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());

        //then
        assertThat(retrievedVoucher).isPresent().get().isEqualTo(fixedAmountVoucher);
    }

    @Test
    @DisplayName("전체 바우처를 조회 할 수 있다.")
    void testFindAll() {
        //given
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L, LocalDateTime.now());
        List<Voucher> voucherList = List.of(fixedAmountVoucher);

        //when
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        var vouchers = jdbcVoucherRepository.findAll();

        //then
        assertThat(vouchers).usingRecursiveFieldByFieldElementComparator().hasSameElementsAs(voucherList);
    }

    @Test
    @DisplayName("생성일 순으로 바우처를 조회 할 수 있다.")
    void testFindByCreatedAt() {
        //given
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L, LocalDateTime.now());
        var fixedAmountVoucher2 = new FixedAmountVoucher(UUID.randomUUID(), 500L, LocalDateTime.now());

        //when
        jdbcVoucherRepository.insert(fixedAmountVoucher2);
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        var vouchers = jdbcVoucherRepository.findByCreatedAt();

        //then
        assertThat(vouchers.get(0).getCreatedAt()).isBefore(vouchers.get(1).getCreatedAt());
    }

    @Test
    @DisplayName("아이디로 바우처를 조회 할 수 있다.")
    void testFindByName() {
        //given
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L, LocalDateTime.now());

        //when
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        var vouchers = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());

        //then
        assertThat(vouchers).isPresent();
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 바우처를 조회 할 수 없다.")
    void testFindByNonexistentName() {
        //given
        var unknown = jdbcVoucherRepository.findById(UUID.randomUUID());

        //then
        assertThat(unknown).isNotPresent();
    }

    @Test
    @DisplayName("바우처를 수정 할 수 있다.")
    void testUpdate() {
        //given
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L, LocalDateTime.now());

        //when
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        fixedAmountVoucher.changeValue(300L);
        jdbcVoucherRepository.update(fixedAmountVoucher);

        //then
        var retrievedVoucher = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());
        assertThat(retrievedVoucher).isPresent().get().isEqualTo(fixedAmountVoucher);
    }

    @Test
    @DisplayName("아이디로 바우처를 삭제 할 수 있다.")
    void testDeleteById() {
        //given
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L, LocalDateTime.now());

        //when
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        jdbcVoucherRepository.deleteById(fixedAmountVoucher.getVoucherId());
        var vouchers = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());

        //then
        assertThat(vouchers).isNotPresent();
    }

    @Test
    @DisplayName("모든 바우처를 삭제 할 수 있다.")
    void testDeleteAll() {
        //given
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 5000, LocalDateTime.now());
        var fixedAmountVoucher1 = new FixedAmountVoucher(UUID.randomUUID(), 3000, LocalDateTime.now());
        var percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20, LocalDateTime.now());

        //when
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        jdbcVoucherRepository.insert(fixedAmountVoucher1);
        jdbcVoucherRepository.insert(percentDiscountVoucher);
        jdbcVoucherRepository.deleteAll();
        var all = jdbcVoucherRepository.findAll();

        //then
        assertThat(all).isEmpty();
    }

    @Test
    @DisplayName("특정 바우처 아이디의 수를 조회 할 수 있다.")
    void testCountByVoucherId() {
        //given
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 5000, LocalDateTime.now());
        var expectResult = 1;

        //when
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        var countVoucher = jdbcVoucherRepository.getCountByVoucherId(fixedAmountVoucher.getVoucherId());
        var noVoucher = jdbcVoucherRepository.getCountByVoucherId(UUID.randomUUID());

        //then
        assertThat(countVoucher).isEqualTo(expectResult);
        assertThat(noVoucher).isZero();
    }
}
