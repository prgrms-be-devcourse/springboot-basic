package org.prgrms.kdt.shop.repository;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.shop.domain.FixedAmountVoucher;
import org.prgrms.kdt.shop.domain.PercentDiscountVoucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemoryVoucherRepositoryTest {
    @Autowired
    VoucherRepository voucherRepository;

    @TestConfiguration
    static class TestConfig {
        @Bean
        VoucherRepository voucherRepository( ) {
            return new MemoryVoucherRepository();
        }
    }

    @Test
    @DisplayName("입력 테스트")
    @Order(1)
    void insert( ) {
        //given
        voucherRepository.deleteAll();
        //when
        voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 1000));
        //then
        assertThat(voucherRepository.findAll().isEmpty(), is(false));
    }

    @Test
    @DisplayName("모든 항목 검색 테스트")
    @Order(2)
    void findAll( ) {
        //given
        voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 2000));
        //when
        var findList = voucherRepository.findAll();
        //then
        assertThat(findList.size(), is(2));
    }

    @Test
    @DisplayName("ID를 이용한 검색 테스트")
    @Order(3)
    void findById( ) {
        //given
        UUID uuid = UUID.randomUUID();
        //when
        var insertVoucher = voucherRepository.insert(new PercentDiscountVoucher(uuid, 1500));
        var findVoucher = voucherRepository.findById(uuid);
        //then
        assertThat(findVoucher.get().getVoucherId(), is(insertVoucher.getVoucherId()));
        assertThat(findVoucher.get().getVoucherType(), is(insertVoucher.getVoucherType()));
        assertThat(findVoucher.get().getAmount(), is(insertVoucher.getAmount()));
        //given
        //when
        voucherRepository.deleteAll();
        //then
        assertThat(voucherRepository.findById(UUID.randomUUID()), is(Optional.empty()));
    }

    @Test
    @DisplayName("수정 테스트")
    @Order(4)
    void update( ) {
        //given
        voucherRepository.deleteAll();
        var uuid = UUID.randomUUID();
        var insertVoucher = voucherRepository.insert(new FixedAmountVoucher(uuid, 2300));
        var updateVoucher = new PercentDiscountVoucher(uuid, 160);
        //when
        voucherRepository.update(updateVoucher);
        //then
        assertThat(updateVoucher.getVoucherType(), is(voucherRepository.findById(insertVoucher.getVoucherId()).get().getVoucherType()));
        assertThat(updateVoucher.getAmount(), is(voucherRepository.findById(insertVoucher.getVoucherId()).get().getAmount()));
    }

    @Test
    @DisplayName("특정 항목 삭제 테스트")
    @Order(5)
    void deleteTest( ) {
        //given
        deleteAll();
        var uuid = UUID.randomUUID();
        voucherRepository.insert(new FixedAmountVoucher(uuid, 1000));
        //when
        voucherRepository.delete(uuid);
        //then
        assertThat(voucherRepository.findAll().size(), is(0));

    }

    @Test
    @DisplayName("모든 항목 삭제 테스트")
    @Order(6)
    void deleteAll( ) {
        //when
        voucherRepository.deleteAll();
        //then
        assertThat(voucherRepository.findAll().isEmpty(), is(true));
    }
}