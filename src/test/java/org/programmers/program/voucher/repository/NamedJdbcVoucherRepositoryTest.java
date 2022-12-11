package org.programmers.program.voucher.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.programmers.program.voucher.model.FixedAmountVoucher;
import org.programmers.program.voucher.model.PercentDiscountVoucher;
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

import java.util.UUID;


import static org.assertj.core.api.Assertions.*;


@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NamedJdbcVoucherRepositoryTest {
    @Configuration
    @ComponentScan(basePackages = "org.programmers.program.voucher")
    static class Config{
        @Bean
        public DataSource dataSource(){
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/vouchers")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
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
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource){
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager){
            return new TransactionTemplate(transactionManager);
        }
    }

    @Autowired
    NamedJdbcVoucherRepository repository;

    @Autowired
    DataSource dataSource;


    @Test
    @Order(1)
    @DisplayName("히카리 DB 소스인지 확인")
    void testHikariConnectionPool(){
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @Order(2)
    @DisplayName("바우처 삽입 테스트")
    void insertTest(){
        var fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 50L);
        repository.save(fixedVoucher);

        var retrievedVoucher = repository.findById(fixedVoucher.getId());

        assertThat(retrievedVoucher).isNotEmpty();
        assertThat(fixedVoucher.getId()).isEqualTo(retrievedVoucher.get().getId());
    }

    @Test
    @Order(3)
    @DisplayName("데이터가 들어간 개수 구하기")
    void countTest(){
        repository.save(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        repository.save(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        repository.save(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        assertThat(repository.findAll()).hasSize(4);
        assertThat(repository.countAll()).isEqualTo(4);
    }

    @Test
    @Order(4)
    @DisplayName("수정 테스트")
    void updateTest(){
        var id = UUID.randomUUID();
        var v1 = new PercentDiscountVoucher(id, 90L);
        repository.save(v1);

        var v2 = new PercentDiscountVoucher(id, 20L);
        int updateStatus = repository.update(v2);

        assertThat(updateStatus).isOne();
        assertThat(repository.findById(id).get().getDiscountAmount()).isEqualTo(20L);
    }

    @Test
    @Order(5)
    @DisplayName("삭제 테스트")
    void deleteTest(){
        repository.deleteAll();

        assertThat(repository.countAll()).isZero();
    }
}