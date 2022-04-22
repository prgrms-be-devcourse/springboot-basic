package org.programmers.springbootbasic.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.programmers.springbootbasic.voucher.FixedDiscountVoucher;
import org.programmers.springbootbasic.voucher.RateDiscountVoucher;
import org.programmers.springbootbasic.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Slf4j
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcTemplateVoucherRepositoryTest {

    @Autowired
    private JdbcTemplateVoucherRepository voucherRepository;
    @Autowired
    private DataSourceCleaner dataSourceCleaner;

    @Component
    static class DataSourceCleaner {

        private JdbcTemplate template;

        public DataSourceCleaner(DataSource dataSource) {
            this.template = new JdbcTemplate(dataSource);
        }

        public void cleanDataBase() {
            int truncate_table_voucher = template.update("truncate table voucher");
        }
    }

    @Configuration
    static class TestConfig {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(H2)
                    .setScriptEncoding("UTF-8")
                    .addScript("h2/schema.sql")
                    .build();
        }

        @Bean
        public DataSourceCleaner dataSourceCleaner() {
            return new DataSourceCleaner(dataSource());
        }

        @Bean
        JdbcTemplateVoucherRepository voucherRepository() {
            return new JdbcTemplateVoucherRepository(dataSource());
        }
    }

    @AfterEach
    void cleanTestData() {
        dataSourceCleaner.cleanDataBase();
    }

    @Test
    @DisplayName("바우처 저장, 조회")
    void insertAndFind() {
        var fixedVoucher = new FixedDiscountVoucher(UUID.randomUUID(), 3000);

        var insertedFixedVoucher = voucherRepository.insert(fixedVoucher);
        var foundFixedVoucher = voucherRepository.findById(insertedFixedVoucher.getId()).get();

        assertThat(insertedFixedVoucher, is(foundFixedVoucher));

        var rateVoucher = new RateDiscountVoucher(UUID.randomUUID(), 30);

        var insertedRateVoucher = voucherRepository.insert(rateVoucher);
        var foundRateVoucher = voucherRepository.findById(insertedRateVoucher.getId()).get();

        assertThat(insertedRateVoucher, is(foundRateVoucher));
    }

    @Test
    @DisplayName("복수의 바우처를 조회하는 시험")
    void findAll() {
        var voucher1 = new FixedDiscountVoucher(UUID.randomUUID(), 3000);
        var voucher2 = new RateDiscountVoucher(UUID.randomUUID(), 30);
        var voucher3 = new FixedDiscountVoucher(UUID.randomUUID(), 5000);

        List<Voucher> addedVouchers = new ArrayList<>();
        addedVouchers.add(voucher1);
        addedVouchers.add(voucher2);
        addedVouchers.add(voucher3);

        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        voucherRepository.insert(voucher3);

        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.size(), is(3));
        assertThat(addedVouchers.containsAll(vouchers), is(true));
    }
}