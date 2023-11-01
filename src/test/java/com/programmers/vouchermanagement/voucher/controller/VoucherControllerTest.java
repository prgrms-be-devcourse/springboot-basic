package com.programmers.vouchermanagement.voucher.controller;

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
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.UpdateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherCustomerRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;
import com.programmers.vouchermanagement.voucher.service.VoucherService;

@SpringBootTest
@ActiveProfiles("test")
class VoucherControllerTest {
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    VoucherService voucherService;
    @Autowired
    VoucherController voucherController;
    @Autowired
    MockTextTerminal textTerminal;

    @BeforeEach
    void setUp() {
        voucherRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처 생성을 성공하고 바우처 생성 성공 뷰를 출력한다.")
    void testCreateVoucher_ViewShowingCreatedVoucher() {
        //given
        CreateVoucherRequest request = new CreateVoucherRequest(new BigDecimal(10000), VoucherType.FIXED);

        //when
        voucherController.create(request);
        String output = textTerminal.getOutput();

        //then
        UUID createdCVoucherId = voucherService.readAllVouchers()
                .stream()
                .map(VoucherResponse::getVoucherId)
                .findFirst()
                .orElse(UUID.randomUUID());
        assertThat(output, containsString(createdCVoucherId.toString()));
        assertThat(output, containsString("successfully saved"));
    }

    @Test
    @DisplayName("저장된 바우처가 없을 때 그에 맞는 뷰를 출력한다.")
    void testReadAllVouchers_NoVoucher() {
        //given
        voucherRepository.deleteAll();

        //when
        voucherController.readAllVouchers();
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("no voucher stored"));
    }

    @Test
    @DisplayName("저장된 바우처들의 전체 조회를 성공하고 바우처 정보를 보여주는 뷰를 출력한다.")
    void testReadAllVouchers_ViewShowingVouchers() {
        //given
        CreateVoucherRequest request = new CreateVoucherRequest(new BigDecimal(10000), VoucherType.FIXED);
        voucherController.create(request);

        //when
        voucherController.readAllVouchers();
        String output = textTerminal.getOutput();

        //then
        UUID foundVoucherId = voucherService.readAllVouchers()
                .stream()
                .map(VoucherResponse::getVoucherId)
                .findFirst()
                .orElse(UUID.randomUUID());
        assertThat(output, containsString("Voucher ID : " + foundVoucherId));
    }

    @Test
    @DisplayName("아이디로 바우처 정보 조회 성공 시 바우처 정보를 포함한 뷰를 출력한다.")
    void testFindVoucherById_ViewShowingFoundVoucher() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);
        voucherRepository.save(voucher);

        //when
        voucherController.findById(voucher.getVoucherId());
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("Voucher ID : " + voucher.getVoucherId()));
    }

    @Test
    @DisplayName("바우처 정보 업데이트 성공 시 성공 문구를 담은 뷰를 출력한다.")
    void testUpdateVoucher_ViewShowingUpdatedVoucher() {
        //given
        CreateVoucherRequest createRequest = new CreateVoucherRequest(new BigDecimal(10000), VoucherType.FIXED);
        VoucherResponse voucher = voucherService.create(createRequest);
        UpdateVoucherRequest updateRequest = new UpdateVoucherRequest(voucher.getVoucherId(), new BigDecimal(20000), VoucherType.FIXED);

        //when
        voucherController.update(updateRequest);
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString(updateRequest.voucherId().toString()));
        assertThat(output, containsString("successfully saved"));
    }

    @Test
    @DisplayName("바우처의 삭제 성공 후 성공 문구를 담은 뷰를 출력한다.")
    void testDeleteVoucherById_SuccessfullyDeleted() {
        //given
        CreateVoucherRequest createRequest = new CreateVoucherRequest(new BigDecimal(10000), VoucherType.FIXED);
        VoucherResponse voucher = voucherService.create(createRequest);

        //when
        voucherController.deleteById(voucher.getVoucherId());
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("Item is successfully deleted."));
    }

    @Test
    @DisplayName("바우처 할당 성공 시 성공 문구를 담은 뷰를 출력한다.")
    void testGrantVoucherToCustomer_SuccessfullyGranted() {
        //given
        CreateVoucherRequest createRequest = new CreateVoucherRequest(new BigDecimal(10000), VoucherType.FIXED);
        VoucherResponse voucher = voucherService.create(createRequest);
        Customer customer = new Customer(UUID.randomUUID(), "test-customer");
        customerRepository.save(customer);
        VoucherCustomerRequest request = new VoucherCustomerRequest(voucher.getVoucherId(), customer.getCustomerId());

        //when
        voucherController.grantToCustomer(request);
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("Voucher (%s) is granted to Customer (%s)."
                .formatted(request.voucherId(), request.customerId())));
    }

    @Test
    @DisplayName("고객에게 할당된 바우처가 없으면 그에 맞는 뷰를 출력한다.")
    void testSearchOwnedVouchers_NoVouchersOwned() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test-customer");
        customerRepository.save(customer);

        //when
        voucherController.searchOwnedVouchers(customer.getCustomerId());
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("This customer has no voucher yet!"));
    }

    @Test
    @DisplayName("고객에게 할당된 바우처를 성공적으로 조회하고 바우처 정보를 담은 뷰를 출력한다.")
    void testSearchOwnedVouchers_ViewShowingOwnedVouchers() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test-customer");
        customerRepository.save(customer);
        Voucher firstVoucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED, customer.getCustomerId());
        Voucher secondVoucher = new Voucher(UUID.randomUUID(), new BigDecimal(40), VoucherType.PERCENT, customer.getCustomerId());
        voucherRepository.save(firstVoucher);
        voucherRepository.save(secondVoucher);

        //when
        voucherController.searchOwnedVouchers(customer.getCustomerId());
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("Voucher ID : " + firstVoucher.getVoucherId()));
        assertThat(output, containsString("Voucher ID : " + secondVoucher.getVoucherId()));
    }

    @Test
    @DisplayName("고객이 가진 바우처를 회수하고 성공 문구를 담은 뷰를 출력한다.")
    void testRemoveVoucherFromCustomer_SuccessfullyRemoved() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test-customer");
        customerRepository.save(customer);
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(40), VoucherType.PERCENT, customer.getCustomerId());
        voucherRepository.save(voucher);
        VoucherCustomerRequest request = new VoucherCustomerRequest(voucher.getVoucherId(), customer.getCustomerId());

        //when
        voucherController.removeVoucherFromCustomer(request);
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("Item is successfully deleted."));
    }
}
