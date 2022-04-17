package org.prgms.voucherProgram.repository.voucher;

import static com.wix.mysql.EmbeddedMysql.*;
import static com.wix.mysql.config.Charset.*;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.distribution.Version.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgms.voucherProgram.domain.voucher.FixedAmountVoucher;
import org.prgms.voucherProgram.domain.voucher.PercentDiscountVoucher;
import org.prgms.voucherProgram.domain.voucher.Voucher;
import org.prgms.voucherProgram.exception.NothingChangeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;

@SpringJUnitConfig
class JdbcVoucherRepositoryTest {

    private static final List<Voucher> vouchers = List.of(
        new FixedAmountVoucher(UUID.randomUUID(), UUID.randomUUID(), 20L),
        new PercentDiscountVoucher(UUID.randomUUID(), 30L));
    private static EmbeddedMysql embeddedMysql;
    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    @BeforeAll
    static void setup() {
        MysqldConfig config = aMysqldConfig(v8_0_17)
            .withCharset(UTF8)
            .withPort(2215)
            .withUser("test", "test1234")
            .withTimeZone("Aisa/Seoul")
            .build();

        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    static void tearDown() {
        embeddedMysql.stop();
    }

    private static Stream<Arguments> provideVoucher() {
        return Stream.of(
            Arguments.of(new FixedAmountVoucher(UUID.randomUUID(), UUID.randomUUID(), 20L)),
            Arguments.of(new FixedAmountVoucher(UUID.randomUUID(), 10L)),
            Arguments.of(new PercentDiscountVoucher(UUID.randomUUID(), 30L)),
            Arguments.of(new PercentDiscountVoucher(UUID.randomUUID(), UUID.randomUUID(), 30L))
        );
    }

    @AfterEach
    void clear() {
        jdbcVoucherRepository.deleteAll();
    }

    @DisplayName("바우처를 저장한다.")
    @ParameterizedTest
    @MethodSource("provideVoucher")
    void should_ReturnVoucher_When_save(Voucher voucher) {
        // given
        // when
        jdbcVoucherRepository.save(voucher);
        // then
        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId());
        assertThat(findVoucher).isNotEmpty();
        assertThat(findVoucher.get()).usingRecursiveComparison()
            .isEqualTo(voucher);
    }

    @DisplayName("모든 바우처를 삭제한다.")
    @Test
    void should_DeleteAllVouchers() {
        // given
        vouchers.forEach(voucher -> jdbcVoucherRepository.save(voucher));
        // when
        jdbcVoucherRepository.deleteAll();
        // then
        assertThat(jdbcVoucherRepository.findAll()).isEmpty();
    }

    @DisplayName("모든 바우처를 조회한다.")
    @Test
    void should_ReturnAllVoucher() {
        // given
        vouchers.forEach(voucher -> jdbcVoucherRepository.save(voucher));
        // when
        // then
        assertThat(jdbcVoucherRepository.findAll()).hasSize(vouchers.size())
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields()
            .containsAll(vouchers);
    }

    @DisplayName("ID를 통해 바우처를 조회한다.")
    @ParameterizedTest
    @MethodSource("provideVoucher")
    void sholud_ReturnVoucher_VoucherIsExists(Voucher voucher) {
        // given
        jdbcVoucherRepository.save(voucher);
        // when
        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId());
        // then
        assertThat(findVoucher).isNotEmpty()
            .get()
            .usingRecursiveComparison()
            .isEqualTo(voucher);
    }

    @DisplayName("저장되지 않은 ID라면 empty를 반환한다.")
    @Test
    void should_ReturnEmpty_VoucherIsNotExists() {
        // given
        UUID voucherId = UUID.randomUUID();
        //when
        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(voucherId);
        //then
        assertThat(findVoucher).isEmpty();
    }

    @DisplayName("바우처를 수정한다.")
    @ParameterizedTest
    @MethodSource("provideVoucher")
    void should_updateVoucher(Voucher voucher) {
        // given
        jdbcVoucherRepository.save(voucher);
        // when
        voucher.changeDiscountValue(100L);
        jdbcVoucherRepository.update(voucher);
        // then
        Optional<Voucher> updateVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId());
        assertThat(updateVoucher).isNotEmpty();
        assertThat(updateVoucher.get()).usingRecursiveComparison()
            .isEqualTo(voucher);
    }

    @DisplayName("ID를 통해 바우처를 삭제한다.")
    @ParameterizedTest
    @MethodSource("provideVoucher")
    void should_DeleteVoucher_VoucherIsExists(Voucher voucher) {
        // given
        jdbcVoucherRepository.save(voucher);
        // when
        jdbcVoucherRepository.deleteById(voucher.getVoucherId());
        // then
        assertThat(jdbcVoucherRepository.findById(voucher.getVoucherId())).isEmpty();
    }

    @DisplayName("잘못된 ID로 삭제하려고 하면 예외를 발생한다.")
    @Test
    void should_ThrowException_When_VoucherIsNotExists() {
        // given
        UUID voucherID = UUID.randomUUID();
        // when
        // then
        assertThatThrownBy(() -> jdbcVoucherRepository.deleteById(voucherID))
            .isInstanceOf(NothingChangeException.class)
            .hasMessage("[ERROR] 해당 요청이 정상적으로 처리되지 않았습니다.");
    }

    @Configuration
    @ComponentScan(basePackages = "org.prgms.voucherProgram.repository.voucher",
        excludeFilters = @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            value = FileVoucherRepository.class))
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                .username("test")
                .password("test1234")
                .type(HikariDataSource.class)
                .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }
}
