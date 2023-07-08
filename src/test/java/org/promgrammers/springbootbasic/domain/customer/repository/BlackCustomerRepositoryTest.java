package org.promgrammers.springbootbasic.domain.customer.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.model.CustomerType;
import org.promgrammers.springbootbasic.domain.customer.repository.impl.BlackCustomerRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BlackCustomerRepositoryTest {

    private BlackCustomerRepository blackCustomerRepository;

    @BeforeEach
    void setUp() {
        String filePath = "src/main/resources/storage/blacklist.csv";
        blackCustomerRepository = new BlackCustomerRepository(filePath);
    }

    @Test
    @DisplayName("조회 성공 - 파일에 저장된 고객을 타입별로 모두 조회(BLACK)")
    void successFindAllTest() throws Exception {
        //given -> when
        List<Customer> customers = blackCustomerRepository.findAllByCustomerType(CustomerType.BLACK);

        //then
        assertNotNull(customers);
        assertThat(customers.size()).isEqualTo(0);
        assertThat(customers).allMatch(customer -> CustomerType.BLACK.equals(customer.getCustomerType()));
    }

}