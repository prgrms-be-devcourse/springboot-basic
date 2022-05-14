package org.programmers.kdt.weekly.voucher.repository;

import static com.wix.mysql.EmbeddedMysql.*;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.distribution.Version.*;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.programmers.kdt.weekly.voucher.model.FixedAmountVoucher;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        var config = aMysqldConfig(v5_7_latest)
            .withCharset(Charset.UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();
        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-voucher_mgmt", ScriptResolver.classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @Autowired
    VoucherRepository jdbcTemplateVoucherRepository;

    FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 25);

    @BeforeEach
    void setOn() {
        jdbcTemplateVoucherRepository.deleteAll();
        jdbcTemplateVoucherRepository.insert(voucher);
    }

    @Test
    @DisplayName("id가 중복된 바우처 저장되어 DuplicateKeyException 발생 되어야함")
    void insert() {
        //given
        //when
        //then
        assertThatThrownBy(() -> jdbcTemplateVoucherRepository.insert(voucher)).isInstanceOf(
            DuplicateKeyException.class);
    }

    @Test
    @DisplayName("해당ID 바우처를 return 해야함")
    void findById() {
        //given
        //when
        Optional<Voucher> foundVoucher = jdbcTemplateVoucherRepository.findById(
            voucher.getVoucherId());
        //then
        log.error(foundVoucher.get().toString());
        assertThat(voucher, samePropertyValuesAs(foundVoucher.get()));
    }

    @Test
    @DisplayName("모든 바우처를 return 해야함")
    void findByAll() {
        //given
        //when
        List<Voucher> vouchers = jdbcTemplateVoucherRepository.findAll();
        //then
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("바우처 value가 30으로 수정되어야함")
    void update() {
        //given
        voucher.changeValue(30);
        //when
        jdbcTemplateVoucherRepository.update(voucher);
        //then
        assertThat(voucher.getValue(), is(30L));
    }

    @Test
    @DisplayName("해당 id 바우처를 삭제해야함")
    void deleteById() {
        //given
        //when
        jdbcTemplateVoucherRepository.deleteById(voucher.getVoucherId());
        //then
        assertThat(jdbcTemplateVoucherRepository.findById(voucher.getVoucherId()),
            is(Optional.empty()));
    }

    @Test
    @DisplayName("모든 바우처를 삭제해야함")
    void deleteAll() {
        //given
        //when
        jdbcTemplateVoucherRepository.deleteAll();
        //then
        assertThat(jdbcTemplateVoucherRepository.findAll(), is(Collections.emptyList()));
    }
}