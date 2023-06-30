package org.promgrammers.springbootbasic.domain.customer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomerResponse;
import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomersResponse;
import org.promgrammers.springbootbasic.domain.customer.model.CustomerType;
import org.promgrammers.springbootbasic.domain.customer.repository.impl.BlackCustomerRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BlackCustomerServiceTest {

    private String blackListStoragePath = "src/main/resources/storage/blacklist.csv";
    private BlackCustomerService blackCustomerService;
    private BlackCustomerRepository blackCustomerRepository;

    @BeforeEach
    void setUp() {
        blackCustomerRepository = new BlackCustomerRepository(blackListStoragePath);
        blackCustomerService = new BlackCustomerService(blackCustomerRepository);
    }

    @Test
    @DisplayName("모든 블랙리스트 고객 조회 성공")
    void successFindAllTest() throws Exception {

        //given -> when
        CustomersResponse customersResponse = blackCustomerService.findAllByCustomerType(CustomerType.BLACK);
        List<CustomerResponse> customers = customersResponse.customers();

        //then
        assertNotNull(customers);
        assertThat(customers.size()).isEqualTo(3);
        assertThat(customers).allMatch(customer -> CustomerType.BLACK.equals(customer.customerType()));
    }
}