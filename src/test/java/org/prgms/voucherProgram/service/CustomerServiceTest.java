package org.prgms.voucherProgram.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.voucherProgram.entity.customer.Customer;
import org.prgms.voucherProgram.repository.customer.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @DisplayName("모든 블랙리스트를 반환한다.")
    @Test
    void findBlackList_ReturnBlackCustomers() {
        List<Customer> mockBlackCustomers = Arrays.asList(new Customer(UUID.randomUUID(), "jin"),
            new Customer(UUID.randomUUID(), "hwan"),
            new Customer(UUID.randomUUID(), "pobi"));

        given(customerRepository.findBlackCustomers()).willReturn(mockBlackCustomers);

        assertThat(customerService.findBlackList()).hasSize(3)
            .extracting("name").contains("hwan", "jin", "pobi");
        then(customerRepository).should(times(1)).findBlackCustomers();
    }
}
