package com.prgrms.voucher_manage.domain.customer.repository;

import com.prgrms.voucher_manage.domain.customer.controller.dto.UpdateCustomerReq;
import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static com.prgrms.voucher_manage.domain.customer.entity.CustomerType.BLACK;
import static com.prgrms.voucher_manage.domain.customer.entity.CustomerType.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JdbcCustomerRepository.class)
public class JdbcCustomerRepositoryTest {
    @Autowired
    private JdbcCustomerRepository repository;

    @Test
    @DisplayName("회원을 저장할 수 있다.")
    public void jdbcCustomerRepository_save() {
        //given
        Customer customer = new Customer("와", BLACK);
        //when
        Customer savedCustomer = repository.save(customer);
        //then
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getName()).isEqualTo("와");
        assertThat(savedCustomer.getType()).isEqualTo(BLACK);
    }

    @Test
    @DisplayName("회원 목록을 가져올 수 있다.")
    public void jdbcCustomerRepository_findAll() {
        //given
        Customer customer1 = new Customer("야", BLACK);
        Customer customer2 = new Customer("호", BLACK);
        repository.save(customer1);
        repository.save(customer2);
        //when
        List<Customer> customers = repository.findAll();
        //then
        assertThat(customers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원을 이름으로 조회할 수 있다.")
    public void jdbcCustomerRepository_findByName() {
        //given
        Customer customer = new Customer("와", BLACK);
        repository.save(customer);
        //when
        Customer foundCustomer = repository.findByName("와");

        //then
        assertThat(foundCustomer).isNotNull();
        assertThat(foundCustomer.getName()).isEqualTo("와");
    }

    @Test
    @DisplayName("회원 정보를 업데이트할 수 있다.")
    public void jdbcCustomerRepository_update() {
        //given
        Customer customer = repository.save(new Customer("전", BLACK));
        //when
        UpdateCustomerReq updateDto = new UpdateCustomerReq("후");
        repository.update(new Customer(customer.getId(), updateDto.name(), customer.getType()));
        Customer updatedCustomer = repository.findById(customer.getId());

        //then
        assertThat(updatedCustomer.getName()).isEqualTo(updateDto.name());
    }

    @Test
    @DisplayName("블랙 리스트를 가져올 수 있다.")
    public void jdbcCustomerRepository_findBlackList() {
        //given
        Customer customer1 = new Customer("야", NORMAL);
        Customer customer2 = new Customer("호", BLACK);
        repository.save(customer1);
        repository.save(customer2);
        //when
        List<Customer> customers = repository.findByType(BLACK.getData());
        //then
        assertThat(customers.size()).isEqualTo(1);
        assertThat(customers.get(0).getName()).isEqualTo("호");
    }
}
