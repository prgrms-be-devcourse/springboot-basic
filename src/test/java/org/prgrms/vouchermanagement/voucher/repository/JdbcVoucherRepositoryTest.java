package org.prgrms.vouchermanagement.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.domain.VoucherType;
import org.prgrms.vouchermanagement.voucher.domain.dto.VoucherCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;
    @Autowired
    DataSource dataSource;
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-customer", ScriptResolver.classPathScript("schema/create_vouchers_schema.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @AfterEach
    void clear() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    @Test
    @DisplayName("바우처 저장 성공")
    void saveVoucher() {
        // given
        Voucher fixedAmountVoucher = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 100, UUID.randomUUID());

        // when
        jdbcVoucherRepository.save(fixedAmountVoucher);

        // then
        Voucher savedVoucher = jdbcVoucherRepository.findAll().get(0);

        assertThat(fixedAmountVoucher)
                .usingRecursiveComparison()
                .isEqualTo(savedVoucher);
    }

    @Test
    @DisplayName("바우처 ID로 찾기 성공")
    void findVoucherById() {
        // given
        Voucher voucher = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 1000, UUID.randomUUID());
        Voucher anotherVoucher = createVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 10, UUID.randomUUID());

        jdbcVoucherRepository.save(voucher);

        // when
        Voucher findVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId()).get();

        // then
        assertThat(voucher).usingRecursiveComparison()
                .isEqualTo(findVoucher);
    }

    @Test
    @DisplayName("찾는 바우처 ID가 DB에 없는 경우")
    void findVoucherIdNotExist() {

        // given
        Voucher voucher = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 1000, UUID.randomUUID());
        Voucher anotherVoucher = createVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 10, UUID.randomUUID());

        jdbcVoucherRepository.save(voucher);

        // when
        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(anotherVoucher.getVoucherId());

        // then
        assertThat(findVoucher).isEmpty();
    }

    @Test
    @DisplayName("모든 바우처 조회 성공")
    void findAllVouchers() {
        // given
        Voucher voucher1 = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 1000, UUID.randomUUID());
        Voucher voucher2 = createVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 10, UUID.randomUUID());

        jdbcVoucherRepository.save(voucher1);
        jdbcVoucherRepository.save(voucher2);

        // when
        List<Voucher> allVouchers = jdbcVoucherRepository.findAll();

        // then
        assertThat(allVouchers).hasSize(2);
        assertThat(allVouchers)
                .extracting("voucherId", "discountAmount", "voucherType", "customerId")
                .contains(tuple(voucher1.getVoucherId(), voucher1.getDiscountAmount(), voucher1.getVoucherType(), voucher1.getCustomerId()))
                .contains(tuple(voucher2.getVoucherId(), voucher2.getDiscountAmount(), voucher2.getVoucherType(), voucher2.getCustomerId()));
    }

    @Test
    @DisplayName("DB에 데이터가 존재하지 않는 경우 모든 바우처 조회 성공")
    void findAllVouchersNotExist() {

        // when
        List<Voucher> allVouchers = jdbcVoucherRepository.findAll();

        // then
        assertThat(allVouchers).isEmpty();
    }

    @Test
    @DisplayName("DB 데이터 전부 삭제")
    void deleteAll() {
        // given
        Voucher voucher1 = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 1000, UUID.randomUUID());
        Voucher voucher2 = createVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 10, UUID.randomUUID());

        jdbcVoucherRepository.save(voucher1);
        jdbcVoucherRepository.save(voucher2);

        // when
        jdbcVoucherRepository.clear();

        // then
        List<Voucher> allVouchers = jdbcVoucherRepository.findAll();
        assertThat(allVouchers).isEmpty();
    }

    private Voucher createVoucher(UUID uuid, VoucherType voucherType, int discountAmount, UUID customerId) {
        return VoucherType.createVoucher(VoucherCreateDTO.of(uuid, voucherType.name(), discountAmount, customerId));
    }

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.vouchermanagement.customer"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-customer")
                    .username("test")
                    .password("test")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public JdbcVoucherRepository jdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new JdbcVoucherRepository(namedParameterJdbcTemplate);
        }
    }

}