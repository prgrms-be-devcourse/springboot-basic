package org.prgrms.kdt.repository.voucher;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.Factory.VoucherFactory;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("staging")
class JdbcVoucherRepositoryTest {

    @Configuration
    static class Config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("root")
                    .password("admin")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }

        @Bean
        public VoucherRepository voucherRepository() {
            return new JdbcVoucherRepository(jdbcTemplate());
        }
    }

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    DataSource dataSource;

    @BeforeAll
    void setup() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처를 추가할 수 있다.")
    public void testInsert() throws Exception {
        //given
        Voucher voucher = createDummyData(UUID.randomUUID(), VoucherType.FIX, 50, LocalDateTime.now());

        //when
        voucherRepository.insert(voucher);

        //then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty()).isFalse();
        assertThat(vouchers).hasSize(1);
        assertThat(vouchers.get(0))
                .extracting(Voucher::getVoucherId, Voucher::getCreatedAt, Voucher::getType, Voucher::getValue)
                .containsExactly(voucher.getVoucherId(), voucher.getCreatedAt(), voucher.getType(), voucher.getValue());

    }

    @Test
    @DisplayName("음수 값의 바우처는 추가할 수 없다")
    public void testNegativeVoucherInsert() throws Exception {
        Voucher voucher = null;
        try {
            //given
            voucher = createDummyData(UUID.randomUUID(), VoucherType.FIX, -1, LocalDateTime.now());
            //when
            voucherRepository.insert(voucher);
        } catch (IllegalArgumentException e) {

        }

        //then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty()).isTrue();
        assertThat(vouchers).hasSize(0);
    }

    @Test
    @DisplayName("한계 값 이상의 바우처는 추가할 수 없다.")
    public void testOverLimitVoucherInsert() throws Exception {
        Voucher voucher = null;
        try {
            //given
            voucher = createDummyData(UUID.randomUUID(), VoucherType.FIX, VoucherType.FIX.getMaxValue() + 1, LocalDateTime.now());
            //when
            voucherRepository.insert(voucher);
        } catch (IllegalArgumentException e) {

        }

        //then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty()).isTrue();
        assertThat(vouchers).hasSize(0);
    }

    @Test
    @DisplayName("값이 0인 바우처는 추가할 수 없다.")
    public void testZeroVoucherInsert() throws Exception {
        Voucher voucher = null;
        try {
            //given
            voucher = createDummyData(UUID.randomUUID(), VoucherType.FIX, 0, LocalDateTime.now());
            //when
            voucherRepository.insert(voucher);
        } catch (IllegalArgumentException e) {

        }

        //then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty()).isTrue();
        assertThat(vouchers).hasSize(0);
    }

    @Test
    @DisplayName("바우처를 아이디로 삭제할 수 있다.")
    public void testDeleteById() throws Exception {
        //given
        Voucher voucher = createDummyData(UUID.randomUUID(), VoucherType.FIX, 10, LocalDateTime.now());
        voucherRepository.insert(voucher);
        //when
        voucherRepository.deleteById(voucher.getVoucherId());

        //then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers).hasSize(0);
        assertThat(voucherRepository.findById(voucher.getVoucherId())).isEmpty();
    }

    @Test
    @DisplayName("바우처를 객체로 삭제할 수 있다.")
    public void testDeleteByObject() throws Exception {
        //given
        Voucher voucher = createDummyData(UUID.randomUUID(), VoucherType.FIX, 10, LocalDateTime.now());
        voucherRepository.insert(voucher);
        //when
        voucherRepository.delete(voucher);

        //then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers).hasSize(0);
        assertThat(voucherRepository.findById(voucher.getVoucherId())).isEmpty();
    }

    @Test
    @DisplayName("바우처를 모두 삭제할 수 있다")
    public void testDeleteAll() throws Exception {
        //given
        int size = 10;
        for (int i=0;i<size;i++) {
            Voucher voucher = createDummyData(UUID.randomUUID(), VoucherType.FIX, 10, LocalDateTime.now());
            voucherRepository.insert(voucher);
        }
        //when
        voucherRepository.deleteAll();

        //then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers).hasSize(0);
    }

    @Test
    @DisplayName("바우처는 중복된 아이디를 가질 수 없다")
    public void testDuplicateId() throws Exception {
        UUID voucherId = UUID.randomUUID();
        try {
            //given
            Voucher voucher1 = createDummyData(voucherId, VoucherType.PERCENT, 10, LocalDateTime.now());
            Voucher voucher2 = createDummyData(voucherId, VoucherType.FIX, 10, LocalDateTime.now());

            //when
            voucherRepository.insert(voucher1);
            voucherRepository.insert(voucher2);
        } catch (DuplicateKeyException e) {

        }
        //then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers).hasSize(1);
        assertThat(vouchers.get(0).getVoucherId()).isEqualTo(voucherId);
    }





    Voucher createDummyData(UUID voucherId, VoucherType type, long value, LocalDateTime createdAt) {
        return VoucherFactory.createVoucher(voucherId, type, value, createdAt);
    }
}