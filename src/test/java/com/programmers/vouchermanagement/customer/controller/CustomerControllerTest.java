package com.programmers.vouchermanagement.customer.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.math.BigDecimal;
import java.util.UUID;

import org.beryx.textio.mock.MockTextTerminal;
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
import com.programmers.vouchermanagement.customer.service.CustomerService;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;

@SpringBootTest
@ActiveProfiles("test")
class CustomerControllerTest {
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerController customerController;
    @Autowired
    MockTextTerminal textTerminal;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("블랙리스트가 없을 때 그에 맞는 뷰를 출력한다.")
    void testReadBlacklist_NoBlacklist() {
        //when
        customerController.readBlacklist();
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("no black customer stored"));
    }

    @Test
    @DisplayName("블랙리스트가 존재하면 해당 블랙리스트를 포함한 뷰를 출력한다.")
    void testReadBlacklist_ViewShowingBlacklist() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "black", CustomerType.BLACK);
        customerRepository.save(customer);

        //when
        customerController.readBlacklist();
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString(customer.getCustomerId().toString()));
    }

    @Test
    @DisplayName("고객 생성을 성공하고 고객 생성 성공 뷰를 출력한다.")
    void testCreateCustomer_ViewShowingCreatedCustomer() {
        //given
        String name = "test-customer";

        //when
        customerController.create(name);
        String output = textTerminal.getOutput();

        //then
        UUID createdCustomerId = customerService.findAll()
                .stream()
                .map(CustomerResponse::customerId)
                .findFirst()
                .orElse(UUID.randomUUID());

        assertThat(output, containsString(createdCustomerId.toString()));
        assertThat(output, containsString("successfully saved"));
    }

    @Test
    @DisplayName("저장된 고객이 없을 때 그에 맞는 뷰를 출력한다.")
    void testReadAllCustomers_NoCustomer() {
        //given
        customerRepository.deleteAll();

        //when
        customerController.readAllCustomers();
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("no customer stored"));
    }

    @Test
    @DisplayName("저장된 고객들의 전체 조회를 성공하고 고객 정보를 보여주는 뷰를 출력한다.")
    void testReadAllCustomers_ViewShowingCustomers() {
        //given
        String name = "test-customer";
        customerService.create(name);

        //when
        customerController.readAllCustomers();
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("Customer Name : " + name));
    }

    @Test
    @DisplayName("아이디로 고객 정보를 조회 성공 시 고객 정보를 포함한 뷰를 출력한다.")
    void testFindCustomerById_ViewShowingFoundCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test-customer");
        customerRepository.save(customer);

        //when
        customerController.findById(customer.getCustomerId());
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("Customer ID : " + customer.getCustomerId()));
    }

    @Test
    @DisplayName("고객 정보 업데이트 성공 시 성공 문구를 담은 뷰를 출력한다.")
    void testUpdateCustomer_ViewShowingUpdatedCustomer() {
        //given
        CustomerResponse customer = customerService.create("test-customer");
        UpdateCustomerRequest request = new UpdateCustomerRequest(customer.customerId(), "updated-name", CustomerType.NORMAL);

        //when
        customerController.update(request);
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString(request.customerId().toString()));
        assertThat(output, containsString("successfully saved"));
    }

    @Test
    @DisplayName("고객의 삭제 성공 후 성공 문구를 담은 뷰를 출력한다.")
    void testDeleteCustomerById_SuccessfullyDeleted() {
        //given
        CustomerResponse customer = customerService.create("test-customer");

        //when
        customerController.deleteById(customer.customerId());
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("Item is successfully deleted."));
    }

    @Test
    @DisplayName("바우처의 주인을 찾고 해당 고객의 정보를 담은 뷰를 출력한다.")
    void testFindVoucherOwner_ViewShowingOwner() {
        //given
        CustomerResponse customer = customerService.create("test-customer");
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED, customer.customerId());
        voucherRepository.save(voucher);

        //when
        customerController.findVoucherOwner(voucher.getVoucherId());
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("owner of Voucher " + voucher.getVoucherId()));
        assertThat(output, containsString("Customer ID : " + customer.customerId()));

        voucherRepository.deleteAll();
    }
}
