package org.programmers.springbootbasic.voucher.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.programmers.springbootbasic.member.domain.SignedMember;
import org.programmers.springbootbasic.member.repository.JdbcTemplateMemberRepository;
import org.programmers.springbootbasic.voucher.domain.FixedDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.RateDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.FIXED;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.RATE;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Slf4j
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcTemplateVoucherRepositoryTest {

    @Autowired
    private JdbcTemplateVoucherRepository voucherRepository;
    @Autowired
    private JdbcTemplateMemberRepository memberRepository;
    @Autowired
    private DataSourceCleaner dataSourceCleaner;

    @Component
    static class DataSourceCleaner {

        private final JdbcTemplate template;

        public DataSourceCleaner(DataSource dataSource) {
            this.template = new JdbcTemplate(dataSource);
        }

        public void cleanDataBase() {
            template.update("TRUNCATE TABLE voucher");
            template.update("DELETE FROM member");
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

        @Bean
        JdbcTemplateMemberRepository memberRepository() {
            return new JdbcTemplateMemberRepository(dataSource());
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

        List<Voucher> expected = new ArrayList<>();
        expected.add(voucher1);
        expected.add(voucher2);
        expected.add(voucher3);

        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        voucherRepository.insert(voucher3);

        List<Voucher> foundVouchers = voucherRepository.findAll();
        assertThat(foundVouchers.size(), is(expected.size()));
        assertThat(foundVouchers.containsAll(expected), is(true));
    }

    @Test
    @DisplayName("종류에 따라 바우처를 검색하는 시험")
    void findByType() {
        var voucher1 = new FixedDiscountVoucher(UUID.randomUUID(), 3000);
        var voucher2 = new RateDiscountVoucher(UUID.randomUUID(), 30);
        var voucher3 = new FixedDiscountVoucher(UUID.randomUUID(), 5000);

        List<Voucher> expected = new ArrayList<>();
        expected.add(voucher1);
        expected.add(voucher3);

        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        voucherRepository.insert(voucher3);

        List<Voucher> foundVouchers = voucherRepository.findByType(FIXED);
        assertThat(foundVouchers.size(), is(expected.size()));
        assertThat(foundVouchers.containsAll(expected), is(true));
    }

    @Test
    @DisplayName("바우처 기간으로 조회")
    void findByDate() {
        var voucher1 = new FixedDiscountVoucher(UUID.randomUUID(), 3000, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 4, 25, 2, 40)));
        var voucher2 = new FixedDiscountVoucher(UUID.randomUUID(), 8000, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 3, 15, 14, 15)));
        var voucher3 = new FixedDiscountVoucher(UUID.randomUUID(), 12000, null,
                Timestamp.valueOf(LocalDateTime.of(2021, 1, 22, 10, 30)));
        var voucher4 = new RateDiscountVoucher(UUID.randomUUID(), 15, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 1, 22, 10, 30)));
        var voucher5 = new RateDiscountVoucher(UUID.randomUUID(), 30, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 3, 22, 10, 30)));
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        voucherRepository.insert(voucher3);
        voucherRepository.insert(voucher4);
        voucherRepository.insert(voucher5);

        List<Voucher> foundVouchers = voucherRepository.findByDate(
                Timestamp.valueOf(LocalDateTime.of(2022, 1, 1, 1, 10)),
                Timestamp.valueOf(LocalDateTime.of(2022, 4, 1, 2, 30)));

        List<Voucher> expected = new ArrayList<>();
        expected.add(voucher2);
        expected.add(voucher4);
        expected.add(voucher5);

        assertThat(foundVouchers.size(), is(expected.size()));
        assertThat(foundVouchers.containsAll(expected), is(true));
    }

    @Test
    @DisplayName("바우처 종류와 기간으로 조회")
    void findByTypeAndDate() {
        var voucher1 = new FixedDiscountVoucher(UUID.randomUUID(), 3000, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 4, 25, 2, 40)));
        var voucher2 = new FixedDiscountVoucher(UUID.randomUUID(), 8000, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 3, 15, 14, 15)));
        var voucher3 = new FixedDiscountVoucher(UUID.randomUUID(), 12000, null,
                Timestamp.valueOf(LocalDateTime.of(2021, 1, 22, 10, 30)));
        var voucher4 = new RateDiscountVoucher(UUID.randomUUID(), 15, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 1, 22, 10, 30)));
        var voucher5 = new RateDiscountVoucher(UUID.randomUUID(), 30, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 3, 22, 10, 30)));
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        voucherRepository.insert(voucher3);
        voucherRepository.insert(voucher4);
        voucherRepository.insert(voucher5);

        List<Voucher> foundVouchers = voucherRepository.findByTypeAndDate(RATE,
                Timestamp.valueOf(LocalDateTime.of(2022, 1, 1, 1, 10)),
                Timestamp.valueOf(LocalDateTime.of(2022, 4, 1, 2, 30)));

        for (Voucher foundVoucher : foundVouchers) {
            System.out.println("foundVoucher = " + foundVoucher);
        }

        List<Voucher> expected = new ArrayList<>();
        expected.add(voucher4);
        expected.add(voucher5);

        assertThat(foundVouchers.size(), is(expected.size()));
        assertThat(foundVouchers.containsAll(expected), is(true));
    }

    @Test
    @DisplayName("바우처 삭제하기")
    void insertAndDelete() {
        var fixedVoucher = new FixedDiscountVoucher(UUID.randomUUID(), 3000);

        var insertedFixedVoucher = voucherRepository.insert(fixedVoucher);
        voucherRepository.remove(insertedFixedVoucher.getId());
        assertThat(voucherRepository.findById(insertedFixedVoucher.getId()).isEmpty(), is(true));
    }

    @Test
    @DisplayName("바우처가 특정 멤버의 소유가 됨")
    void updateVoucherOwner() {
        var member = new SignedMember("tester", "email@mail.com");
        memberRepository.insert(member);
        var voucher = new FixedDiscountVoucher(UUID.randomUUID(), 3000);
        voucherRepository.insert(voucher);

        voucherRepository.updateVoucherOwner(voucher.getId(), member.getMemberId());
        var foundVoucher = voucherRepository.findById(voucher.getId()).get();

        assertThat(foundVoucher.getMemberId(), is(member.getMemberId()));
    }
}