package com.prgms.vouchermanager.repository.customer;

import com.prgms.vouchermanager.domain.customer.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("save()를 통해 id는 자동 증가하면서, 쿼리가 정상적으로 동작한다. ")
    void saveCustomrSuccess() {

        //given
        Customer customer1 = new Customer(null, "heo", "heo@naver.com", true);
        Customer customer2 = new Customer(null, "koo", "koo@naver.com", false);
        Customer customer3 = new Customer(100L, "koo", "koo@naver.com", false);

        //when
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        //then
        Assertions.assertThat(customer1.getId() + 1).isEqualTo(customer2.getId());
        Assertions.assertThat(customer2.getId() + 1).isEqualTo(customer3.getId());
    }

    @Test
    @DisplayName("update()를 이용해 정보수정을 자유롭게 할수 있다.")
    void updateCustomerSuccess() {

        //given
        Customer customer1 = new Customer(null, "heo", "heo@naver.com", false);
        String newEmail = "new@naver.com";

        //when
        Customer savedCustomer = customerRepository.save(customer1);
        Customer updateCustomer = new Customer(savedCustomer.getId(), savedCustomer.getName(), newEmail, savedCustomer.isBlackList());
        customerRepository.update(updateCustomer);
        Optional<Customer> findCustomer = customerRepository.findById(savedCustomer.getId());

        //then
        Assertions.assertThat(updateCustomer.getName()).isEqualTo(findCustomer.get().getName());
        Assertions.assertThat(updateCustomer.getEmail()).isEqualTo(findCustomer.get().getEmail());
        Assertions.assertThat(updateCustomer.getId()).isEqualTo(findCustomer.get().getId());
        Assertions.assertThat(updateCustomer.isBlackList()).isEqualTo(findCustomer.get().isBlackList());

    }

    @Test
    @DisplayName("findById() 를 통해 Customer를 찾아올 수 있다.")
    void findByIdCustomerSuccess() {

        //given
        Customer customer = new Customer(null, "kim", "heo@naver.com", false);

        //when
        Customer savedCustomer = customerRepository.save(customer);
        Long id = savedCustomer.getId();
        Optional<Customer> findByIdCustomer = customerRepository.findById(id);

        //then
        Assertions.assertThat(customer.getName()).isEqualTo(findByIdCustomer.get().getName());
        Assertions.assertThat(customer.getEmail()).isEqualTo(findByIdCustomer.get().getEmail());
        Assertions.assertThat(customer.getId()).isEqualTo(findByIdCustomer.get().getId());
        Assertions.assertThat(customer.isBlackList()).isEqualTo(findByIdCustomer.get().isBlackList());
    }

    @Test
    @DisplayName("findById 를 실행시 없는 ID를 조회하면, 빈 값이 넘어온다.")
    void findByIdCustomerFail() {

        //given
        Long notExistId = 123456L;

        //when
        Optional<Customer> findById = customerRepository.findById(notExistId);

        //then
        Assertions.assertThat(findById).isEmpty();

    }

    @Test
    @DisplayName("findAll()실행시 성공적으로 모든 목록을 조회한다.")
    void findAllCustomerSuccess() {

        //given
        //when
        List<Customer> all = customerRepository.findAll();

        //then
        Assertions.assertThat(all).isNotNull();

    }

    @Test
    @DisplayName("deleteById()실행 시 customer를 성공적으로 삭제한다.")
    void deleteByCustomerSuccess() {

        //given
        Long id = 1L;
        Customer customer = new Customer(id, "kim", "lee@naver.com", true);
        customerRepository.save(customer);

        //when
        customerRepository.deleteById(id);
        Optional<Customer> findById = customerRepository.findById(id);

        //then
        Assertions.assertThat(findById).isEmpty();
    }

    @Test
    @DisplayName("findBlackList()실행시 성공적으로 blackList를 조회한다.")
    void findBlackListSuccess() {

        //given
        //when
        List<Customer> blackList = customerRepository.findBlackList();

        //then
        Assertions.assertThat(blackList).isNotNull();

    }
}
