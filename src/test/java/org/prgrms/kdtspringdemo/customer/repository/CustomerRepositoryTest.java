package org.prgrms.kdtspringdemo.customer.repository;

import com.wix.mysql.EmbeddedMysql;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryTest {
//    @Configuration
//    @ComponentScan(basePackages = {"org.prgrms.kdtspringdemo.customer"})
//    static class Config{
//    }
    @Autowired
    CustomerRepository customerRepository;

    EmbeddedMysql embeddedMysql;
    Customer newCustomer;
    List<Customer> blackListCustomers;
    List<Customer> nonBlackLIstCustomers;

    @BeforeAll
    void setup(){
        newCustomer = new Customer(UUID.randomUUID(),
                "test-user",
                LocalDate.parse("1999-02-04"),
                "test-user@gmail.com",
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                false);

//        embeddedMysql = anEmbeddedMysql().addSchema("order_mgnt",StringResclassPath("schema.sql")).start()

    }

    //CREATE
    @Test
    @DisplayName("고객을 넣는다.")
    void insert() {
        customerRepository.insert(newCustomer);
        Optional<Customer> addedCustomer = customerRepository.findById(newCustomer.getCustomerId());
        assertThat(addedCustomer.get()).isEqualTo(newCustomer);
    }
    //READ
    @Test
    @DisplayName("모든 고객을 찾는다.")
    void findAll() {
        //given

        //when
        List<Customer> customers = customerRepository.findAll();
        //then
        assertThat(customers.isEmpty()).isFalse();
    }
    @Test
    @DisplayName("모든 고객을 찾을때, DB에 아무 데이터도 없다.")
    public void findAllButNoData() throws Exception{
        //given
        
        //when
        List<Customer> customers = customerRepository.findAll();
        //then
        assertThat(customers.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("id로 고객을 찾는다.")
    public void findById() {
        //given

        //when
        Optional<Customer> customer = customerRepository.findById(newCustomer.getCustomerId());
        //then
        assertThat(customer.get()).isEqualTo(newCustomer);
    }
    @Test
    @DisplayName("id로 고객을 찾는다. (id가 잘못됨)")
    public void findByIdWithWrongId(){
        //when
        Optional<Customer> customer = customerRepository.findById(newCustomer.getCustomerId());
        //then
        assertThat(customer.isEmpty()).isTrue();
    }

    //UPDATE
    @Test
    @DisplayName("업데이트를 한다.")
    public void update() throws Exception{
        //given
        //when

        //then
    }
    @Test
    @DisplayName("업데이트를 실패했다.(겹치는 id 가 없음)")
    public void updateFail() throws Exception{
        //given

        //when

        //then
    }
    @Test
    @DisplayName("업데이트를 실패했다.(수정이 불가능한 필드를 수정)")
    public void updateFailCannotUpdateField(){

    }
    //DELETE
    @Test
    @DisplayName("id로 고객을 지운다.")
    void deleteById() {
        customerRepository.deleteById(newCustomer.getCustomerId());
        Optional<Customer> customer = customerRepository.findById(newCustomer.getCustomerId());
        assertThat(customer.isEmpty()).isTrue();
    }
    @Test
    @DisplayName("id로 고객을 지운다 (id가 잘못됨)")
    public void deleteByIdWithWrongId() throws Exception{
        //given

        //when
        //then
        assertThrows(Exception.class,()->customerRepository.deleteById(newCustomer.getCustomerId()));
    }
    @Test
    @DisplayName("모든 고객을 지운다.")
    void deleteAll() {
        customerRepository.deleteAll();
        assertThat(customerRepository.findAll().isEmpty()).isTrue();
    }

    @Test
    @DisplayName("블랙리스트에 해당하는 고객들만 불러온다.")
    void findAllBlackList() {
        //given
//        List<Customer> blackListCustomer
        //when
        List<Customer> selectedBlackList = customerRepository.findAllBlackList();
        //then
        selectedBlackList.forEach(customer -> {
            assertThat(customer.isBlackList()).isTrue();
        });

    }
}