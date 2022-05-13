package com.prgrms.kdt.springbootbasic.W2Test.repository;

import com.prgrms.kdt.springbootbasic.W2Test.Config;
import com.prgrms.kdt.springbootbasic.customer.entity.Customer;
import com.prgrms.kdt.springbootbasic.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(Config.class)
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DataSource dataSource;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    Customer customer = new Customer(UUID.randomUUID(), "tester", "tester@gmail.com");

    @BeforeEach
    void setup() {
        jdbcTemplate.update("SET foreign_key_checks = 0;", Collections.emptyMap());
        jdbcTemplate.update("truncate wallet;", Collections.emptyMap());
        jdbcTemplate.update("truncate customers;", Collections.emptyMap());
        jdbcTemplate.update("truncate vouchers;", Collections.emptyMap());
        jdbcTemplate.update("SET foreign_key_checks = 1;", Collections.emptyMap());

        customerRepository.saveCustomer(customer);
    }

    @Test
    public void insertCustomer(){
        var newCustomer = new Customer(UUID.randomUUID(), "tester2", "tester2@gmail.com");
        var insertedCustomer = customerRepository.saveCustomer(newCustomer);
        assertThat(insertedCustomer.get()).as("Customer").isEqualToComparingFieldByField(newCustomer);
    }

    @Test
    public void findByCustomerId(){
        var findCustomer = customerRepository.findCustomerById(customer.getCustomerId());
        assertThat(findCustomer.get()).as("Customer").isEqualToComparingFieldByField(customer);
    }

    @Test
    public void findByCustomerIdNotExist(){
        var findCustomer = customerRepository.findCustomerById(UUID.randomUUID());
        assertThat(findCustomer.isEmpty()).isTrue();
    }

    @Test
    public void findByCustomerEmail(){
        var findCustomer = customerRepository.findCustomerByEmail(customer.getEmail());
        assertThat(findCustomer.get()).as("Customer").isEqualToIgnoringGivenFields(customer,"customerId");
    }

    @Test
    public void findByCustomerEmailNotExist(){
        var findCustomer = customerRepository.findCustomerByEmail("notexit@email.com");
        assertThat(findCustomer.isEmpty()).isTrue();
    }

    @Test
    public void getAllVouchers(){
        List<Customer> assertList = new ArrayList<>();
        assertList.add(customer);

        List<Customer> foundList = customerRepository.getAllCustomers();
        assertThat(foundList).usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(assertList);
    }

    @Test
    public void updateCustomer(){
        customer.setName("changedName");

        var updatedCustomer = customerRepository.updateCustomer(customer);
        assertThat(updatedCustomer.get()).as("Customer").isEqualToComparingFieldByField(customer);
    }

    @Test
    public void deleteCustomer(){
        var deletedResult = customerRepository.deleteCustomer(customer);
        assertThat(deletedResult).isTrue();

        var findResult = customerRepository.findCustomerById(customer.getCustomerId());
        assertThat(findResult.isEmpty()).isTrue();
    }
}