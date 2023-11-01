package com.programmers.vouchermanagement.customer.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
import com.programmers.vouchermanagement.customer.dto.UpdateCustomerRequest;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;

@SpringBootTest
@ActiveProfiles("test")
class CustomerServiceTest {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("저장된 블랙리스트가 없을 때 블랙리스트 조회 시 빈 리스트를 반환한다.")
    void testReadBlacklistSuccessful_ReturnEmptyList() {
        //when
        final List<CustomerResponse> blacklist = customerService.readBlacklist();

        //then
        assertThat(blacklist.isEmpty(), is(true));
    }

    @Test
    @DisplayName("저장된 블랙리스트 조회에 성공한다.")
    void testReadBlacklistSuccessful_ReturnList() {
        //given
        Customer firstCustomer = new Customer(UUID.randomUUID(), "black 1", CustomerType.BLACK);
        Customer secondCustomer = new Customer(UUID.randomUUID(), "black 2", CustomerType.BLACK);
        customerRepository.save(firstCustomer);
        customerRepository.save(secondCustomer);

        //when
        List<CustomerResponse> blacklist = customerService.readBlacklist();

        //then
        assertThat(blacklist, hasSize(2));
    }

    @Test
    @DisplayName("고객 생성에 성공한다.")
    void testCustomerCreationSuccessful() {
        //given
        String name = "test-customer";

        //when
        CustomerResponse customer = customerService.create(name);

        //then
        assertThat(customer.name(), is(name));
        Optional<Customer> createdCustomer = customerRepository.findById(customer.customerId());
        assertThat(createdCustomer.isEmpty(), is(false));
    }

