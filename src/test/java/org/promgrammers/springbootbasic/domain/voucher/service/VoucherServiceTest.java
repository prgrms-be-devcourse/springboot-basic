package org.promgrammers.springbootbasic.domain.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.repository.impl.JdbcCustomerRepository;
import org.promgrammers.springbootbasic.domain.voucher.dto.request.CreateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.dto.request.UpdateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherListResponse;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherResponse;
import org.promgrammers.springbootbasic.domain.voucher.model.FixedAmountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.PercentDiscountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.model.VoucherType;
import org.promgrammers.springbootbasic.domain.voucher.repository.impl.JdbcVoucherRepository;
import org.promgrammers.springbootbasic.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class VoucherServiceTest {

    @Autowired
    private JdbcVoucherRepository voucherRepository;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private JdbcCustomerRepository customerRepository;

    @Test
    @DisplayName("고객에게 바우처 할당 성공")
    void assignVoucherFromCustomerTest() throws Exception {

        //given
        Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 100L));
        Customer customer = customerRepository.save(new Customer(UUID.randomUUID(), "user1"));

        //when
        voucherService.assignVoucherToCustomer(customer.getCustomerId(), voucher.getVoucherId());

        //then
        List<Voucher> customerVouchers = voucherRepository.findAllByCustomerId(customer.getCustomerId());
        assertThat(customerVouchers.size()).isEqualTo(1);
        assertThat(customerVouchers.get(0).getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    @Test
    @DisplayName("바우처 할당 실패 - 존재하지 않는 바우처 ID")
    void assignVoucherToCustomer_InvalidVoucherIdTest() {

        // given
        Customer customer = customerRepository.save(new Customer(UUID.randomUUID(), "user1"));
        UUID voucherId = UUID.randomUUID();

        // when -> then
        assertThrows(BusinessException.class,
                () -> voucherService.assignVoucherToCustomer(customer.getCustomerId(), voucherId));
    }

    @Test
    @DisplayName("바우처 할당 실패 - 존재하지 않는 고객 ID")
    void assignVoucherToCustomer_InvalidCustomerIdTest() {

        //given
        Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 100L));
        UUID customerId = UUID.randomUUID();
        // when -> then
        assertThrows(BusinessException.class,
                () -> voucherService.assignVoucherToCustomer(customerId, voucher.getVoucherId()));
    }

    @Test
    @DisplayName("생성 성공 - FixedVoucher")
    void createFixedVoucherTest() throws Exception {

        //given
        CreateVoucherRequest request = new CreateVoucherRequest(VoucherType.FIXED, 100L);

        //when
        voucherService.create(request);

        //then
        assertTrue(voucherRepository != null);
        assertThat(voucherRepository.findAll().size()).isEqualTo(1);
        assertThat(voucherRepository.findAll().get(0).getVoucherType()).isEqualTo(VoucherType.FIXED);
        assertThat(voucherRepository.findAll().get(0).getAmount()).isEqualTo(100L);
    }

    @Test
    @DisplayName("생성 성공 - PercentVoucher")
    void createPercentVoucherTest() throws Exception {

        //given
        CreateVoucherRequest request = new CreateVoucherRequest(VoucherType.PERCENT, 10L);

        //when
        voucherService.create(request);

        //then
        assertTrue(voucherRepository != null);
        assertThat(voucherRepository.findAll().size()).isEqualTo(1);
        assertThat(voucherRepository.findAll().get(0).getVoucherType()).isEqualTo(VoucherType.PERCENT);
        assertThat(voucherRepository.findAll().get(0).getAmount()).isEqualTo(10L);
    }

    @Test
    @DisplayName("특정 고객의 모든 바우처 조회")
    void findAllVouchersByCustomerIdTest() {

        //given
        Customer customer = customerRepository.save(new Customer(UUID.randomUUID(), "user1"));
        Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 100L));
        Voucher voucher2 = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        voucherService.assignVoucherToCustomer(customer.getCustomerId(), voucher.getVoucherId());
        voucherService.assignVoucherToCustomer(customer.getCustomerId(), voucher2.getVoucherId());

        // when
        VoucherListResponse voucherList = voucherService.findAllByCustomerId(customer.getCustomerId());

        // then
        assertThat(voucherList.voucherResponseList()).hasSize(2);
        assertThat(voucherList.voucherResponseList().get(0).voucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(voucherList.voucherResponseList().get(1).voucherId()).isEqualTo(voucher2.getVoucherId());
    }

    @Test
    @DisplayName("특정 고객의 모든 바우처 조회 실패 - 존재하지 않는 고객 ID")
    void findAllVouchersByCustomerId_InvalidCustomerIdTest() {
        // given
        UUID customerId = UUID.randomUUID();

        // when, then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> voucherService.findAllByCustomerId(customerId));
    }

    @Test
    @DisplayName("전체 조회")
    void findAllVoucherTest() throws Exception {

        //given
        CreateVoucherRequest request1 = new CreateVoucherRequest(VoucherType.PERCENT, 10L);
        CreateVoucherRequest request2 = new CreateVoucherRequest(VoucherType.FIXED, 100L);
        List<CreateVoucherRequest> requestList = Arrays.asList(request1, request2);
        requestList.forEach(voucherService::create);

        //when
        VoucherListResponse voucherList = voucherService.findAll();

        //then
        assertNotNull(voucherList);
        List<VoucherResponse> voucherResponseList = voucherList.voucherResponseList();
        assertThat(voucherList.voucherResponseList()).hasSize(requestList.size());

        VoucherResponse firstResponse = voucherResponseList.get(0);
        assertThat(firstResponse.voucherType()).isEqualTo(request1.voucherType());
        assertThat(firstResponse.amount()).isEqualTo(request1.discountAmount());

        VoucherResponse secondResponse = voucherResponseList.get(1);
        assertThat(secondResponse.voucherType()).isEqualTo(request2.voucherType());
        assertThat(secondResponse.amount()).isEqualTo(request2.discountAmount());
    }

    @Test
    @DisplayName("단건 조회 성공 - 아이디가 존재하는 경우")
    void findOneSuccessTest() throws Exception {

        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherRepository.insert(voucher);

        //when
        VoucherResponse foundVoucher = voucherService.findById(voucher.getVoucherId());

        //then
        assertThat(foundVoucher.amount()).isEqualTo(amount);
        assertThat(foundVoucher.voucherId()).isEqualTo(voucherId);
    }

    @Test
    @DisplayName("단건 조회 실패 - 아이디가 존재하지 않는 경우")
    void findOneFailTest() throws Exception {

        // given
        UUID voucherId = UUID.randomUUID();

        // when -> then
        assertThrows(BusinessException.class, () -> voucherService.findById(voucherId));
    }

    @Test
    @DisplayName("업데이트 성공 - 해당 바우처가 존재할 때")
    void updateSuccessTest() throws Exception {

        //given
        UUID voucherId = UUID.randomUUID();
        long discount = 20;
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, discount);
        voucherRepository.insert(voucher);
        UpdateVoucherRequest updateRequest = new UpdateVoucherRequest(voucherId, 10);

        //when
        VoucherResponse updateVoucher = voucherService.update(updateRequest);
        //then
        assertThat(updateVoucher.voucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(updateVoucher.amount()).isEqualTo(10);
    }

    @Test
    @DisplayName("전체 삭제")
    void deleteAllTest() throws Exception {

        //given
        CreateVoucherRequest request1 = new CreateVoucherRequest(VoucherType.PERCENT, 10L);
        CreateVoucherRequest request2 = new CreateVoucherRequest(VoucherType.FIXED, 100L);
        List<CreateVoucherRequest> requestList = Arrays.asList(request1, request2);
        requestList.forEach(voucherService::create);

        //when
        voucherService.deleteAll();

        //then
        assertThat(voucherRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("단건 삭제 성공 - 바우처ID가 존재하는 경우")
    void deleteVoucherByIdSuccessTest() throws Exception {

        //given
        UUID voucherId = UUID.randomUUID();
        long discount = 20;
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, discount);
        voucherRepository.insert(voucher);

        //when
        assertDoesNotThrow(() -> voucherService.deleteById(voucherId));
        Optional<Voucher> deletedVoucher = voucherRepository.findById(voucherId);

        //then
        assertThat(deletedVoucher.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("단건 삭제 실패 - 바우처ID가 존재하지 않는 경우")
    void deleteVoucherByIdFailTest() throws Exception {

        //given
        UUID customerId = UUID.randomUUID();

        assertThrows(BusinessException.class, () -> voucherService.deleteById(customerId));
    }


}