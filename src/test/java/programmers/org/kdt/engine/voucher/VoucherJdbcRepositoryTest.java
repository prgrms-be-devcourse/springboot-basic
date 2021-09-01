package programmers.org.kdt.engine.voucher;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import programmers.org.kdt.engine.voucher.repository.JdbcVoucherRepository;
import programmers.org.kdt.engine.voucher.type.Voucher;
import programmers.org.kdt.engine.voucher.type.VoucherStatus;

@SpringJUnitConfig
@ActiveProfiles("local")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class VoucherJdbcRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepositoryTest.class);

    @Configuration
    @ComponentScan(basePackages = {"programmers.org.kdt.engine.voucher"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/test-order_mgmt")
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

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    DataSource dataSource;

    @Autowired
    VoucherService voucherService;

    Voucher newVoucher;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        newVoucher = new Voucher(UUID.randomUUID(), 10, VoucherStatus.FIXEDAMOUNTVOUCHER);
        MysqldConfig mysqldConfig = aMysqldConfig(v8_0_11)
            .withCharset(UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
            .addSchema("test-order_mgmt", classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @DisplayName("바우처 추가")
    @Order(2)
    public void testInsert() {
        try {
            jdbcVoucherRepository.insert(newVoucher);
        } catch (BadSqlGrammarException e) {
            logger.error("Got BadSqlGrammarException error code -> {}", e.getSQLException().getErrorCode(), e);
        }

        var retrievedCustomer = jdbcVoucherRepository.findById(newVoucher.getVoucherId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @DisplayName("전체 바우처 조회")
    @Order(3)
    public void testFindAll() {
        var voucher = jdbcVoucherRepository.getAllEntry();
        assertThat(voucher.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("아이디로 바우처 조회")
    public void testFindById() {
        var customers = jdbcVoucherRepository.findById(newVoucher.getVoucherId());
        assertThat(customers.isEmpty(), is(false));

        var unknown = jdbcVoucherRepository.findById(UUID.randomUUID());
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(5)
    @DisplayName("바우처 수정")
    public void testUpdate() {
        newVoucher.changeValue(20);
        jdbcVoucherRepository.update(newVoucher);

        Set<Entry<UUID, Voucher>> entrySet = jdbcVoucherRepository.getAllEntry();
        List<Voucher> all = new ArrayList<Voucher>();
        for(Entry<UUID, Voucher> i : entrySet) {
            all.add(i.getValue());
        }
        assertThat(all, hasSize(1));
        assertThat(all, everyItem(samePropertyValuesAs(newVoucher)));

        var retrievedVoucher = jdbcVoucherRepository.findById(newVoucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @Order(6)
    @DisplayName("바우처 삭제")
    public void testDelete() {
        jdbcVoucherRepository.deleteAll();
        var voucher = jdbcVoucherRepository.getAllEntry();
        assertThat(voucher.isEmpty(), is(true));
    }
}