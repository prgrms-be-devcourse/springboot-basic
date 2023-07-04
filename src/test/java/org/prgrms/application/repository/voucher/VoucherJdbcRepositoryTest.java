package org.prgrms.application.repository.voucher;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.application.repository.exception.BadSqlExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.prgrms.application.domain.voucher.FixedAmountVoucher;
import org.prgrms.application.domain.voucher.PercentAmountVoucher;
import org.prgrms.application.domain.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.prgrms.application.domain.voucher.VoucherType.FIXED;
import static org.prgrms.application.domain.voucher.VoucherType.PERCENT;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(BadSqlExceptionHandler.class)
class VoucherJdbcRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepositoryTest.class);

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.application.domain.voucher", "org.prgrms.application.repository.voucher"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(EmbeddedDatabaseType.H2)
                    .setScriptEncoding("UTF-8")
                    .ignoreFailedDrops(true)
                    .addScript("schema.sql")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }
    }

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    @Autowired
    DataSource dataSource;

    FixedAmountVoucher newFixedVoucher;

    PercentAmountVoucher newPercentVoucher;

    @BeforeAll
    void setup() {
        newFixedVoucher = new FixedAmountVoucher(1L, FIXED, 10000);
        newPercentVoucher = new PercentAmountVoucher(2L, PERCENT, 50);
    }

    @Test
    @Order(1)
    @DisplayName("바우처를 추가할 수 있다.")
    public void testInsert() {
        try {
            voucherJdbcRepository.insert(newPercentVoucher);
            voucherJdbcRepository.insert(newFixedVoucher); //수정
        } catch (BadSqlGrammarException e) {
            logger.error("Got BadSqlGrammarException error code -> {}", e.getSQLException().getErrorCode(), e);
        }
        Optional<Voucher> retrievedFixedVoucher = voucherJdbcRepository.findById(newFixedVoucher.getVoucherId());
        assertThat(retrievedFixedVoucher.isEmpty(), is(false));
        assertThat(retrievedFixedVoucher.get(), samePropertyValuesAs(newFixedVoucher));

        Optional<Voucher> retrievedPercentVoucher = voucherJdbcRepository.findById(newPercentVoucher.getVoucherId());
        assertThat(retrievedPercentVoucher.isEmpty(), is(false));
        assertThat(retrievedPercentVoucher.get(), samePropertyValuesAs(newPercentVoucher));

    }

    @Test
    @Order(2)
    @DisplayName("전체 바우처를 조회할 수 있다.")
    public void testFindAll() {
        List<Voucher> vouchers = voucherJdbcRepository.findAll();
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("바우처 아이디로 조회할 수 있다.")
    public void testFindByName() {
        Optional<Voucher> fixedVoucher = voucherJdbcRepository.findById(newFixedVoucher.getVoucherId());
        assertThat(fixedVoucher.isEmpty(), is(false));

        Optional<Voucher> percentVoucher = voucherJdbcRepository.findById(newPercentVoucher.getVoucherId());
        assertThat(percentVoucher.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("바우처 타입으로 조회할 수 있다.")
    public void testFindByType() {
        Optional<List<Voucher>> fixedVoucher = voucherJdbcRepository.findByType(newFixedVoucher.getVoucherType());
        assertThat(fixedVoucher.isEmpty(), is(false));

        Optional<List<Voucher>> percentVoucher = voucherJdbcRepository.findByType(newPercentVoucher.getVoucherType());
        assertThat(percentVoucher.isEmpty(), is(false));
    }

    @Test
    @Order(6)
    @DisplayName("바우처를 수정할 수 있다.")
    public void testUpdate() {
        newFixedVoucher.changeFixedAmount(10000);
        voucherJdbcRepository.update(newFixedVoucher);

        List<Voucher> vouchers = voucherJdbcRepository.findAll();
        assertThat(vouchers, hasSize(2));
        assertThat(vouchers, hasItem(samePropertyValuesAs(newFixedVoucher)));

        Optional<Voucher> retrievedVoucher = voucherJdbcRepository.findById(newFixedVoucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(newFixedVoucher));

        newPercentVoucher.changePercentAmount(20);
        voucherJdbcRepository.update(newPercentVoucher);

        List<Voucher> vouchers2 = voucherJdbcRepository.findAll();
        assertThat(vouchers2, hasSize(2));
        assertThat(vouchers2, hasItem(samePropertyValuesAs(newPercentVoucher)));

        Optional<Voucher> retrievedVoucher2 = voucherJdbcRepository.findById(newPercentVoucher.getVoucherId());
        assertThat(retrievedVoucher2.isEmpty(), is(false));
        assertThat(retrievedVoucher2.get(), samePropertyValuesAs(newPercentVoucher));
    }

    @Test
    @Order(7)
    @DisplayName("바우처를 삭제할 수 있다.")
    public void testDelete() {
        voucherJdbcRepository.deleteAll();
        List<Voucher> vouchers = voucherJdbcRepository.findAll();
        assertThat(vouchers.isEmpty(), is(true));
    }


}