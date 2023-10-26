package org.programmers.springorder.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.voucher.model.Voucher;
import org.programmers.springorder.voucher.model.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
class JdbcVoucherRepositoryTest {
    static EmbeddedMysql embeddedMysql;

    @Configuration
    static class Config{
        @Bean
        public DataSource dataSource(){
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test_voucher")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate){
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public VoucherRepository voucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
            return new JdbcVoucherRepository(namedParameterJdbcTemplate);
        }

    }

    @Autowired
    VoucherRepository voucherRepository;

    @BeforeAll
    static void setUp(){
        MysqldConfig mysqldConfig = aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test_voucher", classPathScript("/schema.sql"))
                .start();
    }

    @AfterEach
    void clear(){
        embeddedMysql.executeScripts("test_voucher", List.of(() ->"delete from vouchers; delete from customers;"));
    }

    @Test
    @DisplayName("voucher 생성 테스트")
    public void makeVoucherTest(){
        UUID uuid = UUID.randomUUID();
        Voucher voucher = Voucher.toVoucher(uuid, 100, VoucherType.FIXED );

        Voucher save = voucherRepository.save(voucher);
        Voucher foundVoucher = voucherRepository.findById(uuid).get();

        assertThat(voucher).isEqualTo(save);
        assertThat(voucher).isEqualTo(foundVoucher);
    }

    @Test
    @DisplayName("voucher 전체 조회 테스트")
    public void getAllVoucherTest(){
        Voucher voucher1 = Voucher.toVoucher(UUID.randomUUID(), 100, VoucherType.FIXED );
        Voucher voucher2 = Voucher.toVoucher(UUID.randomUUID(), 100, VoucherType.FIXED );
        Voucher voucher3 = Voucher.toVoucher(UUID.randomUUID(), 100, VoucherType.FIXED );

        Voucher save1 = voucherRepository.save(voucher1);
        Voucher save2 = voucherRepository.save(voucher2);
        Voucher save3 = voucherRepository.save(voucher3);

        List<Voucher> savedvoucherList = Arrays.asList(save1, save2, save3);
        List<Voucher> vouchers = voucherRepository.findAll();

        assertThat(vouchers).hasSize(3);
        assertThat(vouchers).containsAll(savedvoucherList);
    }

    @Test
    @DisplayName("voucher id로 조회 테스트")
    public void getVoucherByIdTest(){
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();

        Voucher voucher1 = Voucher.toVoucher(uuid1, 100, VoucherType.FIXED );
        Voucher voucher2 = Voucher.toVoucher(uuid2, 100, VoucherType.FIXED );
        Voucher voucher3 = Voucher.toVoucher(uuid3, 100, VoucherType.FIXED );

        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);


        Voucher findVoucher1 = voucherRepository.findById(uuid1).get();
        Voucher findVoucher2 = voucherRepository.findById(uuid2).get();
        Voucher findVoucher3 = voucherRepository.findById(uuid3).get();

        assertThat(voucher1).isEqualTo(findVoucher1);
        assertThat(voucher2).isEqualTo(findVoucher2);
        assertThat(voucher3).isEqualTo(findVoucher3);
    }
}