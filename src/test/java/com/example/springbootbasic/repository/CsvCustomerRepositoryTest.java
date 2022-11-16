package com.example.springbootbasic.repository;

import com.example.springbootbasic.config.AppConfiguration;
import com.example.springbootbasic.domain.customer.Customer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
class CsvCustomerRepositoryTest {

    private final String voucherCsvResource = "src/test/resources/csv/voucher/voucher.csv";
    private final String customerCsvResource = "src/test/resources/csv/customer/customer_blacklist.csv";
    private CsvCustomerRepository customerRepository;

    @BeforeAll
    void init() {
        customerRepository = new CsvCustomerRepository(new AppConfiguration(voucherCsvResource, customerCsvResource));
    }

    @RepeatedTest(100)
    @DisplayName("다수 고객 조회 성공")
    void whenFindAllCustomersThenSuccessTest() {
        // when
        List<Customer> allCustomers = customerRepository.findAllCustomers();

        //then
        assertThat(allCustomers, notNullValue());
    }
}