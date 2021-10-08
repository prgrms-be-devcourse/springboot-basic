package org.prgrms.orderApp.voucher;

import com.zaxxer.hikari.HikariDataSource;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.prgrms.orderApp.customer.CustomerNameJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
class VoucherNameJdbcRepositoryTest {

    private static Logger logger = LoggerFactory.getLogger(VoucherNameJdbcRepositoryTest.class);


    @Configuration
    @EnableTransactionManagement
    @ActiveProfiles("local")
    @ComponentScan( basePackages = {"org.prgrms.orderApp.voucher"})
    static class Config{
        @Bean
        public DataSource dataSource(){

            var dataSource =  DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/order_mgmt")
                    .username("root")
                    .password("root1234!")
                    .type(HikariDataSource.class)
                    .build();
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
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource){
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager){
            return new TransactionTemplate(platformTransactionManager);
        }


    }

    @Autowired
    VoucherNameJdbcRepository voucherNameJdbcRepository;

    @Autowired
    DataSource dataSource;


    @AfterEach
    void deleteAll(){
        voucherNameJdbcRepository.findAll()
                .stream().forEach(v -> voucherNameJdbcRepository.deleteById(v.getVoucherId()));

    }

    @Test
    @DisplayName("바우처 정보를 저장할 수 있다.")
    void insertTest() throws IOException {
        //Given
        var voucher = new Voucher("VOUCHER-1234", 20, "Fixed", LocalDateTime.now().toLocalDate());

        //When
        var insertResult = voucherNameJdbcRepository.insert(voucher);

        //Then
        assertThat(insertResult, is(1));
    }



    @Test
    @DisplayName("모든 바우처 정보를 추출할 수 있다.")
    void findAllTest() throws IOException, ParseException {
        //Given
        voucherNameJdbcRepository.insert(new Voucher("VOUCHER-1234", 20, "Fixed", LocalDateTime.now().toLocalDate()));
        voucherNameJdbcRepository.insert(new Voucher("VOUCHER-1223", 60, "Percent", LocalDateTime.now().toLocalDate()));
        voucherNameJdbcRepository.insert(new Voucher("VOUCHER-2443", 2000, "Fixed", LocalDateTime.now().toLocalDate()));

        //When
        var findData = voucherNameJdbcRepository.findAll();

        //Then
        assertThat(findData.isEmpty(), is(false));
    }

    @Test
    @DisplayName("바우처 아이디를 이용하여 바우처 정보를 추출할 수 있다.")
    void findByIdTest(){
        //Given
        var voucher1 = new Voucher("VOUCHER-1234", 20, "Fixed", LocalDateTime.now().toLocalDate());
        var voucher2 = new Voucher("VOUCHER-1223", 60, "Percent", LocalDateTime.now().toLocalDate());
        voucherNameJdbcRepository.insert(voucher1);
        voucherNameJdbcRepository.insert(voucher2);

        //When
        var dataSelected1 = voucherNameJdbcRepository.findById(voucher1.getVoucherId()).get();
        var dataSelected2 = voucherNameJdbcRepository.findById(voucher2.getVoucherId()).get();

        //Then
        assertThat(dataSelected1.getName(), is(voucher1.getName()));
        assertThat(dataSelected1.getVoucherType(), is(voucher1.getVoucherType()));
        assertThat(dataSelected2.getName(), is(voucher2.getName()));
        assertThat(dataSelected2.getVoucherType(), is(voucher2.getVoucherType()));

    }

    @Test
    @DisplayName("바우처 타입을 이용하여, 바우처 정보를 추출할 수 있다.")
    void findAllByType(){
        //Given
        var voucher1 = new Voucher("VOUCHER-1234", 20, "Fixed", LocalDateTime.now().toLocalDate());
        var voucher2 = new Voucher("VOUCHER-1223", 60, "percent", LocalDateTime.now().toLocalDate());
        voucherNameJdbcRepository.insert(voucher1);
        voucherNameJdbcRepository.insert(voucher2);

        //When
        var fixedVouchersSelected = voucherNameJdbcRepository.findAllByType("Fixed");
        var percentVoucherSelected = voucherNameJdbcRepository.findAllByType("percent");

        //Then
        assertThat(fixedVouchersSelected.stream().allMatch(v -> v.getVoucherType().equals("Fixed")), is(true) );
        assertThat(percentVoucherSelected.stream().allMatch(v -> v.getVoucherType().equals("percent")), is(true) );
    }

    @Test
    @DisplayName("바우처 정보 생성 날짜를 설정하여, 바우처 정보를 추출할 수 있다.")
    void findAllByCreateDate(){
        //Given
        var voucher1 = new Voucher("VOUCHER-1234", 20, "Fixed", LocalDateTime.now().toLocalDate());
        var voucher2 = new Voucher("VOUCHER-1223", 60, "percent", LocalDateTime.now().toLocalDate());
        voucherNameJdbcRepository.insert(voucher1);
        voucherNameJdbcRepository.insert(voucher2);

        //When
        var voucherByCreatedDate1 = voucherNameJdbcRepository.findAllByCreatedDate(LocalDateTime.now().minusDays(1), LocalDateTime.now());
        var voucherByCreatedDate2 = voucherNameJdbcRepository.findAllByCreatedDate(LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1));

        //Then
        assertThat(voucherByCreatedDate1.size(), greaterThan(0));
        assertThat(voucherByCreatedDate2.size(), is(0));
    }

}