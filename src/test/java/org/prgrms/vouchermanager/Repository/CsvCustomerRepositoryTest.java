package org.prgrms.vouchermanager.Repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.customer.Customer;

import java.util.List;
import java.util.Map;

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