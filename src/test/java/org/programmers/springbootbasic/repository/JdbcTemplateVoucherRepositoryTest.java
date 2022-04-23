package org.programmers.springbootbasic.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.programmers.springbootbasic.voucher.domain.FixedDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.RateDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.repository.JdbcTemplateVoucherRepository;
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

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Slf4j
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcTemplateVoucherRepositoryTest {

    @Autowired
    private JdbcTemplateVoucherRepository voucherRepository;
    @Autowired
    private DataSourceCleaner dataSourceCleaner;

    //TODO PR 포인트: 실제 서비스에서 사용할 레포에 전체 데이터 삭제 메서드가 존재한다는 것 자체가 문제이기 때문, 혹시 더 나은 방법이 있는지 궁금
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

    //TODO PR 포인트: 테스트용 바우처 객체를 만들어서 사용하려고 했는데, enum 타입 필드때문에 문제가 생겼음: UNIQUE 제약조건 위반하거나 테스트를 위한 바우처 종류를 서비스용 코드에 추가해야함
    //TODO PR 포인트: 테스트를 위해서 insert() 리턴 값으로 영향 받은 컬럼 수 사용하는 것은 본말이 전도되었다고 느꼈음, 저장과 조회는 하나가 되어야 하지 않나?
    //TODO 기존에 데이터를 준비해놓고 하는게 낫나? 하지만 List 수 조회 같은 경우 테스트 케이스 주입받은 것에 좌우되버림
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

    @Test
    @DisplayName("바우처 삭제하기")
    void insertAndDelete() {
        var fixedVoucher = new FixedDiscountVoucher(UUID.randomUUID(), 3000);

        var insertedFixedVoucher = voucherRepository.insert(fixedVoucher);
        voucherRepository.remove(insertedFixedVoucher.getId());
        assertThat(voucherRepository.findById(insertedFixedVoucher.getId()).isEmpty(), is(true));
    }
}