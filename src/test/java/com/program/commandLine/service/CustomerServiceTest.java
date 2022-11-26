package com.program.commandLine.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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