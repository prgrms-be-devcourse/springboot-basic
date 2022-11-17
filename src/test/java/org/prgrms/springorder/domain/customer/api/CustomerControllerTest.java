package org.prgrms.springorder.domain.customer.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springorder.common.ControllerIntegrationBase;
import org.prgrms.springorder.console.io.Response;
import org.prgrms.springorder.domain.customer.api.request.CreateCustomerRequest;
import org.prgrms.springorder.domain.customer.model.BlockCustomer;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.customer.repository.BlockCustomerJdbcRepository;
import org.prgrms.springorder.domain.customer.repository.CustomerJdbcRepository;
import org.prgrms.springorder.domain.voucher.api.request.AllocateVoucherRequest;
import org.prgrms.springorder.domain.voucher.api.request.DeleteVoucherRequest;
import org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.repository.VoucherJdbcRepository;
import org.prgrms.springorder.global.exception.BadAccessRequestException;
import org.prgrms.springorder.global.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("CustomerController 통합 테스트")
class CustomerControllerTest extends ControllerIntegrationBase {

    @Autowired
    private CustomerController customerController;

    @Autowired
    private CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    private BlockCustomerJdbcRepository blockCustomerJdbcRepository;

    @Autowired
    private VoucherJdbcRepository voucherJdbcRepository;

    @DisplayName("findAll BlockCustomer 테스트 - 비어있으면 예외가 발생한다. ")
    @Test
    void findAllCustomersEmptyThrowsExceptionTest() {
        //given & when & then
        assertThrows(EntityNotFoundException.class,
            () -> customerController.findAllBlockCustomers());
    }


    @DisplayName("createCustomer 테스트 - 커스토머가 생성된다")
    @Test
    void createCustomer() {
        //given
        String name = "name";
        String email = "email@email.com";
        CreateCustomerRequest createCustomerRequest
            = new CreateCustomerRequest(name, email);

        //when
        Response response = customerController.createCustomer(createCustomerRequest);

        //then
        assertThat(response.getResponse()).contains(name, email);
    }

    @DisplayName("findAll Customer 테스트 - 저장된 모든 BlockCustomer 가 반환된다 ")
    @Test
    void findAllCustomersSuccessTest() {
        //given
        List<BlockCustomer> save = save(5);
        String s = save.stream().map(Objects::toString).collect(Collectors.joining("\n"));
        Response allCustomers = customerController.findAllBlockCustomers();

        //then
        assertThat(allCustomers.getResponse()).contains(s);
    }

