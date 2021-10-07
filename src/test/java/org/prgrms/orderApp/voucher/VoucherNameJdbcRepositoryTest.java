package org.prgrms.orderApp.voucher;

import com.zaxxer.hikari.HikariDataSource;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
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
                    .url("jdbc:mysql://localhost:3306/order_mgmt_w3")
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
        voucherNameJdbcRepository.deleteAll();
    }





    @Test
    @DisplayName("Fixed 바우처 타입을 정보를 저장할 수 있다.")
    void insertFixedAmountDataTest() throws IOException {
        // Given
        var fixedVoucherDataToInsert = new FixedAmountVoucher(UUID.randomUUID(), 20);

        // When
        var insertResult = voucherNameJdbcRepository.insert(fixedVoucherDataToInsert);

        // Then
        assertThat(insertResult, is(1)); // 데이터베이스에 저장이 성공할 경우, 1이 반환됨.

    }

    @Test
    @DisplayName("Percent 바우처 타입의 저장할 수 있다.")
    void insertPercentAmountDataTest() throws IOException {
        //Given
        var percentVoucherDataToInsert = new PercentDiscountVoucher(UUID.randomUUID(), 50);

        //When
        var insertResult = voucherNameJdbcRepository.insert(percentVoucherDataToInsert);

        //Then
        assertThat(insertResult, is(1)); // 데이터베이스에 저장이 성공할 경우, 1이 반환됨.
    }

    @Test
    @DisplayName("모든 고객 정보를 추출할 수 있다.")
    void findAllTest() throws IOException, ParseException {
        //Given
        voucherNameJdbcRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 50));
        voucherNameJdbcRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 70));
        voucherNameJdbcRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 2000));
        voucherNameJdbcRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10000));

        //When
        var findData = voucherNameJdbcRepository.findAll();

        //Then
        assertThat(findData.isEmpty(), is(false));
    }

    @Test
    @DisplayName("바우처 정보를 저장할 수 있다. ")
    void insertTest() throws IOException, ParseException {
        //Given
        var fixedVoucherId = UUID.randomUUID();
        var percentVoucherId = UUID.randomUUID();

        Voucher fixedDataToInsert = new FixedAmountVoucher(fixedVoucherId, 100L);
        Voucher percentDataToInsert = new PercentDiscountVoucher(percentVoucherId, 30L);

        //When
        voucherNameJdbcRepository.insert(fixedDataToInsert);
        voucherNameJdbcRepository.insert(percentDataToInsert);

        //Then
        assertThat(voucherNameJdbcRepository.findAll().isEmpty(), is(false));
        assertThat(voucherNameJdbcRepository.findAll().size(), is(2));
        assertThat(voucherNameJdbcRepository.findById(fixedVoucherId).get().getVoucherAmount(), is(100L));
        assertThat(voucherNameJdbcRepository.findById(percentVoucherId).get().getVoucherAmount(), is(30L));
    }

    @Test
    @DisplayName("동일한 바우터 아이디를 가지지 않는다.")
    void NotDuplicateTest() throws IOException {
        // Given
        var voucherId = UUID.randomUUID();
        Voucher fixedDataToInsert = new FixedAmountVoucher(voucherId, 100L);
        Voucher percentDataToInsert = new PercentDiscountVoucher(voucherId, 30L);

        //When and Then
        voucherNameJdbcRepository.insert(fixedDataToInsert);
        assertThrows(DuplicateKeyException.class, () -> {
            voucherNameJdbcRepository.insert(percentDataToInsert);
        });
        assertThrows(DuplicateKeyException.class, () -> {
            voucherNameJdbcRepository.insert(fixedDataToInsert);
        });
    }

    @Test
    @DisplayName("바우처 정보를 변경할 수 있다.")
    void updateTest() throws IOException {
        //Given
        var fixedVoucherId = UUID.randomUUID();
        var percentVoucherId = UUID.randomUUID();

        Voucher fixedDataToInsert = new FixedAmountVoucher(fixedVoucherId, 100L);
        Voucher percentDataToInsert = new PercentDiscountVoucher(percentVoucherId, 30L);

        voucherNameJdbcRepository.insert(fixedDataToInsert);
        voucherNameJdbcRepository.insert(percentDataToInsert);

        //When
        var changeFixedVoucherAmount = 90L;
        var changePercentVoucherAmount = 50L;
        voucherNameJdbcRepository.update(new FixedAmountVoucher(fixedVoucherId, changeFixedVoucherAmount));
        voucherNameJdbcRepository.update(new PercentDiscountVoucher(percentVoucherId, changePercentVoucherAmount));

        //Then
        var updatedFixedVoucherAmount = voucherNameJdbcRepository
                .findById(fixedVoucherId)
                .get()
                .getVoucherAmount();
        var updatedPercentVoucherAmount = voucherNameJdbcRepository
                .findById(percentVoucherId)
                .get()
                .getVoucherAmount();

        assertThat(updatedFixedVoucherAmount, is(changeFixedVoucherAmount));
        assertThat(updatedPercentVoucherAmount, is(changePercentVoucherAmount));

    }

    @Test
    @DisplayName("voucherId를 이용하여, 바우처 정보를 추출할 수 있다. ")
    void findByIdTest() throws IOException {
        //Given
        var fixedVoucherId = UUID.randomUUID();
        var percentVoucherId = UUID.randomUUID();

        Voucher fixedDataToInsert = new FixedAmountVoucher(fixedVoucherId, 100L);
        Voucher percentDataToInsert = new PercentDiscountVoucher(percentVoucherId, 30L);

        voucherNameJdbcRepository.insert(fixedDataToInsert);
        voucherNameJdbcRepository.insert(percentDataToInsert);

        //When
        var fixedVoucherDataFindById = voucherNameJdbcRepository.findById(fixedVoucherId).get();
        var percentVoucherDataFindById = voucherNameJdbcRepository.findById(percentVoucherId).get();

        //Then
        assertThat(fixedVoucherDataFindById.getVoucherId(), is(fixedVoucherId));
        assertThat(percentVoucherDataFindById.getVoucherId(), is(percentVoucherId));

    }

    @Test
    @DisplayName("저장된 모든 데이터의 수를 파악할 수 있다.")
    void countTest() throws IOException {
        //Given
        voucherNameJdbcRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 50));
        voucherNameJdbcRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 70));
        voucherNameJdbcRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 2000));
        voucherNameJdbcRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10000));

        //When
        var count = voucherNameJdbcRepository.count();

        //Then
        assertThat(count, is(4));
    }

}