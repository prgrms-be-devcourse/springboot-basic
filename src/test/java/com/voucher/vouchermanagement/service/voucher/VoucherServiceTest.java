package com.voucher.vouchermanagement.service.voucher;

import static com.wix.mysql.EmbeddedMysql.*;
import static com.wix.mysql.ScriptResolver.*;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.distribution.Version.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.voucher.vouchermanagement.configuration.YamlPropertiesFactory;
import com.voucher.vouchermanagement.controller.api.v1.request.UpdateVoucherRequest;
import com.voucher.vouchermanagement.dto.voucher.CreateVoucherRequest;
import com.voucher.vouchermanagement.dto.voucher.VoucherDto;
import com.voucher.vouchermanagement.model.voucher.FixedAmountVoucher;
import com.voucher.vouchermanagement.model.voucher.PercentDiscountVoucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import com.voucher.vouchermanagement.repository.voucher.VoucherJdbcRepository;
import com.voucher.vouchermanagement.repository.voucher.VoucherRepository;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;

@SpringJUnitConfig
@ActiveProfiles("prod")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VoucherServiceTest {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherRepository voucherRepository;

    private static EmbeddedMysql embeddedMysql;

    @Configuration
    @EnableTransactionManagement
    @ComponentScan(basePackages = {"com.voucher.vouchermanagement.service.voucher",
            "com.voucher.vouchermanagement.repository.voucher"})
    @PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
    static class AppConfig {
        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        VoucherRepository voucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new VoucherJdbcRepository(jdbcTemplate);
        }

        @Bean
        PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @AfterEach
    public void cleanEach() {
        voucherRepository.deleteAll();
    }

    @BeforeAll
    public void setup() {
        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-voucher", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    public void stopDb() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("다건 추가 테스트")
    public void multiInsertVoucherTest() {
        //given
        List<CreateVoucherRequest> vouchers = List.of(
                CreateVoucherRequest.of(new FixedAmountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now())),
                CreateVoucherRequest.of(new FixedAmountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now())),
                CreateVoucherRequest.of(new FixedAmountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now())),
                CreateVoucherRequest.of(new FixedAmountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now()))
        );
        voucherService.multiCreate(vouchers);

        //when
        List<VoucherDto> foundVouchers = voucherService.findAll();

        //then
        assertThat(foundVouchers.size(), is(4));
        assertThat(foundVouchers, containsInAnyOrder(samePropertyValuesAs(foundVouchers.get(0)),
                samePropertyValuesAs(foundVouchers.get(1)),
                samePropertyValuesAs(foundVouchers.get(2)),
                samePropertyValuesAs(foundVouchers.get(3))
        ));
    }

    @Test
    @DisplayName("100% 초과 PercentDiscountVoucher 생성 불가 테스트 - 101%")
    public void createPercentDiscountVoucherByOverMaxLimitValueTest() {
        assertThrows(IllegalArgumentException.class, () -> voucherService.create(VoucherType.Percent, 101L));
    }

    @Test
    @DisplayName("1% 미만 PercentDiscountVoucher 생성 불가 테스트 - 0%")
    public void createPercentDiscountVoucherByUnderMaxLimitValueTest() {
        assertThrows(IllegalArgumentException.class, () -> voucherService.create(VoucherType.Percent, 0L));
    }

    @Test
    @DisplayName("PercentDiscountVoucher의 최소 할인 비율은 1퍼센트다.")
    public void createPercentDiscountVoucherTest_1() {
        //given
        voucherService.create(VoucherType.Percent, 1L);

        //when
        List<VoucherDto> foundVoucher = voucherService.findAll();

        //then
        assertThat(foundVoucher.size(), is(1));
        assertThat(foundVoucher.get(0).getValue(), is(1L));
        assertThat(foundVoucher.get(0).getVoucherType().getTypeName(), is(PercentDiscountVoucher.class.getSimpleName()));
    }

    @Test
    @DisplayName("100% PercentDiscountVoucher 생성 테스트")
    public void createPercentDiscountVoucherTest_100() {
        //given
        voucherService.create(VoucherType.Percent, 100L);

        //when
        List<VoucherDto> foundVoucher = voucherService.findAll();

        //then
        assertThat(foundVoucher.size(), is(1));
        assertThat(foundVoucher.get(0).getValue(), is(100L));
        assertThat(foundVoucher.get(0).getVoucherType().getTypeName(), is(PercentDiscountVoucher.class.getSimpleName()));
    }

    @Test
    @DisplayName("FixedAmountVoucher의 최소 할인 금액은 1원이다.")
    public void createFixedAmountVoucherTest_1() {
        //given
        voucherService.create(VoucherType.Fixed, 1L);

        //when
        List<VoucherDto> foundVoucher = voucherService.findAll();

        //then
        assertThat(foundVoucher.size(), is(1));
        assertThat(foundVoucher.get(0).getValue(), is(1L));
        assertThat(foundVoucher.get(0).getVoucherType().getTypeName(), is(FixedAmountVoucher.class.getSimpleName()));
    }

    @Test
    @DisplayName("할인금액 1미만 FixedAmountVoucher 생성 불가 테스트 - 0")
    public void createFixedAmountVoucherByUnderMaxLimitValueTest() {
        assertThrows(IllegalArgumentException.class, () -> voucherService.create(VoucherType.Fixed, 0L));
    }

    @Test
    @DisplayName("5000원 할인 FixedAmountVoucher 생성 테스트")
    public void createFixedAmountVoucherTest_5000() {
        //given
        voucherService.create(VoucherType.Fixed, 5000L);

        //when
        List<VoucherDto> foundVoucher = voucherService.findAll();

        //then
        assertThat(foundVoucher.size(), is(1));
        assertThat(foundVoucher.get(0).getValue(), is(5000L));
        assertThat(foundVoucher.get(0).getVoucherType().getTypeName(), is(FixedAmountVoucher.class.getSimpleName()));
    }

    @Test
    @DisplayName("ID로 바우처 찾기 테스트")
    public void findByIdTest() {
        //given
        UUID uuid = voucherService.create(VoucherType.Fixed, 5000L);

        //when
        VoucherDto voucherDto = voucherService.findById(uuid);

        //then
        assertThat(voucherDto.getId(), is(uuid));
    }

    @Test
    @DisplayName("ID로 바우처 삭제 테스트")
    public void deleteByIdTest() {
        //given
        UUID voucherId = voucherService.create(VoucherType.Fixed, 10L);

        //when
        voucherService.deleteById(voucherId);

}
