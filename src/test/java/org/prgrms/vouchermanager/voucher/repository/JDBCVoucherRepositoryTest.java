package org.prgrms.vouchermanager.voucher.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.vouchermanager.voucher.domain.FixedAmountVoucher;
import org.prgrms.vouchermanager.voucher.domain.PercentDiscountVoucher;
import org.prgrms.vouchermanager.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JDBCVoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("insert를 하면 voucher를 삽입한다.")
    void insert() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);

        //when
        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);

        Voucher foundFixedVoucher = voucherRepository.findById(fixedAmountVoucher.getVoucherId()).get();
        Voucher foundPercentVoucher = voucherRepository.findById(percentDiscountVoucher.getVoucherId()).get();

        //then
        assertThat(foundFixedVoucher).isEqualTo(fixedAmountVoucher);
        assertThat(foundPercentVoucher).isEqualTo(percentDiscountVoucher);
    }

    @Test
    @DisplayName("voucherId로 저장된 voucher를 찾아서 반환한다.")
    void findById() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        voucherRepository.insert(fixedAmountVoucher);

        //when
        Voucher foundVoucher = voucherRepository.findById(fixedAmountVoucher.getVoucherId()).get();

        //then
        assertThat(foundVoucher).isEqualTo(fixedAmountVoucher);
    }

    @Test
    @DisplayName("저장된 모든 Voucher를 List로 반환한다.")
    void getAll() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        List<Voucher> testVoucherList = List.of(fixedAmountVoucher, percentDiscountVoucher);

        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);

        //when
        List<Voucher> all = voucherRepository.getAll();

        //then
        assertThat(all).isNotEmpty().containsAll(testVoucherList);
    }

    @Test
    @DisplayName("이미 존재하는 Voucher를 insert 할 수 없다")
    void insert_이미_존재하는_Voucher를_insert할_수_없다() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);

        //when
        voucherRepository.insert(fixedAmountVoucher);

        //then
        assertThatThrownBy(() -> voucherRepository.insert(fixedAmountVoucher)).isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @DisplayName("findById의 voucher가 존재하지 않는 경우 Optional.empty()를 반환한다.")
    void findById_해당_voucher가_존재하지_않을_수_있다() {
        //given
        UUID notExsistVoucherId = UUID.randomUUID();

        //when
        Optional<Voucher> foundVoucher = voucherRepository.findById(notExsistVoucherId);

        //then
        assertThat(foundVoucher).isEqualTo(Optional.empty());
    }

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.vouchermanager.voucher"})
    static class Config {

        @Bean
        public VoucherRepository voucherRepository() {
            return new JDBCVoucherRepository(jdbcTemplate(dataSource()));
        }

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create().url("jdbc:mysql://localhost/order_mgmt").username("root").password("1234").type(HikariDataSource.class).build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

    }

}