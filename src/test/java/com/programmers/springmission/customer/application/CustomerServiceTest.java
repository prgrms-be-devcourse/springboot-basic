package com.programmers.springmission.customer.application;

import com.programmers.springmission.ManagementController;
import com.programmers.springmission.customer.presentation.request.CustomerCreateRequest;
import com.programmers.springmission.customer.presentation.request.CustomerUpdateRequest;
import com.programmers.springmission.customer.presentation.response.CustomerResponse;
import com.programmers.springmission.customer.presentation.response.WalletResponse;
import com.programmers.springmission.customer.repository.JdbcCustomerRepository;
import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.InvalidInputException;
import com.programmers.springmission.voucher.domain.FixedAmountPolicy;
import com.programmers.springmission.voucher.domain.PercentDiscountPolicy;
import com.programmers.springmission.voucher.domain.Voucher;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import com.programmers.springmission.voucher.repository.JdbcVoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Transactional
@ActiveProfiles({"test", "jdbc"})
@TestPropertySource(locations = "classpath:application.yml")
class CustomerServiceTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @Autowired
    JdbcCustomerRepository customerRepository;

    @Autowired
    CustomerService service;

    @MockBean
    ManagementController managementController;

    @DisplayName("Customer create 성공 테스트")
    @Test
    void customer_create_success() {

        // given
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest("신재윤", "aaa@gmail.com");

        // when
        service.createCustomer(customerCreateRequest);

        // then
        List<CustomerResponse> customerResponses = service.findAllCustomer();
        assertThat(customerResponses.size()).isEqualTo(1);
        assertThat(customerResponses.get(0).getName()).isEqualTo(customerCreateRequest.getName());
        assertThat(customerResponses.get(0).getEmail()).isEqualTo(customerCreateRequest.getEmail());
    }

    @DisplayName("Customer create 실패 테스트 - 중복 이메일")
    @Test
    void customer_create_fail() {

        // given
        CustomerCreateRequest customerCreateRequest1 = new CustomerCreateRequest("신재윤", "aaa@gmail.com");
        CustomerCreateRequest customerCreateRequest2 = new CustomerCreateRequest("김철수", "aaa@gmail.com");

        // when
        service.createCustomer(customerCreateRequest1);

        // then
        assertThatThrownBy(() -> service.createCustomer(customerCreateRequest2))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(ErrorMessage.DUPLICATE_CUSTOMER_EMAIL.getMessage());
    }

    @DisplayName("findByIdCustomer 고객 단건 조회 성공 테스트 - id 기반")
    @Test
    void find_customer_by_id_success() {

        // given
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest("신재윤", "aaa@gmail.com");

        // when
        CustomerResponse customerResponse = service.createCustomer(customerCreateRequest);
        CustomerResponse result = service.findByIdCustomer(customerResponse.getCustomerId());

        // then
        assertThat(result.getCustomerId()).isEqualTo(customerResponse.getCustomerId());
    }

    @DisplayName("findByEmailCustomer 고객 단건 조회 성공 테스트 - email 기반")
    @Test
    void find_customer_by_email_success() {

        // given
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest("신재윤", "aaa@gmail.com");

        // when
        CustomerResponse customerResponse = service.createCustomer(customerCreateRequest);
        CustomerResponse result = service.findByEmailCustomer(customerResponse.getEmail());

        // then
        assertThat(result.getCustomerId()).isEqualTo(customerResponse.getCustomerId());
    }

    @DisplayName("findByEmailCustomer 고객 단건 조회 실패 테스트 - id 기반")
    @Test
    void find_customer_by_id_fail() {

        // given
        CustomerCreateRequest customerCreateRequest1 = new CustomerCreateRequest("신재윤", "aaa@gmail.com");
        CustomerResponse customerResponse1 = service.createCustomer(customerCreateRequest1);

        // then
        assertThatThrownBy(() -> service.findByIdCustomer(UUID.randomUUID()))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(ErrorMessage.NOT_EXIST_CUSTOMER.getMessage());
    }

    @DisplayName("findByEmailCustomer 고객 단건 조회 실패 테스트 - email 기반")
    @Test
    void find_customer_by_email_fail() {

        // given
        CustomerCreateRequest customerCreateRequest1 = new CustomerCreateRequest("신재윤", "aaa@gmail.com");
        CustomerResponse customerResponse1 = service.createCustomer(customerCreateRequest1);

        // then
        assertThatThrownBy(() -> service.findByEmailCustomer("abc@gmail.com"))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(ErrorMessage.NOT_EXIST_CUSTOMER.getMessage());
    }

    @DisplayName("findAllCustomer 고객 전체 조회 성공 테스트")
    @Test
    void find_customer_all_success() {

        // given
        CustomerCreateRequest customerCreateRequest1 = new CustomerCreateRequest("신재윤", "aaa@gmail.com");
        CustomerCreateRequest customerCreateRequest2 = new CustomerCreateRequest("김철수", "abc@gmail.com");

        // when
        service.createCustomer(customerCreateRequest1);
        service.createCustomer(customerCreateRequest2);

        // then
        List<CustomerResponse> customerResponses = service.findAllCustomer();
        assertThat(customerResponses.size()).isEqualTo(2);
    }

    @DisplayName("updateCustomer 고객 이름 수정 성공 테스트")
    @Test
    void update_customer_success() {

        // given
        CustomerCreateRequest customerCreateRequest1 = new CustomerCreateRequest("신재윤", "aaa@gmail.com");
        CustomerCreateRequest customerCreateRequest2 = new CustomerCreateRequest("김철수", "abc@gmail.com");
        CustomerResponse customerResponse1 = service.createCustomer(customerCreateRequest1);
        CustomerResponse customerResponse2 = service.createCustomer(customerCreateRequest2);
        CustomerUpdateRequest customerUpdateRequest1 = new CustomerUpdateRequest("홍길동");
        CustomerUpdateRequest customerUpdateRequest2 = new CustomerUpdateRequest("이감자");

        // when
        CustomerResponse result1 = service.updateName(customerResponse1.getCustomerId(), customerUpdateRequest1);
        CustomerResponse result2 = service.updateName(customerResponse2.getCustomerId(), customerUpdateRequest2);

        // then
        assertThat(result1.getName()).isEqualTo(customerUpdateRequest1.getName());
        assertThat(result2.getName()).isEqualTo(customerUpdateRequest2.getName());
    }

    @DisplayName("deleteByIdCustomer 고객 단건 삭제 성공 테스트")
    @Test
    void delete_customer_by_id_success() {

        // given
        CustomerCreateRequest customerCreateRequest1 = new CustomerCreateRequest("신재윤", "aaa@gmail.com");
        CustomerCreateRequest customerCreateRequest2 = new CustomerCreateRequest("김철수", "abc@gmail.com");

        // when
        CustomerResponse customerResponse1 = service.createCustomer(customerCreateRequest1);
        CustomerResponse customerResponse2 = service.createCustomer(customerCreateRequest2);
        customerRepository.deleteById(customerResponse2.getCustomerId());

        // then
        List<CustomerResponse> customerResponses = service.findAllCustomer();
        assertThat(customerResponses.size()).isEqualTo(1);
        assertThat(customerResponses.get(0).getCustomerId()).isEqualTo(customerResponse1.getCustomerId());
    }

    @DisplayName("deleteAllCustomer 고객 전체 삭제 성공 테스트")
    @Test
    void delete_customer_all_success() {

        // given
        CustomerCreateRequest customerCreateRequest1 = new CustomerCreateRequest("신재윤", "aaa@gmail.com");
        CustomerCreateRequest customerCreateRequest2 = new CustomerCreateRequest("김철수", "abc@gmail.com");
        CustomerResponse customerResponse1 = service.createCustomer(customerCreateRequest1);
        CustomerResponse customerResponse2 = service.createCustomer(customerCreateRequest2);

        // when
        customerRepository.deleteAll();

        // then
        List<CustomerResponse> customerResponses = service.findAllCustomer();
        assertThat(customerResponses.size()).isEqualTo(0);
    }

    @DisplayName("메모리에 고객이 존재하지 않을 때 예외 던지는지 테스트")
    @Test
    void valid_voucher_exist() {

        // given
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest("신재윤", "aaa@gmail.com");

        // when
        service.createCustomer(customerCreateRequest);

        // then
        assertThatThrownBy(() -> service.findByIdCustomer(UUID.randomUUID()))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(ErrorMessage.NOT_EXIST_CUSTOMER.getMessage());
    }

    @DisplayName("findCustomerWallet 지갑 조회 테스트")
    @Test
    void find_customer_wallet() {

        // given
        CustomerCreateRequest customerCreateRequest1 = new CustomerCreateRequest("신재윤", "aaa@gmail.com");
        CustomerCreateRequest customerCreateRequest2 = new CustomerCreateRequest("김철수", "abc@gmail.com");
        CustomerResponse customerResponse1 = service.createCustomer(customerCreateRequest1);
        CustomerResponse customerResponse2 = service.createCustomer(customerCreateRequest2);
        Voucher voucher1 = new Voucher(new FixedAmountPolicy(VoucherType.FIXED_AMOUNT), 2000L);
        Voucher voucher2 = new Voucher(new PercentDiscountPolicy(VoucherType.PERCENT_DISCOUNT), 50L);
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);

        // when
        voucher1.updateCustomer(customerResponse1.getCustomerId());
        voucher2.updateCustomer(customerResponse1.getCustomerId());
        voucherRepository.updateCustomer(voucher1);
        voucherRepository.updateCustomer(voucher2);

        // then
        List<WalletResponse> customerWallet1 = service.findWallet(customerResponse1.getCustomerId());
        assertThat(customerWallet1.size()).isEqualTo(2);
        assertThat(customerWallet1.get(0).getVoucherId()).isEqualTo(voucher1.getVoucherId());
        assertThat(customerWallet1.get(1).getVoucherId()).isEqualTo(voucher2.getVoucherId());

        List<WalletResponse> customerWallet2 = service.findWallet(customerResponse2.getCustomerId());
        assertThat(customerWallet2.size()).isEqualTo(0);
    }
}