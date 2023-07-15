package com.programmers.springmission.voucher.application;

import com.programmers.springmission.ManagementController;
import com.programmers.springmission.customer.domain.Customer;
import com.programmers.springmission.customer.repository.JdbcCustomerRepository;
import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.InvalidInputException;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import com.programmers.springmission.voucher.presentation.request.VoucherCreateRequest;
import com.programmers.springmission.voucher.presentation.request.VoucherUpdateRequest;
import com.programmers.springmission.voucher.presentation.response.VoucherResponse;
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
class VoucherServiceTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcCustomerRepository customerRepository;

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @Autowired
    VoucherService service;

    @MockBean
    ManagementController managementController;

    @DisplayName("FixedAmountVoucher create 성공 테스트")
    @Test
    void fixed_voucher_create_success() {

        // given
        VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, 10L);

        // when
        service.createVoucher(voucherCreateRequest);

        // then
        List<VoucherResponse> voucherResponses = service.findAllVoucher();
        assertThat(voucherResponses.size()).isEqualTo(1);
        assertThat(voucherResponses.get(0).getVoucherAmount()).isEqualTo(10L);
        assertThat(voucherResponses.get(0).getVoucherType()).isEqualTo(VoucherType.FIXED_AMOUNT);
    }

    @DisplayName("PercentDiscountVoucher create 성공 테스트")
    @Test
    void percent_voucher_create_success() {

        // given
        VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(VoucherType.PERCENT_DISCOUNT, 10L);

        // when
        service.createVoucher(voucherCreateRequest);

        // then
        List<VoucherResponse> voucherResponses = service.findAllVoucher();
        assertThat(voucherResponses.size()).isEqualTo(1);
        assertThat(voucherResponses.get(0).getVoucherAmount()).isEqualTo(10L);
        assertThat(voucherResponses.get(0).getVoucherType()).isEqualTo(VoucherType.PERCENT_DISCOUNT);
    }

    @DisplayName("findByIdVoucher 바우처 단건 조회 성공 테스트")
    @Test
    void find_voucher_by_id_success() {

        // given
        VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, 10L);

        // when
        VoucherResponse voucherResponse = service.createVoucher(voucherCreateRequest);
        VoucherResponse result = service.findByIdVoucher(voucherResponse.getVoucherId());

        // then
        assertThat(result.getVoucherId()).isEqualTo(voucherResponse.getVoucherId());
    }

    @DisplayName("findAllVoucher 바우처 전체 조회 성공 테스트")
    @Test
    void find_voucher_all_success() {

        // given
        VoucherCreateRequest voucherCreateRequest1 = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, 10L);
        VoucherCreateRequest voucherCreateRequest2 = new VoucherCreateRequest(VoucherType.PERCENT_DISCOUNT, 10L);

        // when
        service.createVoucher(voucherCreateRequest1);
        service.createVoucher(voucherCreateRequest2);

        // then
        List<VoucherResponse> voucherResponses = service.findAllVoucher();
        assertThat(voucherResponses.size()).isEqualTo(2);
    }

    @DisplayName("updateVoucher 바우처 수정 성공 테스트")
    @Test
    void update_voucher_success() {

        // given
        VoucherCreateRequest voucherCreateRequest1 = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, 10L);
        VoucherCreateRequest voucherCreateRequest2 = new VoucherCreateRequest(VoucherType.PERCENT_DISCOUNT, 10L);
        VoucherResponse voucherResponse1 = service.createVoucher(voucherCreateRequest1);
        VoucherResponse voucherResponse2 = service.createVoucher(voucherCreateRequest2);
        VoucherUpdateRequest voucherUpdateRequest1 = new VoucherUpdateRequest(500L);
        VoucherUpdateRequest voucherUpdateRequest2 = new VoucherUpdateRequest(50L);

        // when
        VoucherResponse result1 = service.updateVoucher(voucherResponse1.getVoucherId(), voucherUpdateRequest1);
        VoucherResponse result2 = service.updateVoucher(voucherResponse2.getVoucherId(), voucherUpdateRequest2);

        // then
        assertThat(result1.getVoucherAmount()).isEqualTo(500L);
        assertThat(result2.getVoucherAmount()).isEqualTo(50L);
    }

    @DisplayName("updateVoucher 바우처 수정 실패 테스트")
    @Test
    void update_voucher_fail() {

        // given
        VoucherCreateRequest voucherCreateRequest1 = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, 10L);
        VoucherCreateRequest voucherCreateRequest2 = new VoucherCreateRequest(VoucherType.PERCENT_DISCOUNT, 10L);
        VoucherResponse voucherResponse1 = service.createVoucher(voucherCreateRequest1);
        VoucherResponse voucherResponse2 = service.createVoucher(voucherCreateRequest2);
        VoucherUpdateRequest voucherUpdateRequest1 = new VoucherUpdateRequest(0L);
        VoucherUpdateRequest voucherUpdateRequest2 = new VoucherUpdateRequest(200L);

        // then
        assertThatThrownBy(() -> service.updateVoucher(voucherResponse1.getVoucherId(), voucherUpdateRequest1))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(ErrorMessage.INVALID_DISCOUNT_AMOUNT.getMessage());

        assertThatThrownBy(() -> service.updateVoucher(voucherResponse2.getVoucherId(), voucherUpdateRequest2))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(ErrorMessage.INVALID_DISCOUNT_AMOUNT.getMessage());
    }

    @DisplayName("deleteByIdVoucher 바우처 단건 삭제 성공 테스트")
    @Test
    void delete_voucher_by_id_success() {

        // given
        VoucherCreateRequest voucherCreateRequest1 = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, 10L);
        VoucherCreateRequest voucherCreateRequest2 = new VoucherCreateRequest(VoucherType.PERCENT_DISCOUNT, 10L);

        // when
        VoucherResponse voucherResponse1 = service.createVoucher(voucherCreateRequest1);
        VoucherResponse voucherResponse2 = service.createVoucher(voucherCreateRequest2);
        voucherRepository.deleteById(voucherResponse2.getVoucherId());

        // then
        List<VoucherResponse> voucherResponses = service.findAllVoucher();
        assertThat(voucherResponses.size()).isEqualTo(1);
        assertThat(voucherResponses.get(0).getVoucherId()).isEqualTo(voucherResponse1.getVoucherId());
    }

    @DisplayName("deleteAllVoucher 바우처 전체 삭제 성공 테스트")
    @Test
    void delete_voucher_all_success() {

        // given
        VoucherCreateRequest voucherCreateRequest1 = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, 10L);
        VoucherCreateRequest voucherCreateRequest2 = new VoucherCreateRequest(VoucherType.PERCENT_DISCOUNT, 10L);
        VoucherResponse voucherResponse1 = service.createVoucher(voucherCreateRequest1);
        VoucherResponse voucherResponse2 = service.createVoucher(voucherCreateRequest2);

        // when
        voucherRepository.deleteAll();

        // then
        List<VoucherResponse> voucherResponses = service.findAllVoucher();
        assertThat(voucherResponses.size()).isEqualTo(0);
    }

    @DisplayName("메모리에 바우처가 존재하지 않을 때 예외 던지는지 테스트")
    @Test
    void valid_voucher_exist() {

        // given
        VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, 10L);

        // when
        service.createVoucher(voucherCreateRequest);

        // then
        assertThatThrownBy(() -> service.findByIdVoucher(UUID.randomUUID()))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(ErrorMessage.NOT_EXIST_VOUCHER.getMessage());
    }

    @DisplayName("바우처에 고객 할당 성공하는지 테스트")
    @Test
    void assign_voucher_to_customer() {

        // given
        VoucherCreateRequest voucherCreateRequest1 = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, 10L);
        VoucherCreateRequest voucherCreateRequest2 = new VoucherCreateRequest(VoucherType.PERCENT_DISCOUNT, 10L);
        VoucherResponse voucherResponse1 = service.createVoucher(voucherCreateRequest1);
        VoucherResponse voucherResponse2 = service.createVoucher(voucherCreateRequest2);

        Customer customer = new Customer("신재윤", "abc@gmail.com");
        customerRepository.save(customer);

        // when
        service.assignVoucherToCustomer(voucherResponse1.getVoucherId(), customer.getCustomerId());
        service.assignVoucherToCustomer(voucherResponse2.getVoucherId(), customer.getCustomerId());

        // then
        List<VoucherResponse> voucherResponses = service.findAllVoucher();
        assertThat(voucherResponses.get(0).getCustomerId()).isEqualTo(customer.getCustomerId());
        assertThat(voucherResponses.get(1).getCustomerId()).isEqualTo(customer.getCustomerId());
    }
}

