package org.prgrms.springbasic.repository.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbasic.domain.customer.Customer;
import org.springframework.test.context.ActiveProfiles;

import static java.util.UUID.randomUUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

@ActiveProfiles("dev")
class FileCustomerRepositoryTest {

    FileCustomerRepository repository = new FileCustomerRepository();
    Customer customer = Customer.blackCustomer(randomUUID(), "black-user");

    @BeforeEach
    void init() {
        repository.deleteCustomers();
    }

    @Test
    @DisplayName("블랙회원을 저장하면 레포지토리의 사이즈가 증가해야한다.")
    void save() {
        repository.save(customer);

        assertThat(repository.countData(), is(1));
    }

    @Test
    @DisplayName("저장한 블랙훠은 아이디로 찾은 객체는 비어있지 않고 저장한 객체와 같아야 한다.")
    void findById() {
        repository.save(customer);
        var customerId = customer.getCustomerId();

        var foundCustomer = repository.findByCustomerId(customerId);

        assertThat(foundCustomer.isEmpty(), is(false));
        assertThat(foundCustomer.get(), is(customer));
    }

    @Test
    @DisplayName("블랙회원을 저장 후 모두 조회를 하면 저장한 개수와 리스트의 사이즈가 같아야 하고 리스트는 저장한 바우처를 모두 포함해야한다.")
    void findAll() {
        Customer newCustomer1 = Customer.blackCustomer(randomUUID(), "black-user1");
        Customer newCustomer2 = Customer.blackCustomer(randomUUID(), "black-user2");
        Customer newCustomer3 = Customer.blackCustomer(randomUUID(), "black-user3");
        Customer newCustomer4 = Customer.blackCustomer(randomUUID(), "black-user4");
        Customer newCustomer5 = Customer.blackCustomer(randomUUID(), "black-user5");

        repository.save(newCustomer1);
        repository.save(newCustomer2);
        repository.save(newCustomer3);
        repository.save(newCustomer4);
        repository.save(newCustomer5);

        var customers = repository.findCustomers();

        assertThat(customers.size(), is(5));
        assertThat(customers, containsInAnyOrder(newCustomer1, newCustomer2, newCustomer3, newCustomer4, newCustomer5));
    }

    @Test
    @DisplayName("레포지토리를 클리어하면 레포지토리 사이즈가 0이되어야 한다.")
    void clear() {
        repository.save(customer);
        assertThat(repository.countData(), is(1));

        repository.deleteCustomers();
        assertThat(repository.countData(), is(0));
    }
}