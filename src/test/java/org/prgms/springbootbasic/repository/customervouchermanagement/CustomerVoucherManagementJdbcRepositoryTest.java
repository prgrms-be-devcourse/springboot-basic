package org.prgms.springbootbasic.repository.customervouchermanagement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.voucher.FixedAmountPolicy;
import org.prgms.springbootbasic.domain.voucher.PercentDiscountPolicy;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.repository.customer.CustomerRepository;
import org.prgms.springbootbasic.repository.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ActiveProfiles("dev")
class CustomerVoucherManagementJdbcRepositoryTest {
    @Autowired
    private CustomerVoucherManagementRepository managementRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VoucherRepository voucherRepository;

    private UUID setUpCustomerId;
    private UUID setUpVoucherId;

    @BeforeEach
    void setUp() {
        setUpCustomerId = UUID.randomUUID();
        setUpVoucherId = UUID.randomUUID();
        customerRepository.upsert(new Customer(setUpCustomerId, "name", "email", LocalDateTime.now()));
        voucherRepository.upsert(new Voucher(setUpVoucherId, 1000, new FixedAmountPolicy()));
    }

    @AfterEach
    void clean() {
        managementRepository.deleteAll();
        customerRepository.deleteAll();
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("고객과 바우처가 제대로 매핑되는지 확인")
    void allocateVoucherToCustomerById() {
        UUID anotherVoucherId = UUID.randomUUID();
        voucherRepository.upsert(new Voucher(anotherVoucherId, 20, new PercentDiscountPolicy()));

        managementRepository.allocateVoucherById(setUpCustomerId, setUpVoucherId);
        managementRepository.allocateVoucherById(setUpCustomerId, anotherVoucherId);

        List<Customer> customers = managementRepository.searchCustomersByVoucherId(setUpVoucherId);
        List<Voucher> vouchers = managementRepository.searchVouchersByCustomerId(setUpCustomerId);

        assertThat(customers, hasSize(1));
        assertThat(vouchers, hasSize(2));
    }

    @Test
    @DisplayName("특정 고객과 특정 바우처 간 관계 제거")
    void deleteRelationBetweenCustomerAndVoucherById() {
        UUID anotherVoucherId = UUID.randomUUID();
        voucherRepository.upsert(new Voucher(anotherVoucherId, 100, new FixedAmountPolicy()));

        managementRepository.allocateVoucherById(setUpCustomerId, setUpVoucherId);
        managementRepository.allocateVoucherById(setUpCustomerId, anotherVoucherId);
        managementRepository.deleteVoucherById(setUpCustomerId, setUpVoucherId);

        List<Voucher> vouchers = managementRepository.searchVouchersByCustomerId(setUpCustomerId);
        List<Customer> customers = managementRepository.searchCustomersByVoucherId(setUpVoucherId);

        assertThat(vouchers, hasSize(1));
        assertThat(customers, hasSize(0));
    }

    @Test
    @DisplayName("고객 id를 이용해 바우처들을 조회")
    void searchVouchersRelatedToACustomerByCustomerId() {
        managementRepository.allocateVoucherById(setUpCustomerId, setUpVoucherId);

        List<Voucher> vouchers = managementRepository.searchVouchersByCustomerId(setUpCustomerId);
        List<Voucher> noVouchers = managementRepository.searchVouchersByCustomerId(UUID.randomUUID());

        assertThat(vouchers, hasSize(1));
        assertThat(vouchers.get(0).getVoucherId(), is(setUpVoucherId));
        assertThat(noVouchers, hasSize(0));
    }

    @Test
    @DisplayName("바우처 id를 통해 연관 고객들을 조회")
    void searchCustomersRelatedToAVoucherByVoucherId() {
        managementRepository.allocateVoucherById(setUpCustomerId, setUpVoucherId);

        List<Customer> customers = managementRepository.searchCustomersByVoucherId(setUpVoucherId);
        List<Customer> noCustomers = managementRepository.searchCustomersByVoucherId(UUID.randomUUID());

        assertThat(customers, hasSize(1));
        assertThat(customers.get(0).getCustomerId(), is(setUpCustomerId));
        assertThat(noCustomers, hasSize(0));
    }
}
