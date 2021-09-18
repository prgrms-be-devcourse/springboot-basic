package org.programmers.voucher.service;

import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.programmers.voucher.model.VoucherBase;
import org.programmers.voucher.model.VoucherType;
import org.programmers.voucher.repository.VoucherJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherBaseJdbcServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(VoucherBaseJdbcServiceTest.class);

    @Configuration
    @ComponentScan(basePackages = {"org.programmers.voucher.service"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/homework")
                    .username("root")
                    .password("skyey9808")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        VoucherJdbcRepository voucherJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new VoucherJdbcRepository(namedParameterJdbcTemplate);
        }

        @Bean
        VoucherJdbcService voucherJdbcService(VoucherJdbcRepository voucherJdbcRepository) {
            return new VoucherJdbcService(voucherJdbcRepository);
        }
    }

    @Autowired
    VoucherJdbcService voucherJdbcService;

    VoucherBase fixedVoucherBase;
    VoucherBase percentVoucherBase;

    @BeforeEach
    void setUp() {
        fixedVoucherBase = new VoucherBase(UUID.randomUUID(), VoucherType.FIXED, 10L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        percentVoucherBase = new VoucherBase(UUID.randomUUID(), VoucherType.PERCENT, 10L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

        voucherJdbcService.save(fixedVoucherBase);
        voucherJdbcService.save(percentVoucherBase);
    }

    @AfterEach
    void clearUp() {
        voucherJdbcService.deleteAllVouchers();
    }

    @Test
    @DisplayName("바우처를 새로 생성 및 저장할 수 있다.")
    void create() {
        VoucherBase testVoucherBase = new VoucherBase(UUID.randomUUID(), VoucherType.FIXED, 50L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        voucherJdbcService.save(testVoucherBase);

        VoucherBase findVoucherBase = voucherJdbcService.findById(testVoucherBase.getVoucherId());
        assertThat(findVoucherBase, samePropertyValuesAs(testVoucherBase));
    }

    @Test
    @DisplayName("바우처의 타입과 값으로 바우처를 찾을 수 있다.")
    void findVoucherByVoucherTypeAndVoucherValue() {
        VoucherBase findFixedVoucherBase = voucherJdbcService.findByVoucherTypeAndVoucherValue(VoucherType.FIXED, 10L);
        VoucherBase findPercentVoucherBase = voucherJdbcService.findByVoucherTypeAndVoucherValue(VoucherType.PERCENT, 10L);
        assertThat(findFixedVoucherBase, samePropertyValuesAs(fixedVoucherBase));
        assertThat(findPercentVoucherBase, samePropertyValuesAs(percentVoucherBase));
    }

    @Test
    @DisplayName("바우처의 값을 수정할 수 있다.")
    void update() {
        voucherJdbcService.update(VoucherType.FIXED, 10L, 50L);
        VoucherBase findVoucherBase = voucherJdbcService.findById(fixedVoucherBase.getVoucherId());

        Assertions.assertThat(findVoucherBase.getVoucherValue()).isEqualTo(50L);
    }

    @Test
    @DisplayName("바우처 전체를 조회할 수 있다.")
    void findAllVouchers() {
        List<VoucherBase> allVoucherBases = voucherJdbcService.findAllVouchers();

        assertThat(allVoucherBases, hasSize(2));
    }

    @Test
    @DisplayName("아이디로 바우처를 조회할 수 있다.")
    void findById() {
        VoucherBase findVoucherBase = voucherJdbcService.findById(fixedVoucherBase.getVoucherId());
        assertThat(findVoucherBase, samePropertyValuesAs(fixedVoucherBase));
    }

    @Test
    @DisplayName("모든 바우처를 삭제할 수 있다.")
    void deleteAllVouchers() {
        List<VoucherBase> beforeVoucherBases = voucherJdbcService.findAllVouchers();
        assertThat(beforeVoucherBases, hasSize(2));

        voucherJdbcService.deleteAllVouchers();
        List<VoucherBase> afterVoucherBases = voucherJdbcService.findAllVouchers();
        assertThat(afterVoucherBases, hasSize(0));
    }

    @Test
    @DisplayName("타입과 값으로 바우처를 삭제할 수 있다.")
    void deleteByVoucherTypeAndVoucherValue() {
        List<VoucherBase> beforeVoucherBases = voucherJdbcService.findAllVouchers();
        assertThat(beforeVoucherBases, hasSize(2));

        voucherJdbcService.deleteByVoucherTypeAndVoucherValue(VoucherType.FIXED, 10L);
        List<VoucherBase> afterVoucherBases = voucherJdbcService.findAllVouchers();
        assertThat(afterVoucherBases, hasSize(1));
    }

    @Test
    @DisplayName("아이디로 바우처를 삭제할 수 있다.")
    void deleteVoucherByVoucherId() {
        List<VoucherBase> beforeVoucherBases = voucherJdbcService.findAllVouchers();
        assertThat(beforeVoucherBases, hasSize(2));

        voucherJdbcService.deleteByVoucherId(fixedVoucherBase.getVoucherId());
        List<VoucherBase> afterVoucherBases = voucherJdbcService.findAllVouchers();
        assertThat(afterVoucherBases, hasSize(1));
    }
}