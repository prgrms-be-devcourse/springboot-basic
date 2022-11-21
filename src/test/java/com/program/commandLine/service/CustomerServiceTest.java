package com.program.commandLine.service;

import com.program.commandLine.customer.CustomerFactory;
import com.program.commandLine.customer.CustomerType;
import com.program.commandLine.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CustomerServiceTest {

    @DisplayName("blacklist가 정상 호출된다.")
    @Test
    public void testGetBlackList(){
        // Give
        var customerServiceMock = mock(CustomerService.class);
        // When
        customerServiceMock.getBlackListCustomers();
        // Then
        verify(customerServiceMock).getBlackListCustomers();
    }

}