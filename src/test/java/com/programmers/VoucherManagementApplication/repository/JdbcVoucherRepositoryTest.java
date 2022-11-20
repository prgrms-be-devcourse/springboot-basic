package com.programmers.VoucherManagementApplication.repository;

import com.programmers.VoucherManagementApplication.vo.Amount;
import com.programmers.VoucherManagementApplication.vo.VoucherType;
import com.programmers.VoucherManagementApplication.voucher.Voucher;
import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("jdbc")
class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.programmers.VoucherManagementApplication.repository"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher_mgmt")
                    .username("test")
                    .password("test1234")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    DataSource dataSource;

    EmbeddedMysql embeddedMysql;

    Voucher voucher;

    @BeforeAll
    void setup() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher_mgmt", classPathScript("vouchers.sql"))
                .start();
    }

    @BeforeEach
    void setInitData() {
        voucher = Voucher.from(UUID.randomUUID(), VoucherType.FIXED_DISCOUNT, new Amount(100L));
        jdbcVoucherRepository.insert(voucher);
    }

    @AfterEach
    void cleanRepository() {
        jdbcVoucherRepository.deleteAll();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("바우처를 추가할 수 있다.")
    void testInsert() {
        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId());
        assertThat(findVoucher.isEmpty()).isFalse();
        assertThat(voucher.getVoucherId()).isEqualTo(findVoucher.get().getVoucherId());
    }

    @Test
    @DisplayName("전체 바우처를 조회할 수 있다.")
    void testFindAll() {
        List<Voucher> vouchers = jdbcVoucherRepository.findAll();
        assertThat(vouchers.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("아이디로 바우처를 조회할 수 있다.")
    void testFindById() {
        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId());
        assertThat(findVoucher.isEmpty()).isFalse();
        assertThat(findVoucher.get().getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    @Test
    @DisplayName("등록되지 않은 아이디로 바우처를 조회할 수 없다.")
    void testFindById_fail() {
        Optional<Voucher> unknownVoucher = jdbcVoucherRepository.findById(UUID.randomUUID());
        assertThat(unknownVoucher.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("바우처의 사용여부를 수정할 수 있다.")
    void testUpdate() {
        voucher.changedStatus("Y");
        Voucher updateVoucher = jdbcVoucherRepository.update(voucher);

        assertThat(updateVoucher.getUsageStatus()).isEqualTo(voucher.getUsageStatus());
    }

    @Test
    @DisplayName("올바르지 않은 사용 여부가 들어오면 수정할 수 없다.")
    void testUpdate_fail() {
        assertThatThrownBy(() -> {
            voucher.changedStatus(" ");
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("전체 바우처를 삭제할 수 있다.")
    void testDeleteAll() {
        assertThat(jdbcVoucherRepository.count()).isEqualTo(1);
        jdbcVoucherRepository.deleteAll();
        assertThat(jdbcVoucherRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("등록된 바우처 입력시 해당 바우처를 삭제할 수 있다.")
    public void testDeleteById() {
        assertThat(jdbcVoucherRepository.count()).isEqualTo(1);
        jdbcVoucherRepository.deleteById(voucher.getVoucherId());
        assertThat(jdbcVoucherRepository.count()).isEqualTo(0);
    }
}