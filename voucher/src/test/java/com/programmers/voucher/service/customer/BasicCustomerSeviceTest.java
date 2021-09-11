package com.programmers.voucher.service.customer;

import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.repository.customer.CustomerRepository;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class BasicCustomerSeviceTest {

    CustomerRepository customerRepository = mock(CustomerRepository.class);
    VoucherRepository voucherRepository = mock(VoucherRepository.class);
    CustomerService basicCustomerService = new BasicCustomerService(customerRepository, voucherRepository);


    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate.execute("TRUNCATE customers");
    }

    @Test
    @DisplayName("Customer Creation Test")
    void createCustomer() {
        String username = "usernameCreate";
        String alias = "aliasCreate";
        final Customer customerCreationParam = new Customer(username, alias);
        when(customerRepository.save(customerCreationParam)).thenReturn(new Customer(1L, username, alias, false, LocalDate.now()));

        final Customer customer = basicCustomerService.create(username, alias);
        assertEquals(username, customer.getUsername());
        assertEquals(alias, customer.getAlias());

        verify(customerRepository).save(customerCreationParam);
    }

    @Test
    @DisplayName("Customer Read Test")
    void readCustomer() {
        long id = 1234;
        String username = "usernameRead";
        String alias = "aliasRead";
        when(customerRepository.findById(id)).thenReturn(Optional.of(new Customer(id, username, alias, false, LocalDate.now())));

        final Optional<Customer> byId = basicCustomerService.findById(1234);
        assertTrue(byId.isPresent());
        assertEquals(username, byId.get().getUsername());
        assertEquals(alias, byId.get().getAlias());

        verify(customerRepository).findById(id);
    }

    @Test
    @DisplayName("Customer List Test")
    void listAllCustomers() {
        String username = "usernameList";
        String alias = "usernameAlias";
        List<Customer> list = new ArrayList<>(10);
        for(long i=0;i<10;i++) {
            list.add(new Customer(i+1, username+i, alias+i, false, LocalDate.now()));
        }

        when(customerRepository.listAll()).thenReturn(list);

        final List<Customer> customers = basicCustomerService.listAll();
        assertEquals(10, customers.size());
        for(int i=0;i<10;i++) {
            assertEquals(list.get(i).getUsername(), customers.get(i).getUsername());
            assertEquals(list.get(i).getAlias(), customers.get(i).getAlias());
        }

        verify(customerRepository).listAll();
    }
}
