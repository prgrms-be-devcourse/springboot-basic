package org.prgrms.kdt.jdbcRepository;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.configuration.AppConfig;
import org.prgrms.kdt.domain.CustomerEntity;
import org.prgrms.kdt.domain.VoucherEntity;
import org.prgrms.kdt.enumType.VoucherStatus;
import org.prgrms.kdt.repository.CustomerRepository;
import org.prgrms.kdt.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.junit.jupiter.api.Assertions.*;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@Import({AppConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherJdbcRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    DataSource dataSource;

    VoucherEntity testVoucher, testVoucher2;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        testVoucher = new VoucherEntity(UUID.randomUUID(), VoucherStatus.FIXED, 3000L, LocalDateTime.now());
        testVoucher2 = new VoucherEntity(UUID.randomUUID(), VoucherStatus.PERCENT, 15L, LocalDateTime.now());
        var mysqldConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia.Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-order-mgmt", classPathScript("schema.sql"))
                .start();
    }

    @Test
    @Order(1)
    @DisplayName("바우처를 저장할 수 있다.")
    void testInsert() {
        voucherRepository.insert(testVoucher);

        System.out.println("voucherId -> " + testVoucher.getVoucherId());
        var retrievedVoucher = voucherRepository.findById(testVoucher.getVoucherId());
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(testVoucher));
    }

    @Test
    @Order(2)
    @DisplayName("특정 바우처를 조회 할 수 있습니다.")
    void testFindById() {
//        voucherRepository.insert(testVoucher);

        var retrivedVoucher = voucherRepository.findById(testVoucher.getVoucherId());
        assertThat(retrivedVoucher.get(), samePropertyValuesAs(testVoucher));
    }

    @Test
    @Order(3)
    @DisplayName("생성한 바우처를 모두 조회할 수 있다.")
    void testFindAll() {
        voucherRepository.insert(testVoucher2);

        var voucherList = voucherRepository.findAll();
        assertThat(voucherList.isEmpty() , is(false));
        assertThat(voucherList.size(), is(2));
    }

    @Test
    @Order(4)
    @DisplayName("DB에 있는 모든 바우처를 삭제 할 수 있다.")
    void deleteAll() {
        voucherRepository.deleteAll();
        var voucherList = voucherRepository.findAll();
        assertThat(voucherList.isEmpty(), is(true));
        assertThat(voucherList.size(), is(0));
    }

    @Test
    @Order(5)
    @DisplayName("특정 바우처를 삭제할 수 있다.")
    void deleteById() {
        voucherRepository.insert(testVoucher);

        voucherRepository.deleteById(testVoucher.getVoucherId());
        var retrivedVoucher = voucherRepository.findById(testVoucher.getVoucherId());
        assertThat(retrivedVoucher.isEmpty(), is(true));
    }

    @Test
    @Order(6)
    @DisplayName("바우처를 수정할 수 있다.")
    void testUpdate() {
        voucherRepository.insert(testVoucher);
        var changeVoucher = new VoucherEntity(testVoucher.getVoucherId(),VoucherStatus.PERCENT,14L,testVoucher.getCreatedAt());

        voucherRepository.update(changeVoucher);
        var retrivedChangeVoucher = voucherRepository.findById(changeVoucher.getVoucherId());
        assertThat(retrivedChangeVoucher.get(), samePropertyValuesAs(changeVoucher));
    }
}