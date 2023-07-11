package org.prgrms.application.repository.voucher;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.application.entity.VoucherEntity;
import org.prgrms.application.repository.exception.BadSqlExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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

@SpringJUnitConfig

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(BadSqlExceptionHandler.class)
class VoucherJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.application.domain.voucher", "org.prgrms.application.repository.voucher","org.prgrms.application.service"}
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
        }}

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    @Autowired
    DataSource dataSource;

    VoucherEntity newFixedVoucher;
    VoucherEntity newPercentVoucher;

    @BeforeAll
    void setup() {
        newFixedVoucher = new VoucherEntity(1L, "FIXED", 10000);
        newPercentVoucher = new VoucherEntity(2L, "PERCENT", 50);
    }

    @Test
    @DisplayName("바우처를 추가할 수 있다.")
    public void testInsert() {
        voucherJdbcRepository.insert(newFixedVoucher);
        voucherJdbcRepository.insert(newPercentVoucher);

        Optional<VoucherEntity> retrievedFixedVoucher = voucherJdbcRepository.findById(newFixedVoucher.getVoucherId());
        assertThat(retrievedFixedVoucher.isEmpty(), is(false));
        assertThat(retrievedFixedVoucher.get(), samePropertyValuesAs(newFixedVoucher));

        Optional<VoucherEntity> retrievedPercentVoucher = voucherJdbcRepository.findById(newPercentVoucher.getVoucherId());
        assertThat(retrievedPercentVoucher.isEmpty(), is(false));
        assertThat(retrievedPercentVoucher.get(), samePropertyValuesAs(newPercentVoucher));

    }

    @Test
    @DisplayName("전체 바우처를 조회할 수 있다.")
    public void testFindAll() {
        List<VoucherEntity> vouchers = voucherJdbcRepository.findAll();
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("바우처 아이디로 조회할 수 있다.")
    public void testFindByName() {
        Optional<VoucherEntity> fixedVoucher = voucherJdbcRepository.findById(100L);
        assertThat(fixedVoucher.isEmpty(), is(false));

        Optional<VoucherEntity> percentVoucher = voucherJdbcRepository.findById(1000L);
        assertThat(percentVoucher.isEmpty(), is(false));
    }

    @Test
    @DisplayName("바우처 타입으로 조회할 수 있다.")
    public void testFindByType() {
        List<VoucherEntity> fixedVouchers = voucherJdbcRepository.findByType("FIXED");
        assertThat(fixedVouchers.isEmpty(), is(false));

        List<VoucherEntity> percentVouchers = voucherJdbcRepository.findByType("PERCENT");
        assertThat(percentVouchers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("바우처를 수정할 수 있다.")
    public void testUpdate() {
        Optional<VoucherEntity> fixedVoucherEntity = voucherJdbcRepository.findById(100L);
        VoucherEntity notNullFixedEntity = fixedVoucherEntity.orElse(null);
        notNullFixedEntity.changeDiscountAmount(10000);
        voucherJdbcRepository.update(notNullFixedEntity);

        List<VoucherEntity> vouchers = voucherJdbcRepository.findAll();
        assertThat(vouchers, hasSize(2));
        assertThat(vouchers, hasItem(samePropertyValuesAs(notNullFixedEntity)));

        Optional<VoucherEntity> retrievedVoucher = voucherJdbcRepository.findById(100L);
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(notNullFixedEntity));


        Optional<VoucherEntity> percentVoucherEntity = voucherJdbcRepository.findById(100L);
        VoucherEntity notNullPercentEntity = percentVoucherEntity.orElse(null);
        notNullPercentEntity.changeDiscountAmount(20);
        voucherJdbcRepository.update(notNullPercentEntity);

        List<VoucherEntity> vouchers2 = voucherJdbcRepository.findAll();
        assertThat(vouchers2, hasSize(2));
        assertThat(vouchers2, hasItem(samePropertyValuesAs(notNullPercentEntity)));

        Optional<VoucherEntity> retrievedVoucher2 = voucherJdbcRepository.findById(notNullPercentEntity.getVoucherId());
        assertThat(retrievedVoucher2.isEmpty(), is(false));
        assertThat(retrievedVoucher2.get(), samePropertyValuesAs(notNullPercentEntity));
    }

    @Test
    @DisplayName("바우처 전체를 삭제할 수 있다.")
    public void testDelete() {
        voucherJdbcRepository.deleteAll();
        List<VoucherEntity> vouchers = voucherJdbcRepository.findAll();
        assertThat(vouchers.isEmpty(), is(true));
    }


}