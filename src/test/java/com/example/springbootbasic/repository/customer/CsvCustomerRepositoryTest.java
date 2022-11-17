package com.example.springbootbasic.repository.customer;

import com.example.springbootbasic.config.AppConfig;
import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.repository.customer.CsvCustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.example.springbootbasic.domain.customer.CustomerStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
class CsvCustomerRepositoryTest {

    private final String voucherCsvResource = "src/test/resources/csv/voucher/voucher.csv";
    private final String customerCsvResource = "src/test/resources/csv/customer/customer_blacklist.csv";
    private final CsvCustomerRepository customerRepository;
    private final AppConfig appConfiguration;

    public CsvCustomerRepositoryTest() {
        this.appConfiguration = new AppConfig(voucherCsvResource, customerCsvResource);
        this.customerRepository = new CsvCustomerRepository(appConfiguration);
    }

    @RepeatedTest(100)
    @DisplayName("다수 고객 조회 성공")
    void whenFindAllCustomersThenSuccessTest() {
        // when
        List<Customer> allCustomers = customerRepository.findAllCustomers();

        //then
        assertThat(allCustomers, notNullValue());
    }

    @RepeatedTest(100)
    @DisplayName("다수 블랙리스트 고객 조회 성공")
    void whenFindAllBlackCustomersThenSuccessTest() {
        // when
        List<Customer> allBlackCustomers = customerRepository.findCustomersByStatus(BLACK);

        //then
        boolean isAllBlackCustomers = allBlackCustomers.stream().allMatch(Customer::isBlack);
        assertThat(isAllBlackCustomers, is(true));
    }
}