package org.prgrms.assignment.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.prgrms.assignment.voucher.entity.VoucherEntity;
import org.prgrms.assignment.voucher.entity.VoucherHistoryEntity;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherStatus;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.prgrms.assignment.voucher.service.VoucherService;
import org.prgrms.assignment.voucher.service.VoucherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_17;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(
        basePackages = {"org.prgrms.assignment.voucher.repository"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/test_order_mgmt")
                .username("test")
                .password("test1234!")
                .type(HikariDataSource.class)
                .build();
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

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    DataSource dataSource;

    EmbeddedMysql embeddedMysql;

    VoucherEntity voucherEntity;

    VoucherHistoryEntity voucherHistoryEntity;

    UUID historyId;

    @BeforeAll
    void setup() {
        MysqldConfig mysqldConfig = aMysqldConfig(v8_0_17)
            .withCharset(UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
            .addSchema("test_order_mgmt", classPathScript("schema.sql"))
            .start();

        LocalDateTime now = LocalDateTime.now();
        historyId = UUID.randomUUID();
        voucherEntity = new VoucherEntity(UUID.randomUUID(), VoucherType.FIXED, now, 23L, now.plusDays(3));
        voucherHistoryEntity = new VoucherHistoryEntity(voucherEntity.voucherId(), historyId, VoucherStatus.UNUSED, now);
    }

    @AfterAll
    void endTest() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("바우처 엔티티를 저장할 수 있다.")
    void insertVoucherEntityTest() {
        // when
        jdbcVoucherRepository.insertVoucherEntity(voucherEntity);

        // then
        Optional<VoucherEntity> retrievedVoucherEntity = jdbcVoucherRepository.findVoucherEntityById(voucherEntity.voucherId());
        assertThat(retrievedVoucherEntity.isEmpty(), is(false));
        assertThat(retrievedVoucherEntity.get(), samePropertyValuesAs(voucherEntity));
    }

    @Test
    @Order(2)
    @DisplayName("바우처 히스토리 엔티티를 저장할 수 있다.")
    void insertVoucherHistoryEntityTest() {
        // when
        jdbcVoucherRepository.insertVoucherHistoryEntity(voucherHistoryEntity);

        // then
        Optional<VoucherHistoryEntity> retrievedVoucherHistoryEntity = jdbcVoucherRepository.findVoucherHistoryEntityById(historyId);
        assertThat(retrievedVoucherHistoryEntity.isEmpty(), is(false));
        assertThat(retrievedVoucherHistoryEntity.get(), samePropertyValuesAs(voucherHistoryEntity));
    }

    @Test
    @Order(3)
    @DisplayName("전체 바우처를 조회할 수 있다.")
    void findAllTest() {
        List<VoucherEntity> voucherEntityList = jdbcVoucherRepository.findAll();
        assertThat(voucherEntityList.isEmpty(), is(false));
        assertThat(voucherEntityList.size(), is(jdbcVoucherRepository.count()));
    }

    @Test
    @Order(4)
    @DisplayName("하나의 엔티티를 업데이트 할 수 있어야 한다.")
    void updateTest() {
        Long updateBenefit = voucherEntity.benefit() + 1L;
        VoucherEntity updateVoucherEntity = new VoucherEntity(voucherEntity.voucherId(), voucherEntity.voucherType(),
            voucherEntity.createdAt(), updateBenefit, voucherEntity.expireDate());

        jdbcVoucherRepository.update(updateVoucherEntity);

        Optional<VoucherEntity> retrievedUpdateVoucherEntity = jdbcVoucherRepository.findVoucherEntityById(voucherEntity.voucherId());
        assertThat(retrievedUpdateVoucherEntity.isEmpty(), is(false));
        assertThat(retrievedUpdateVoucherEntity.get(), samePropertyValuesAs(updateVoucherEntity));
    }

    @Test
    @Order(6)
    @DisplayName("모든 엔티티를 삭제할 수 있어야 한다.")
    void deleteAllTest() {
        jdbcVoucherRepository.deleteAll();
        assertThat(jdbcVoucherRepository.count(), is(0));
    }

    @Test
    @Order(5)
    @DisplayName("하나의 엔티티를 삭제할 수 있어야 한다.")
    void deleteTest() {
        jdbcVoucherRepository.delete(voucherEntity.voucherId());
        assertEquals(Optional.empty(), jdbcVoucherRepository.findVoucherEntityById(voucherEntity.voucherId()));
    }
}