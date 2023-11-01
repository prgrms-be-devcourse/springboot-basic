package com.programmers.vouchermanagement.voucher.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
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
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.UpdateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherCustomerRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;

@SpringBootTest
@ActiveProfiles("test")
class VoucherServiceTest {
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    VoucherService voucherService;

    @BeforeEach
    void setUp() {
        voucherRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("고정 금액 바우처 생성에 성공한다.")
    void testFixedVoucherCreationSuccessful() {
        //given
        CreateVoucherRequest request = new CreateVoucherRequest(new BigDecimal("100"), VoucherType.FIXED);

        //when
        VoucherResponse voucher = voucherService.create(request);

        //then
        Voucher createdVoucher = voucherRepository.findById(voucher.getVoucherId())
                .get();
        assertThat(VoucherResponse.from(createdVoucher), samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("유효하지 않은 할인 값의 고정 금액 바우처 생성에 실패한다.")
    void testFixedVoucherCreationFailed_InvalidAmount() {
        //given
        CreateVoucherRequest request = new CreateVoucherRequest(new BigDecimal("0"), VoucherType.FIXED);

        //when
        assertThatThrownBy(() -> voucherService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input should be a number greater than 0");

        //then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @DisplayName("퍼센트 할인 바우처 생성에 성공한다.")
    void textPercentVoucherCreationSuccessful() {
        //given
        CreateVoucherRequest request = new CreateVoucherRequest(new BigDecimal("50"), VoucherType.PERCENT);

        //when
        VoucherResponse voucher = voucherService.create(request);

        //then
        Voucher createdVoucher = voucherRepository.findById(voucher.getVoucherId())
                .get();
        assertThat(VoucherResponse.from(createdVoucher), samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("유효하지 않은 할인율의 퍼센트 할인 바우처 생성에 실패한다.")
    void testPercentVoucherCreationFailed_InvalidPercent() {
        //given
        CreateVoucherRequest firstRequest = new CreateVoucherRequest(new BigDecimal("0"), VoucherType.PERCENT);
        CreateVoucherRequest secondRequest = new CreateVoucherRequest(new BigDecimal("100.1"), VoucherType.PERCENT);

        //when
        assertThatThrownBy(() -> voucherService.create(firstRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input should be a number greater than 0 and smaller than 100");
        assertThatThrownBy(() -> voucherService.create(secondRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input should be a number greater than 0 and smaller than 100");

        //then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @DisplayName("저장된 바우처가 없을 때 바우처 조회 시 빈 리스트를 반환한다.")
    void testListVouchersSuccessful_ReturnEmptyList() {
        //when
        List<VoucherResponse> vouchers = voucherService.readAllVouchers();

        //then
        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @DisplayName("저장된 바우처의 리스트를 읽는데 성공한다.")
    void testListVouchersSuccessful_ReturnList() {
        //given
        Voucher firstVoucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);
        Voucher secondVoucher = new Voucher(UUID.randomUUID(), new BigDecimal(50), VoucherType.PERCENT);
        voucherRepository.save(firstVoucher);
        voucherRepository.save(secondVoucher);

        //when
        List<VoucherResponse> vouchers = voucherService.readAllVouchers();

        //then
        assertThat(vouchers, hasSize(2));
    }

    @Test
    @DisplayName("존재하지 않는 바우처의 조회를 실패한다.")
    void testFindVoucherByIdFailed_NonExistentVoucher() {
        //given
        UUID voucherId = UUID.randomUUID();

        //when & then
        assertThatThrownBy(() -> voucherService.findById(voucherId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("There is no voucher with " + voucherId);
    }

    @Test
    @DisplayName("아이디로 바우처 조회를 성공한다.")
    void testFindVoucherByIdSuccessful() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(1000), VoucherType.FIXED);
        voucherRepository.save(voucher);

        //when
        VoucherResponse voucherResponse = voucherService.findById(voucher.getVoucherId());

        //then
        assertThat(voucherResponse, samePropertyValuesAs(VoucherResponse.from(voucher)));
    }

    @Test
    @DisplayName("존재하지 않는 바우처의 정보 수정을 실패한다.")
    void testUpdateVoucherFailed_NonExistentVoucher() {
        //given
        UpdateVoucherRequest request = new UpdateVoucherRequest(UUID.randomUUID(), new BigDecimal(1000), VoucherType.FIXED);

        //when
        assertThatThrownBy(() -> voucherService.update(request))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("There is no voucher with " + request.voucherId());

        //then
        Optional<Voucher> updatedVoucher = voucherRepository.findById(request.voucherId());
        assertThat(updatedVoucher.isEmpty(), is(true));
    }

    @Test
    @DisplayName("유효하지 않은 값의 바우처 정보 수정을 실패한다.")
    void testUpdateVoucherFailed_InvalidDiscountValue() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);
        voucherRepository.save(voucher);
        UpdateVoucherRequest request = new UpdateVoucherRequest(voucher.getVoucherId(), BigDecimal.ZERO, VoucherType.FIXED);

        //when
        assertThatThrownBy(() -> voucherService.update(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input should be a number greater than 0");

        //then
        Voucher updatedVoucher = voucherRepository.findById(voucher.getVoucherId())
                .get();
        assertThat(updatedVoucher.getDiscountValue(), not(request.discountValue()));
    }

    @Test
    @DisplayName("바우처 정보 수정을 성공한다.")
    void testUpdateVoucherSuccessful() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);
        voucherRepository.save(voucher);
        UpdateVoucherRequest request = new UpdateVoucherRequest(voucher.getVoucherId(), BigDecimal.TEN, VoucherType.PERCENT);

        //when
        VoucherResponse voucherResponse = voucherService.update(request);

        //then
        Voucher updatedVoucher = voucherRepository.findById(voucher.getVoucherId())
                .get();
        assertThat(VoucherResponse.from(updatedVoucher), samePropertyValuesAs(voucherResponse));
    }

    @Test
    @DisplayName("존재하지 않는 바우처의 삭제를 실패한다.")
    void testDeleteVoucherByIdFailed_NonExistentVoucher() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);
        voucherRepository.save(voucher);
        UUID voucherId = UUID.randomUUID();
        int voucherCount = voucherRepository.findAll()
                .size();

        //when
        assertThatThrownBy(() -> voucherService.deleteById(voucherId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("There is no voucher with " + voucherId);

        //then
        int countAfterDeletion = voucherRepository.findAll()
                .size();
        assertThat(countAfterDeletion, is(voucherCount));
    }

    @Test
    @DisplayName("바우처 삭제를 성공한다.")
    void testDeleteVoucherByIdSuccessful() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);
        voucherRepository.save(voucher);
        int voucherCount = voucherRepository.findAll()
                .size();

        //when
        voucherService.deleteById(voucher.getVoucherId());

        //then
        int countAfterDeletion = voucherRepository.findAll()
                .size();
        assertThat(countAfterDeletion, is(voucherCount - 1));
    }

    @Test
    @DisplayName("존재하지 않는 고객에게 바우처 할당을 실패한다.")
    void testAssignVoucherFailed_NonExistentCustomer() {
        //given
        VoucherCustomerRequest request = new VoucherCustomerRequest(UUID.randomUUID(), UUID.randomUUID());

        //when & then
        assertThatThrownBy(() -> voucherService.grantToCustomer(request))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("There is no customer with " + request.customerId());
    }

    @Test
    @DisplayName("존재하지 않는 바우처의 할당을 실패한다.")
    void testAssignVoucherFailed_NonExistentVoucher() {
        //given
        VoucherCustomerRequest request = new VoucherCustomerRequest(UUID.randomUUID(), UUID.randomUUID());
        Customer customer = new Customer(request.customerId(), "test-customer");
        customerRepository.save(customer);

        //when & then
        assertThatThrownBy(() -> voucherService.grantToCustomer(request))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("There is no voucher with " + request.voucherId());
    }

    @Test
    @DisplayName("바우처 할당을 성공한다.")
    void testAssignVoucherSuccessful() {
        //given
        VoucherCustomerRequest request = new VoucherCustomerRequest(UUID.randomUUID(), UUID.randomUUID());
        Voucher voucher = new Voucher(request.voucherId(), new BigDecimal(10000), VoucherType.FIXED);
        Customer customer = new Customer(request.customerId(), "test-customer");
        customerRepository.save(customer);
        voucherRepository.save(voucher);

        //when
        voucherService.grantToCustomer(request);

        //then
        Voucher updatedVoucher = voucherRepository.findById(voucher.getVoucherId())
                .get();
        assertThat(updatedVoucher.getCustomerId(), is(customer.getCustomerId()));
    }

    @Test
    @DisplayName("존재하지 않는 고객의 바우처 정보 조회를 실패한다.")
    void testFindVouchersByCustomerIdFailed_NonExistentCustomer() {
        //given
        UUID customerId = UUID.randomUUID();

        //when && then
        assertThatThrownBy(() -> voucherService.findByCustomerId(customerId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("There is no customer with " + customerId);
    }

    @Test
    @DisplayName("검색한 고객에게 할당된 바우처가 없으면 빈 리스트를 반환한다.")
    void testFindVouchersByCustomerIdSuccessful_ReturnEmptyList() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test-customer");
        customerRepository.save(customer);

        //when
        List<VoucherResponse> vouchers = voucherService.findByCustomerId(customer.getCustomerId());

        //then
        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객 아이디로 보유하고 있는 바우처 조회를 성공한다.")
    void testFindVouchersByCustomerIdSuccessful_ReturnList() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test-customer", CustomerType.NORMAL);
        customerRepository.save(customer);
        Voucher firstVoucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED, customer.getCustomerId());
        Voucher secondVoucher = new Voucher(UUID.randomUUID(), new BigDecimal(40), VoucherType.PERCENT, customer.getCustomerId());
        voucherRepository.save(firstVoucher);
        voucherRepository.save(secondVoucher);

        //when
        List<VoucherResponse> vouchersOwned = voucherService.findByCustomerId(customer.getCustomerId());

        //then
        assertThat(vouchersOwned, hasSize(2));
    }

    @Test
    @DisplayName("고객 보유 바우처 삭제 시 존재하지 않는 고객이면 삭제를 실패한다.")
    void testReleaseVoucherFromCustomerFailed_NonExistentCustomer() {
        //given
        VoucherCustomerRequest request = new VoucherCustomerRequest(UUID.randomUUID(), UUID.randomUUID());
        Voucher voucher = new Voucher(request.voucherId(), new BigDecimal(10000), VoucherType.FIXED, request.customerId());
        voucherRepository.save(voucher);

        //when
        assertThatThrownBy(() -> voucherService.releaseFromCustomer(request))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("There is no customer with " + request.customerId());

        //then
        Voucher releasedVoucher = voucherRepository.findById(voucher.getVoucherId())
                .get();
        assertThat(releasedVoucher.getCustomerId(), notNullValue());
    }

    @Test
    @DisplayName("고객 보유 바우처 삭제 시 없는 바우처이면 예외를 발생시킨다.")
    void testReleaseVoucherFromCustomerFailed_NonExistentVoucher() {
        //given
        VoucherCustomerRequest request = new VoucherCustomerRequest(UUID.randomUUID(), UUID.randomUUID());
        Customer customer = new Customer(request.customerId(), "test-voucher");
        customerRepository.save(customer);

        //when & then
        assertThatThrownBy(() -> voucherService.releaseFromCustomer(request))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("There is no voucher with " + request.voucherId());
    }

    @Test
    @DisplayName("고객 보유 바우처 삭제를 성공한다.")
    void testReleaseVoucherFromVoucherSuccessful() {
        //given
        VoucherCustomerRequest request = new VoucherCustomerRequest(UUID.randomUUID(), UUID.randomUUID());
        Voucher voucher = new Voucher(request.voucherId(), new BigDecimal(10000), VoucherType.FIXED, request.customerId());
        Customer customer = new Customer(request.customerId(), "test-customer");
        voucherRepository.save(voucher);
        customerRepository.save(customer);

        //when
        voucherService.releaseFromCustomer(request);

        //then
        Voucher releasedVoucher = voucherRepository.findById(voucher.getVoucherId())
                .get();
        assertThat(releasedVoucher.getCustomerId(), nullValue());
    }
}
