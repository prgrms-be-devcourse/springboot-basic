package com.prgrms.vouchermanagement.core.customer.service;

import com.prgrms.vouchermanagement.core.customer.dto.CustomerDto;
import com.prgrms.vouchermanagement.core.customer.repository.CustomerRepository;
import com.prgrms.vouchermanagement.core.customer.repository.jdbc.JdbcCustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("prod")
@ContextConfiguration(classes = {CustomerService.class, JdbcCustomerRepository.class, CustomerServiceIntegrationTest.Config.class})
public class CustomerServiceIntegrationTest {

    @Configuration
    static class Config {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(H2)
                    .setScriptEncoding("UTF-8")
                    .ignoreFailedDrops(true)
                    .addScript("sql/customer-init.sql")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }


    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @DisplayName("고객을 추가할 수 있다.")
    @Test
    void testCreate() {
        // given
        CustomerDto customerDto = new CustomerDto("sujin", "sujin@email.com");

        // when
        CustomerDto findCustomerDto = customerService.create(customerDto);

        // then
        assertThat(findCustomerDto).isEqualTo(customerDto);
    }

    @DisplayName("id로 고객을 조회할 수 있다.")
    @Test
    void testFindById() {
        // given
        CustomerDto customerDto = new CustomerDto("test", "test@email.com");
        CustomerDto savedCustomerDto = customerService.create(customerDto);

        // when
        CustomerDto findCustomerDto = customerService.findById(savedCustomerDto.getId());

        // then
        assertThat(findCustomerDto).isEqualTo(customerDto);
    }

    @DisplayName("전체 고객을 조회할 수 있다.")
    @Test
    void testFindAll() {
        // given

        CustomerDto customerDto1 = new CustomerDto("customer1", "customer1@email.com");
        CustomerDto customerDto2 = new CustomerDto("customer2", "customer2@email.com");
        customerService.create(customerDto1);
        customerService.create(customerDto2);

        // when
        List<CustomerDto> customerDtoList = customerService.findAll();

        // then
        assertThat(customerDtoList.containsAll(List.of(customerDto1, customerDto2)));
    }
}