    @Test
    @DisplayName("이름이 너무 긴 고객 생성에 실패한다.")
    void testCustomerCreationFailed_ExcessiveNameLength() {
        //given
        String name = "test-customer-updated-excessive-name-length";

        //when
        assertThatThrownBy(() -> customerService.create(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name is too long.");

        //then
        Optional<Customer> createdCustomer = customerRepository.findAll()
                .stream()
                .filter(customer -> customer.getName().equals(name))
                .findFirst();
        assertThat(createdCustomer.isEmpty(), is(true));
    }

    @Test
    @DisplayName("저장된 고객이 없을 때 고객 전체 조회 시 빈 리스트를 반환한다.")
    void testListCustomersSuccessful_ReturnEmptyList() {
        //when
        List<CustomerResponse> customers = customerService.findAll();

        //then
        assertThat(customers.isEmpty(), is(true));
    }

    @Test
    @DisplayName("저장된 고객 리스트를 읽는데 성공한다.")
    void testListCustomersSuccessful_ReturnList() {
        //given
        Customer firstCustomer = new Customer(UUID.randomUUID(), "test-customer1");
        Customer secondCustomer = new Customer(UUID.randomUUID(), "test-customer2");
        customerRepository.save(firstCustomer);
        customerRepository.save(secondCustomer);

        //when
        List<CustomerResponse> customers = customerService.findAll();

        //then
        assertThat(customers, hasSize(2));
    }

    @Test
    @DisplayName("존재하지 않는 고객의 조회를 실패한다.")
    void testFindCustomerByIdFailed_NonExistentCustomer() {
        //given
        UUID customerId = UUID.randomUUID();

        //when
        assertThatThrownBy(() -> customerService.findById(customerId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("There is no customer with " + customerId);
    }

    @Test
    @DisplayName("아이디로 고객 조회를 성공한다.")
    void testFindCustomerByIdSuccessful() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test-customer");
        customerRepository.save(customer);

        //when
        CustomerResponse foundCustomer = customerService.findById(customer.getCustomerId());

        //then
        assertThat(foundCustomer, samePropertyValuesAs(CustomerResponse.from(customer)));
    }

    @Test
    @DisplayName("존재하지 않는 고객의 정보 수정을 실패한다.")
    void testUpdateCustomerFailed_NonExistentCustomer() {
        //given
        UpdateCustomerRequest request = new UpdateCustomerRequest(UUID.randomUUID(), "test-customer", CustomerType.NORMAL);

        //when
        assertThatThrownBy(() -> customerService.update(request))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("There is no customer with " + request.customerId());

        //then
        Optional<Customer> updatedCustomer = customerRepository.findById(request.customerId());
        assertThat(updatedCustomer.isEmpty(), is(true));
    }

    @Test
    @DisplayName("너무 긴 이름으로 고객 정보 수정을 실패한다.")
    void testUpdateCustomerFailed_ExcessiveNameLength() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test-customer");
        customerRepository.save(customer);
        String longName = "test-customer-updated-excessive-name-length";
        UpdateCustomerRequest request = new UpdateCustomerRequest(customer.getCustomerId(), longName, CustomerType.NORMAL);

        //when
        assertThatThrownBy(() -> customerService.update(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name is too long.");

        //then
        Customer foundCustomer = customerRepository.findById(customer.getCustomerId())
                .get();
        assertThat(foundCustomer.getName(), not(longName));
    }

    @Test
    @DisplayName("고객 정보 수정을 성공한다.")
    void testUpdateCustomerSuccessful() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test-customer");
        customerRepository.save(customer);
        UpdateCustomerRequest request = new UpdateCustomerRequest(customer.getCustomerId(), "updated-customer", CustomerType.NORMAL);

        //when
        CustomerResponse customerResponse = customerService.update(request);

        //then
        Customer updatedCustomer = customerRepository.findById(customer.getCustomerId())
                .get();
        assertThat(customerResponse, samePropertyValuesAs(CustomerResponse.from(updatedCustomer)));
    }

    @Test
    @DisplayName("존재하지 않는 고객의 삭제를 실패한다.")
    void testDeleteCustomerByIdFailed_NonExistentCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test-customer");
        customerRepository.save(customer);
        UUID customerId = UUID.randomUUID();
        int customerCount = customerRepository.findAll()
                .size();

        //when
        assertThatThrownBy(() -> customerService.deleteById(customerId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("There is no customer with " + customerId);

        //then
        int countAfterDeletion = customerRepository.findAll()
                .size();
        assertThat(countAfterDeletion, is(customerCount));
    }

    @Test
    @DisplayName("고객 삭제를 성공한다.")
    void testDeleteCustomerByIdSuccessful() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test-customer");
        customerRepository.save(customer);
        int customerCount = customerRepository.findAll()
                .size();

        //when
        customerService.deleteById(customer.getCustomerId());

        //then
        int countAfterDeletion = customerRepository.findAll()
                .size();
        assertThat(countAfterDeletion, is(customerCount - 1));
    }

    @Test
    @DisplayName("없는 바우처의 주인 조회를 실패한다.")
    void testFindByVoucherIdFailed_NonExistentVoucher() {
        //given
        UUID voucherId = UUID.randomUUID();

        //when & then
        assertThatThrownBy(() -> customerService.findByVoucherId(voucherId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("There is no voucher with " + voucherId);
    }

    @Test
    @DisplayName("바우처의 주인이 없을 경우 주인 조회를 실패한다.")
    void testFindByVoucherIdFailed_VoucherNotOwned() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);
        voucherRepository.save(voucher);

        //when & then
        assertThatThrownBy(() -> customerService.findByVoucherId(voucher.getVoucherId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("This voucher is not owned by any customer");
    }

    @Test
    @DisplayName("바우처의 주인이 존재하지 않는 고객일 경우 주인 조회를 실패한다.")
    void testFindByVoucherIdFailed_NonExistenceCustomer() {
        //given
        UUID customerId = UUID.randomUUID();
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED, customerId);
        voucherRepository.save(voucher);

        //when & then
        assertThatThrownBy(() -> customerService.findByVoucherId(voucher.getVoucherId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("There is no customer with " + customerId);
    }

    @Test
    @DisplayName("특정 바우처를 보유한 고객 조회를 성공한다.")
    void testFindByVoucherId() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED, UUID.randomUUID());
        voucherRepository.save(voucher);
        Customer customer = new Customer(voucher.getCustomerId(), "test-customer");
        customerRepository.save(customer);

        //when
        CustomerResponse voucherOwner = customerService.findByVoucherId(voucher.getVoucherId());

        //then
        assertThat(voucherOwner, samePropertyValuesAs(CustomerResponse.from(customer)));
    }
}
