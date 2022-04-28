package com.blessing333.springbasic.domain.voucher.repository;

import com.blessing333.springbasic.domain.voucher.model.Voucher;
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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcTemplateVoucherRepositoryTest {
    @Autowired
    DataSource dataSource;
    EmbeddedMysql embeddedMysql;
    @Autowired
    private JdbcTemplateVoucherRepository repository;

    @BeforeAll
    void setup() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher", classPathScript("voucher.sql"))
                .start();
    }

    @AfterEach
    void resetDB(){
        repository.deleteAll();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @DisplayName("새로운 바우처를 저장할 수 있어야 한다")
    @Test
    void insert() {
        UUID id = UUID.randomUUID();
        Voucher voucher = createVoucher(id, Voucher.VoucherType.FIXED,200);
        repository.insert(voucher);
        Optional<Voucher> found = repository.findById(id);
        assertTrue(found.isPresent());
        Voucher v = found.get();
        assertThat(v.getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(v.getDiscountAmount()).isEqualTo(voucher.getDiscountAmount());
        assertThat(v.getVoucherType()).isEqualTo(voucher.getVoucherType());
    }

    @DisplayName("바우처 정보를 수정할 수 있어야 한다.")
    @Test
    void updateTest() {
        UUID id = UUID.randomUUID();
        long beforeDiscountAmount = 5000;
        long afterDiscountAmount = 10000;
        Voucher voucher = createVoucher(id, Voucher.VoucherType.FIXED,beforeDiscountAmount);
        repository.insert(voucher);

        voucher.changeDiscountAmount(afterDiscountAmount);
        repository.update(voucher);

        Optional<Voucher> byId = repository.findById(id);
        assertTrue(byId.isPresent());
        Voucher found = byId.get();
        assertThat(found.getDiscountAmount()).isEqualTo(afterDiscountAmount);
    }

    @DisplayName("바우처 id를 통해 특정 바우처의 정보를 조회할 수 있어야 한다")
    @Test
    void findByIdTest() {
        UUID id = UUID.randomUUID();
        long percent = 30;
        Voucher voucher = createVoucher(id, Voucher.VoucherType.PERCENT,percent);
        repository.insert(voucher);

        Optional<Voucher> byId = repository.findById(id);

        assertTrue(byId.isPresent());
        Voucher found = byId.get();
        assertThat(found.getVoucherId()).isEqualTo(id);
        assertThat(found.getVoucherType()).isEqualTo(Voucher.VoucherType.PERCENT);
        assertThat(found.getDiscountAmount()).isEqualTo(percent);
    }

    @DisplayName("존재하지 않는 id로 바우처 조회를 시도할 경우 예외를 발생시키지 않고 Optional에 null을 담아 반환한다.")
    @Test
    void findByIdWithInvalidIdTest() {
        UUID invalidId = UUID.randomUUID();

        Optional<Voucher> byId = repository.findById(invalidId);

        assertTrue(byId.isEmpty());
    }

    @DisplayName("등록된 모든 바우처 정보를 조회할 수 있어야 한다.")
    @Test
    void findAll() {
        List<Voucher> vouchers = insert3Voucher();
        Voucher firstVoucher = vouchers.get(0);
        Voucher secondVoucher = vouchers.get(1);
        Voucher thirdVoucher = vouchers.get(2);

        List<Voucher> all = repository.findAll();

        assertThat(all).hasSize(3).contains(firstVoucher,secondVoucher,thirdVoucher);
    }

    @DisplayName("모든 바우처를 삭제할 수 있어야 한다.")
    @Test
    void deleteAll() {
        List<Voucher> vouchers = insert3Voucher();
        Voucher firstVoucher = vouchers.get(0);
        Voucher secondVoucher = vouchers.get(1);
        Voucher thirdVoucher = vouchers.get(2);

        repository.deleteAll();

        assertTrue(repository.findById(firstVoucher.getVoucherId()).isEmpty());
        assertTrue(repository.findById(secondVoucher.getVoucherId()).isEmpty());
        assertTrue(repository.findById(thirdVoucher.getVoucherId()).isEmpty());
    }

    private Voucher createVoucher(UUID id, Voucher.VoucherType type, long discountAmount){
        return new Voucher(id,type,discountAmount);
    }

    private List<Voucher> insert3Voucher(){
        long amount = 5000;
        long percent = 50;
        Voucher firstVoucher = createVoucher(UUID.randomUUID(), Voucher.VoucherType.FIXED,amount);
        Voucher secondVoucher = createVoucher(UUID.randomUUID(), Voucher.VoucherType.PERCENT,percent);
        Voucher thirdVoucher = createVoucher(UUID.randomUUID(), Voucher.VoucherType.PERCENT,percent);

        repository.insert(firstVoucher);
        repository.insert(secondVoucher);
        repository.insert(thirdVoucher);
        return List.of(firstVoucher,secondVoucher,thirdVoucher);
    }

    @Configuration
    @ComponentScan("com.blessing333.springbasic.domain.voucher")
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

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }
    }
}