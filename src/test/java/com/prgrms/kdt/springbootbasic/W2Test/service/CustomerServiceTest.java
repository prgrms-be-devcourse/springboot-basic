package com.prgrms.kdt.springbootbasic.W2Test.service;

import com.prgrms.kdt.springbootbasic.entity.Customer;
import com.prgrms.kdt.springbootbasic.repository.CustomerRepository;
import com.prgrms.kdt.springbootbasic.service.CustomerService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    CustomerRepository customerRepository = mock(CustomerRepository.class);

    CustomerService customerService = new CustomerService(customerRepository);

    Customer customer = new Customer(UUID.randomUUID(), "tester","tester@gmail.com");

    @Test
    void createCustomer() {
        var createdCustomer = customerService.createCustomer(customer.getName(),customer.getEmail());

        assertThat(createdCustomer).as("Customer").isEqualToIgnoringGivenFields(customer,"customerId");
    }

    @Test
    void checkDuplicationExist(){
        //Given
        when(customerRepository.findCustomerByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        //When
        var duplicationResult = customerService.checkDuplication(customer);

        //Then
        assertThat(duplicationResult).isTrue();
    }

    @Test
    void checkDuplicationNotExist(){
        //Given
        var newCustomer = new Customer(UUID.randomUUID(),"tester","tester@email.com");
        when(customerRepository.findCustomerByEmail(newCustomer.getEmail())).thenReturn(Optional.empty());

        //When
        var duplicationResult = customerService.checkDuplication(newCustomer);

        //Then
        assertThat(duplicationResult).isFalse();
    }

    @Test
    void saveCustomerNotDuplicated() {
        //Given
        var newCustomer = new Customer(UUID.randomUUID(),"tester","tester@email.com");
        when(customerRepository.findCustomerByEmail(newCustomer.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.saveCustomer(newCustomer)).thenReturn(Optional.of(newCustomer));

        //When
        var savedCustomer = customerService.saveCustomer(newCustomer);

        //Then
        verify(customerRepository).saveCustomer(newCustomer);
        assertThat(savedCustomer.get()).as("Customer").isEqualToComparingFieldByField(newCustomer);
    }

    @Test
    void saveCustomerDuplicated(){
        //Given
        when(customerRepository.findCustomerByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        //When
        var savedCustomer = customerService.saveCustomer(customer);

        //Then
        assertThat(savedCustomer.isEmpty()).isTrue();
    }

    @Test
    void findCustomerById() {
        //Given
        when(customerRepository.findCustomerById(customer.getCustomerId())).thenReturn(Optional.of(customer));

        //When
        var foundCustomer = customerService.findCustomerById(customer.getCustomerId());

        //Then
        assertThat(foundCustomer.get()).as("Customer").isEqualToComparingFieldByField(customer);
    }

    @Test
    void getAllCustomers() {
        //Given
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        customerList.add(new Customer(UUID.randomUUID(), "tester2", "tester2@gmail.com"));
        when(customerRepository.getAllCustomers()).thenReturn(customerList);

        //When
        var returnCustomerList = customerService.getAllCustomers();

        //Then
        assertThat(returnCustomerList)
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(customerList);
    }

    @Test
    void updateCustomerExist(){
        //Given
        when(customerRepository.findCustomerById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        Customer newCustomer = new Customer(customer.getCustomerId(), "ChangedName", customer.getEmail());
        when(customerRepository.updateCustomer(newCustomer)).thenReturn(Optional.of(newCustomer));

        //When
        var updatedResult = customerService.updateCustomer(newCustomer);

        //Then
        assertThat(updatedResult.get()).as("Customer").isEqualToComparingFieldByField(newCustomer);
    }

    @Test
    void updateCustomerNotExist(){
        //Given
        Customer newCustomer = new Customer(UUID.randomUUID(), "newCustomer", "newCustomer@gmail.com");
        when(customerRepository.findCustomerById(newCustomer.getCustomerId())).thenReturn(Optional.empty());

        //When
        var updatedResult = customerService.updateCustomer(newCustomer);

        //Then
        assertThat(updatedResult.isEmpty()).isTrue();
    }

    @Test
    void deleteCustomerExist(){
        //Given
        when(customerRepository.findCustomerById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        when(customerRepository.deleteCustomer(customer)).thenReturn(true);

        //When
        var deletedResult = customerService.deleteCustomer(customer);

        //Then
        assertThat(deletedResult).isTrue();
    }

    @Test
    void deleteCustomerNotExist(){
        //Given
        Customer newCustomer = new Customer(UUID.randomUUID(), "newCustomer", "newCustomer@gmail.com");
        when(customerRepository.findCustomerById(newCustomer.getCustomerId())).thenReturn(Optional.empty());

        //When
        var deletedResult = customerService.deleteCustomer(newCustomer);

        //Then
        assertThat(deletedResult).isFalse();
    }
}