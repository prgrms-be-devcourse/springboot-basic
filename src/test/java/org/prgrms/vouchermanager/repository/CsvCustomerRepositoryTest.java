package org.prgrms.vouchermanager.repository;

import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.prgrms.vouchermanager.repository.customer.CsvCustomerRepository;
import org.prgrms.vouchermanager.repository.customer.CustomerRepositroy;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CsvCustomerRepositoryTest {

    CustomerRepositroy repository = new CsvCustomerRepository();


    @Test
    public void findAll(){
        List<Customer> all = repository.findAll();
        String name = all.get(0).getName();
        assertThat(name).isEqualTo("최인준");
    }



}