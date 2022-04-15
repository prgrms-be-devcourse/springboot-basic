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
import org.prgms.voucherProgram.repository.customer.BlackListRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private BlackListRepository fileCustomerRepository;

    @InjectMocks
    private CustomerService customerService;

    @DisplayName("모든 블랙리스트를 반환한다.")
    @Test
    void findBlackList_ReturnBlackCustomers() {
        List<Customer> mockBlackCustomers = Arrays.asList(new Customer(UUID.randomUUID(), "jin", "jin@gmail.com", null,
                null),
            new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", null, null),
            new Customer(UUID.randomUUID(), "pobi", "pobi@gmail.com", null, null));

        given(fileCustomerRepository.findBlackCustomers()).willReturn(mockBlackCustomers);

        assertThat(customerService.findBlackList()).hasSize(3)
            .extracting("name", "email").contains(tuple("hwan", "hwan@gmail.com"),
                tuple("pobi", "pobi@gmail.com"),
                tuple("jin", "jin@gmail.com"));
        then(fileCustomerRepository).should(times(1)).findBlackCustomers();
    }
}
