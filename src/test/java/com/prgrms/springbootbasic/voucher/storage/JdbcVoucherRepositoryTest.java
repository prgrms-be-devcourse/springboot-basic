package com.prgrms.springbootbasic.voucher.storage;

import com.prgrms.springbootbasic.common.exception.DataModifyingException;
import com.prgrms.springbootbasic.voucher.VoucherType;
import com.prgrms.springbootbasic.voucher.domain.FixedAmountVoucher;
import com.prgrms.springbootbasic.voucher.domain.PercentVoucher;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@ActiveProfiles("prod")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = "com.prgrms.springbootbasic.voucher"
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt?autoReconnect=true&verifyServerCertificate=false&useSSL=true")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    DataSource dataSource;

    private Voucher voucher;
    private List<Voucher> voucherList;

    @BeforeAll
    void setDatabase() {

        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        anEmbeddedMysql(mysqldConfig)
                .addSchema("test-order_mgmt", classPathScript("voucher.sql"))
                .start();
    }

    @BeforeEach
    public void setup() {
        voucher = new PercentVoucher(UUID.randomUUID(), 10, VoucherType.PERCENT);
        voucherList = List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 1000, VoucherType.FIXED_AMOUNT),
                new PercentVoucher(UUID.randomUUID(), 20, VoucherType.PERCENT),
                new FixedAmountVoucher(UUID.randomUUID(), 200, VoucherType.FIXED_AMOUNT)
        );
    }

    @AfterEach
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM voucher");
    }

    @Test
    @DisplayName("Voucher를 저장할 수 있다.")
    void save() {
        //when
        jdbcVoucherRepository.save(voucher);

        //then
        Optional<Voucher> found = jdbcVoucherRepository.findById(voucher.getUUID());

        assertThat(found.isPresent()).isTrue();
        Voucher foundVoucher = found.get();

        assertThat(foundVoucher)
                .usingRecursiveComparison()
                .isEqualTo(voucher);
    }

    @Test
    @DisplayName("모든 Voucher를 조회할 수 있다.")
    void findAll() {
        //given
        voucherList.forEach(jdbcVoucherRepository::save);

        //when
        List<Voucher> foundVouchers = jdbcVoucherRepository.findAll();

        //then
        assertThat(voucherList.size()).isEqualTo(foundVouchers.size());
    }


    @Test
    @DisplayName("Voucher를 수정할 수 있다.")
    void update() {
        //given
        jdbcVoucherRepository.save(voucher);

        //when
        int beforeChangeAmount = voucher.getDiscountRate();
        voucher.update(beforeChangeAmount - 1);

        jdbcVoucherRepository.update(voucher);
        Optional<Voucher> foundAfterUpdate = jdbcVoucherRepository.findById(voucher.getUUID());

        //then
        assertThat(foundAfterUpdate.isPresent()).isTrue();
        assertThat(foundAfterUpdate.get())
                .usingRecursiveComparison()
                .isEqualTo(voucher);
    }

    @Test
    @DisplayName("조회할 수 없는 PK값을 갖고있는 Voucher를 update하려고 하면 실패한다.")
    void updateFailIdNotFound() {
        //given
        jdbcVoucherRepository.save(voucher);

        //when&then
        PercentVoucher wrongIdVoucher = new PercentVoucher(UUID.randomUUID(), 99);
        assertThrows(DataModifyingException.class, () -> jdbcVoucherRepository.update(wrongIdVoucher));
    }

    @Test
    @DisplayName("Voucher id를 통해 Voucher를 삭제할 수 있다.")
    void deleteById() {
        //given
        jdbcVoucherRepository.save(voucher);
        List<Voucher> foundBeforeDelete = jdbcVoucherRepository.findAll();

        //when
        jdbcVoucherRepository.delete(voucher.getUUID());

        //then
        List<Voucher> foundAfterDelete = jdbcVoucherRepository.findAll();
        assertThat(foundAfterDelete.size()).isEqualTo(foundBeforeDelete.size() - 1);
    }

    @Test
    @DisplayName("조회할 수 없는 PK값을 갖고있는 Voucher을 delete하려고 하면 실패한다.")
    void deleteFailIdNotFound() {
        //given
        jdbcVoucherRepository.save(voucher);

        //when&then
        PercentVoucher wrongIdVoucher = new PercentVoucher(UUID.randomUUID(), 99);
        assertThrows(DataModifyingException.class, () -> jdbcVoucherRepository.delete(wrongIdVoucher.getUUID()));
    }
}