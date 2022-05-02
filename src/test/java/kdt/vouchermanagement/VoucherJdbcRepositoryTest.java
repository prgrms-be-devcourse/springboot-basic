package kdt.vouchermanagement;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;
import kdt.vouchermanagement.domain.voucher.domain.FixedAmountVoucher;
import kdt.vouchermanagement.domain.voucher.domain.PercentDiscountVoucher;
import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import kdt.vouchermanagement.domain.voucher.repository.VoucherJdbcRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class VoucherJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"kdt.vouchermanagement.domain.voucher"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
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
    VoucherJdbcRepository voucherRepository;

    @Autowired
    DataSource dataSource;

    EmbeddedMysql embeddedMysql;


    Voucher voucherEntity = new FixedAmountVoucher(1L, VoucherType.FIXED_AMOUNT, 100, LocalDateTime.now(), LocalDateTime.now());

    @BeforeAll
    void setup() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test","test1234!")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("바우처 저장할 때 전달받은 Voucher가 NULL이라면 IllegalArgumentException이 발생한다._실패")
    void nullArgumentWhenSave() {
        //given
        Voucher voucher = null;

        //when, then
        assertThrows(IllegalArgumentException.class, () -> voucherRepository.save(voucher));
    }

    @Test
    @Order(2)
    @DisplayName("바우처 저장할 때 전달받은 Voucher가 정상적이라면 저장한 VoucherEntity를 반환_성공")
    void returnSavedVoucherWhenSave() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(VoucherType.FIXED_AMOUNT, 10);
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(VoucherType.PERCENT_DISCOUNT, 10);

        //when
        Voucher savedFixedAmountVoucherEntity = voucherRepository.save(fixedAmountVoucher);
        Voucher savedPercentDiscountVoucherEntity = voucherRepository.save(percentDiscountVoucher);

        //then
        assertThat(fixedAmountVoucher.getVoucherType()).isEqualTo(savedFixedAmountVoucherEntity.getVoucherType());
        assertThat(fixedAmountVoucher.getDiscountValue()).isEqualTo(savedFixedAmountVoucherEntity.getDiscountValue());
        assertThat(percentDiscountVoucher.getVoucherType()).isEqualTo(savedPercentDiscountVoucherEntity.getVoucherType());
        assertThat(percentDiscountVoucher.getDiscountValue()).isEqualTo(savedPercentDiscountVoucherEntity.getDiscountValue());
    }

    @Test
    @Order(3)
    @DisplayName("저장소에 저장된 모든 바우처들을 조회 후 바우처 리스트 반환_성공")
    void findAllVouchers() {
        //given, when
        List<Voucher> foundVouchers = voucherRepository.findAll();

        //then
        assertThat(foundVouchers.size()).isEqualTo(2);
    }

    @Test
    @Order(4)
    @DisplayName("바우처 id 값으로 조회시 조회가 되지 않았을 때 Optional.empty() 반환")
    void returnOptionalEmptyFindById() {
        //given
        Long voucherId = 100L;

        //when
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);

        //then
        assertThat(foundVoucher).isEqualTo(Optional.empty());
    }

    @Test
    @Order(5)
    @DisplayName("바우처 id 값으로 조회시 Optional로 감싼 바우처 반환")
    void returnOptionalVoucherFindById() {
        //given
        Long voucherId = 1L;

        //when
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);

        //then
        assertThat(foundVoucher.isPresent()).isTrue();
    }

    @Test
    @Order(6)
    @DisplayName("바우처 id 값으로 바우처 삭제")
    void deleteVoucherById() {
        //given
        Long voucherId = 1L;

        //when, then
        voucherRepository.deleteById(voucherId);
    }
}
