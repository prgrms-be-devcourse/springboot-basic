package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.config.TestDataSourceConfig;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherFactory;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@Import(TestDataSourceConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class JdbcVoucherRepositoryTest {

    JdbcVoucherRepository jdbcVoucherRepository;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        this.jdbcVoucherRepository = new JdbcVoucherRepository(jdbcTemplate);
    }


    @Test
    @DisplayName("[성공] 바우처를 조회할 수 있다.")
    void findByVoucherIdTest() {
        //given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        jdbcVoucherRepository.insert(voucher);
        //when
        Optional<Voucher> voucherOptional = jdbcVoucherRepository.findById(voucher.getVoucherId());
        //then
        assertThat(voucherOptional.isPresent()).isTrue();
        assertThat(voucherOptional.get()).isEqualTo(voucher);
    }

    @Test
    @DisplayName("[실패] 없는 바우처는 조회할 수 없다.")
    void findByNotExistVoucherIdTest() {
        //given
        UUID uuid = UUID.randomUUID();
        //when & then
        assertThat(jdbcVoucherRepository.findById(uuid)).isEmpty();
    }

    @Test
    @DisplayName("[성공] 바우처를 저장할 수 있다.")
    void insertVoucherTest() {
        //given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        //when
        boolean insertResult = jdbcVoucherRepository.insert(voucher);
        //then
        assertThat(insertResult).isTrue();
    }

    @Test
    @DisplayName("[성공] id만 다르면 type과 discount가 같은 바우처도 저장할 수 있다.")
    void insertSameTypeAndNameVoucherTest() {
        //given
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        //when
        boolean insertResult1 = jdbcVoucherRepository.insert(voucher1);
        boolean insertResult2 = jdbcVoucherRepository.insert(voucher2);
        //then
        assertThat(insertResult1).isTrue();
        assertThat(insertResult2).isTrue();
    }

    @Test
    @DisplayName("[실패] 같은 바우처id는 저장할 수 없다.")
    void insertSameVoucherTest() {
        //given
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        jdbcVoucherRepository.insert(voucher1);
        //when & then
        assertThat(jdbcVoucherRepository.insert(voucher1)).isFalse();
    }


    @Test
    @DisplayName("[성공] 전체 바우처를 조회할 수 있다.")
    void findAllVoucherTest() {
        //given
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT, UUID.randomUUID(), 10);
        jdbcVoucherRepository.insert(voucher1);
        jdbcVoucherRepository.insert(voucher2);
        //when
        List<Voucher> voucherList = jdbcVoucherRepository.findAll();
        //then
        assertThat(voucherList).contains(voucher1, voucher2); // 순서를 보장할 필요성?
    }

    @Test
    @DisplayName("[성공] 바우처를 업데이트를 할 수 있다.")
    void updateVoucherTest() {
        //given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        jdbcVoucherRepository.insert(voucher);
        Voucher newVoucher = VoucherFactory.createVoucher(VoucherType.PERCENT, voucher.getVoucherId(), 10);
        //when
        boolean updateResult = jdbcVoucherRepository.update(newVoucher);
        Optional<Voucher> foundVoucher = jdbcVoucherRepository.findById(newVoucher.getVoucherId());
        //then
        assertThat(updateResult).isTrue();
        assertThat(foundVoucher.isPresent()).isTrue();
        assertThat(foundVoucher.get()).isEqualTo(newVoucher);
    }

    @Test
    @DisplayName("[실패] 앖는 바우처는 업데이트를 할 수 없다.")
    void updateNotExistVoucherTest() {
        //given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        //when & then
        assertThat(jdbcVoucherRepository.update(voucher)).isFalse();
    }

    @Test
    @DisplayName("[성공] 바우처를 삭제할 수 있다")
    void deleteVoucherTest() {
        //given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        jdbcVoucherRepository.insert(voucher);
        //when & then
        assertThat(jdbcVoucherRepository.deleteById(voucher.getVoucherId())).isTrue();
        assertThat(jdbcVoucherRepository.findById(voucher.getVoucherId())).isEmpty();
    }

    @Test
    @DisplayName("[실패] 없는 바우처는 삭제할 수 없다")
    void deleteNotExistVoucherTest() {
        //given
        UUID uuid = UUID.randomUUID();
        //when & then
        assertThat(jdbcVoucherRepository.deleteById(uuid)).isFalse();
    }
}