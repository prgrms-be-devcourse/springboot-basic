package com.prgrms.vouchermanagement.core.voucher.repository.jdbc;

import com.prgrms.vouchermanagement.core.voucher.domain.Voucher;
import com.prgrms.vouchermanagement.core.voucher.domain.VoucherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("prod")
@ContextConfiguration(classes = {JdbcVoucherRepository.class, JdbcVoucherRepositoryTest.Config.class})
class JdbcVoucherRepositoryTest {

    @Configuration
    static class Config {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(H2)
                    .setScriptEncoding("UTF-8")
                    .ignoreFailedDrops(true)
                    .addScript("sql/voucher-init.sql")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @BeforeEach
    void cleanUp() {
        jdbcVoucherRepository.deleteAll();
    }

    @DisplayName("Voucher를 저장할 수 있다.")
    @Test
    void testSave() {
        // given
        Voucher voucher = new Voucher(UUID.randomUUID().toString(), "sujin", 1000, VoucherType.FIXED);

        // when
        jdbcVoucherRepository.save(voucher);
        List<Voucher> voucherList = jdbcVoucherRepository.findAll();

        // then
        assertThat(voucherList.contains(voucher), is(true));
    }

    @DisplayName("Voucher를 모두 삭제할 수 있다.")
    @Test
    void testDeleteAll() {
        // given

        // when
        jdbcVoucherRepository.deleteAll();
        List<Voucher> voucherList = jdbcVoucherRepository.findAll();

        // then
        assertThat(voucherList.size(), is(0));
    }

    @DisplayName("id 값으로 Voucher를 조회할 수 있다.")
    @Test
    void testFindById() {
        // given
        String uuid = UUID.randomUUID().toString();
        Voucher voucher = new Voucher(uuid, "sujin", 1000, VoucherType.FIXED);
        jdbcVoucherRepository.save(voucher);

        // when
        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(uuid);

        // then
        assertThat(findVoucher.get().equals(voucher), is(true));
    }

    @DisplayName("id 값들에 해당하는 Voucher를 모두 조회할 수 있다.")
    @Test
    void testFindAllByIds() {
        // given
        String uuid1 = UUID.randomUUID().toString();
        String uuid2 = UUID.randomUUID().toString();
        String uuid3 = UUID.randomUUID().toString();
        Voucher voucher1 = new Voucher(uuid1, "voucher1", 1000, VoucherType.FIXED);
        Voucher voucher2 = new Voucher(uuid2, "voucher2", 2000, VoucherType.FIXED);
        Voucher voucher3 = new Voucher(uuid3, "voucher3", 3000, VoucherType.FIXED);

        jdbcVoucherRepository.save(voucher1);
        jdbcVoucherRepository.save(voucher2);
        jdbcVoucherRepository.save(voucher3);

        // when
        List<Voucher> voucherList = jdbcVoucherRepository.findAllByIds(List.of(uuid1, uuid2));

        // then
        assertThat(voucherList.containsAll(List.of(voucher1, voucher2)), is(true));
    }
}