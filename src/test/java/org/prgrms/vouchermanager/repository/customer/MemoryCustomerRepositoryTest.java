package org.prgrms.vouchermanager.repository.customer;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.customer.Customer;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class MemoryCustomerRepositoryTest {

    CustomerRepositroy repository = new MemoryCustomerRepository();

    @Test
    @DisplayName("id로 customer를 조회할 수 있다")
    void findVyId() {
        Customer jun = repository.save(new Customer(UUID.randomUUID(), "jun", "asd@", true));

        Optional<Customer> byId = repository.findById(jun.getCustomerId());

        assertThat(jun).isEqualTo(byId.get());
    }

    @Test
    @DisplayName("id로 customer를 삭제할 수 있다")
    void deleteById() {
        Customer jun = repository.save(new Customer(UUID.randomUUID(), "jun", "asd@", true));

        Optional<Customer> customer = repository.deleteById(jun.getCustomerId());

        assertThat(customer).isNotEmpty();
    }

    @Test
    @DisplayName("없는 고객 삭제 시 false가 반환된다")
    void deleteByNotExistId(){
        Optional<Customer> customer = repository.deleteById(UUID.randomUUID());

        assertThat(customer).isEmpty();
    }
}