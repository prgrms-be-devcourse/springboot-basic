package org.prgrms.kdtspringorder.customer.repository.implementation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringorder.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

@SpringJUnitConfig
class CsvCustomerRepositoryTest {

    @Autowired
    CsvCustomerRepository csvCustomerRepository;

    @Test
    @DisplayName("CsvCustomerRepository가 정상적으로 생성 되어야 한다.")
    public void csvCustomerRepositoryCreationTest() {
        assertThat(csvCustomerRepository, notNullValue());
    }

    @Test
    @DisplayName("CsvCustomerRepository는 Custmomer List를 정상적으로 읽어와야 한다.")
    public void bannedCustomerReadTest() {
        List<Customer> bannedCustomerList = csvCustomerRepository.getBannedCustomers();
        assertThat(bannedCustomerList, hasSize(100));
    }

    @Configuration
    static class Config {

        @Bean
        public CsvCustomerRepository csvCustomerRepository() {
            return new CsvCustomerRepository();
        }
    }
}