    @DisplayName("바우처 제거 테스트 - 고객이 보유한 바우처가 제거된다. ")
    @Test
    void deleteVoucherSuccess() {
        //given
        String email = "email@gmail.com";
        String name = "name";
        Customer customer = new Customer(UUID.randomUUID(), name, email);
        customerJdbcRepository.insert(customer);

        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, customer.getCustomerId(),
            LocalDateTime.now());
        voucherJdbcRepository.insert(voucher);
        //when
        Response response = customerController.deleteVoucher(
            new DeleteVoucherRequest(voucher.getVoucherId().toString(),
                customer.getCustomerId().toString()));

        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucher.getVoucherId());

        //then
        assertEquals("ok", response.getResponse());
        assertTrue(voucherOptional.isEmpty());
    }

    @DisplayName("바우처 제거 실패 테스트 - 고객이 보유한 바우처가 아니라면 예외를 던진다.")
    @Test
    void deleteVoucherFailNotCustomerVoucherThrowsExceptionTest() {
        //given
        String email = "email@gmail.com";
        String name = "name";
        Customer customer = new Customer(UUID.randomUUID(), name, email);

        UUID otherCustomerId = UUID.randomUUID();
        String otherName = "otherName";
        String otherEmail = "otherEmail@gamil.com";
        Customer otherCustomer = new Customer(otherCustomerId, otherName, otherEmail);
        customerJdbcRepository.insert(otherCustomer);
        customerJdbcRepository.insert(customer);

        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, otherCustomerId,
            LocalDateTime.now());
        voucherJdbcRepository.insert(voucher);

        DeleteVoucherRequest deleteVoucherRequest = new DeleteVoucherRequest(
            voucher.getVoucherId().toString(),
            customer.getCustomerId().toString());

        //when
        assertThrows(BadAccessRequestException.class,
            () -> customerController.deleteVoucher(deleteVoucherRequest));

        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucher.getVoucherId());

        //then
        assertTrue(voucherOptional.isPresent());
        Voucher findVoucher = voucherOptional.get();
        assertEquals(voucher, findVoucher);
        assertNotEquals(customer.getCustomerId(), voucher.getCustomerId());
    }

    @DisplayName("바우처 제거 실패 테스트 - 고객이 존재하지 않는다면 예외를 던진다.")
    @Test
    void deleteVoucherNotExistsCustomer() {
        //given
        String email = "email@gmail.com";
        String name = "name";
        Customer customer = new Customer(UUID.randomUUID(), name, email);

        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();

        DeleteVoucherRequest deleteVoucherRequest = new DeleteVoucherRequest(voucherId.toString(),
            customerId.toString());

        //when
        assertThrows(EntityNotFoundException.class,
            () -> customerController.deleteVoucher(deleteVoucherRequest));

        Optional<Customer> customerOptional = customerJdbcRepository.findById(customerId);

        //then
        assertTrue(customerOptional.isEmpty());
    }

    @DisplayName("바우처 할당 테스트 - 고객에게 바우처를 할당한다.")
    @Test
    void allocateVoucherSuccessTest() {
        //given
        String email = "email@gmail.com";
        String name = "name";
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, name, email);

        customerJdbcRepository.insert(customer);

        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, 100L);

        voucherJdbcRepository.insert(voucher);

        AllocateVoucherRequest allocateVoucherRequest = new AllocateVoucherRequest(
            voucherId.toString(), customerId.toString());

        //when
        Response response = customerController.allocateVoucher(allocateVoucherRequest);

        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucherId);

        //then
        assertEquals("ok", response.getResponse());
        assertTrue(voucherOptional.isPresent());
        Voucher findVoucher = voucherOptional.get();

        assertEquals(customerId, findVoucher.getCustomerId());
    }

    @DisplayName("바우처 할당 테스트 - 고객이 존재하지 않으면 예외를 던진다.")
    @Test
    void allocateVoucherFailNotFoundCustomerThrowsExceptionTest() {
        //given
        UUID customerId = UUID.randomUUID();

        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, 100L);

        voucherJdbcRepository.insert(voucher);

        AllocateVoucherRequest allocateVoucherRequest = new AllocateVoucherRequest(
            voucherId.toString(), customerId.toString());

        //when
        assertThrows(EntityNotFoundException.class, () ->
            customerController.allocateVoucher(allocateVoucherRequest));

        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucherId);
        Optional<Customer> customerOptional = customerJdbcRepository.findById(customerId);

        //then
        assertTrue(customerOptional.isEmpty());
        assertTrue(voucherOptional.isPresent());
    }

    @DisplayName("바우처 할당 테스트 - 바우처가 존재하지 않으면 예외를 던진다.")
    @Test
    void allocateVoucherFailNotFoundVoucherThrowsExceptionTest() {
        //given
        String email = "email@gmail.com";
        String name = "name";
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, name, email);

        customerJdbcRepository.insert(customer);
        UUID voucherId = UUID.randomUUID();

        AllocateVoucherRequest allocateVoucherRequest = new AllocateVoucherRequest(
            voucherId.toString(), customerId.toString());

        //when
        assertThrows(EntityNotFoundException.class, () ->
            customerController.allocateVoucher(allocateVoucherRequest));

        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucherId);
        Optional<Customer> customerOptional = customerJdbcRepository.findById(customerId);

        //then
        assertTrue(voucherOptional.isEmpty());
        assertTrue(customerOptional.isPresent());
    }

    private List<BlockCustomer> save(int saveCount) {
        String name = "name";
        String email = "email@gmail.com";
        return IntStream.range(0, saveCount)
            .mapToObj(value -> {
                Customer customer = new Customer(UUID.randomUUID(), name + value, value + email);
                customerJdbcRepository.insert(customer);
                BlockCustomer blockCustomer = new BlockCustomer(UUID.randomUUID(),
                    customer.getCustomerId(), LocalDateTime.now());
                return blockCustomerJdbcRepository.insert(blockCustomer);
            }).collect(Collectors.toList());
    }

}