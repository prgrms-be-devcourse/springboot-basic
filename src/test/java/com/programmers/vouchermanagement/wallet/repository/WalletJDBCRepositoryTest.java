package com.programmers.vouchermanagement.wallet.repository;

import com.programmers.vouchermanagement.TestConfig;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.repository.CustomerJDBCRepository;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.repository.VoucherJDBCRepository;
import com.programmers.vouchermanagement.wallet.domain.Ownership;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
class WalletJDBCRepositoryTest {
    private final static UUID NON_EXISTENT_VOUCHER_ID = UUID.randomUUID();
    private final static UUID NON_EXISTENT_CUSTOMER_ID = UUID.randomUUID();

    @Autowired
    WalletJDBCRepository walletJDBCRepository;
    @Autowired
    VoucherJDBCRepository voucherJDBCRepository;
    @Autowired
    CustomerJDBCRepository customerJDBCRepository;

    @Test
    @Order(1)
    @DisplayName("🆗 의존성 주입 테스트.")
    void injectionTest() {
        assertAll(
                () -> assertThat(walletJDBCRepository).isNotNull(),
                () -> assertThat(voucherJDBCRepository).isNotNull(),
                () -> assertThat(customerJDBCRepository).isNotNull()
        );
    }

    @Test
    @DisplayName("🆗 고객에게 바우처를 할당할 수 있다.")
    void save() {
        Voucher voucher = new Voucher(UUID.randomUUID(), 333, VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);
        Customer customer = new Customer(UUID.randomUUID(), "바우처 주인");
        customerJDBCRepository.save(customer);

        Ownership newOwnership = new Ownership(voucher.voucherId(), customer.getCustomerId());
        walletJDBCRepository.save(newOwnership);
    }

    @Test
    @DisplayName("🚨 이미 할당된 바우처라면, 고객에게 바우처를 할당할 수 없다.")
    void saveAllocatedVoucher() {
        Voucher voucher = new Voucher(UUID.randomUUID(), 333, VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);
        Customer customer = new Customer(UUID.randomUUID(), "바우처를 가진 고객");
        customerJDBCRepository.save(customer);

        walletJDBCRepository.save(new Ownership(voucher.voucherId(), customer.getCustomerId()));

        Customer customer2 = new Customer(UUID.randomUUID(), "바우처를 가지지 못하는 고객");
        customerJDBCRepository.save(customer2);

        assertThrows(RuntimeException.class, () -> walletJDBCRepository.save(new Ownership(voucher.voucherId(), customer2.getCustomerId())));
    }

    @Test
    @DisplayName("🚨 고객 id에 해당하는 고객이 없다면, 고객에게 바우처를 할당할 수 없다.")
    void saveNonExistentCustomer() {
        Voucher voucher = new Voucher(UUID.randomUUID(), 333, VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);

        assertThrows(RuntimeException.class, () -> walletJDBCRepository.save(new Ownership(voucher.voucherId(), NON_EXISTENT_CUSTOMER_ID)));
    }

    @Test
    @DisplayName("🚨 바우처 id에 해당하는 바우처가 없다면, 바우처를 고객에게 할당할 수 없다.")
    void saveNonExistentVoucher() {
        Customer customer = new Customer(UUID.randomUUID(), "바우처를 가지지 못한 고객");
        customerJDBCRepository.save(customer);

        assertThrows(RuntimeException.class, () -> walletJDBCRepository.save(new Ownership(NON_EXISTENT_VOUCHER_ID, customer.getCustomerId())));
    }

    @Test
    @DisplayName("🚨 id에 해당하는 바우처와 고객이 모두 없다면, 바우처를 고객에게 할당할 수 없다.")
    void saveNonExistentBoth() {
        assertThrows(RuntimeException.class, () -> walletJDBCRepository.save(new Ownership(NON_EXISTENT_VOUCHER_ID, NON_EXISTENT_CUSTOMER_ID)));
    }

    @Test
    @DisplayName("🆗 고객 id로 고객이 가진 바우처들을 가져올 수 있다.")
    void findAllVoucherByCustomerId() {
        Customer customer = new Customer(UUID.randomUUID(), "조회하려는 바우처들의 주인");
        customerJDBCRepository.save(customer);

        for (int i = 1; i < 6; i++) {
            Voucher voucher = new Voucher(UUID.randomUUID(), i, VoucherType.FIXED);
            voucherJDBCRepository.save(voucher);
            walletJDBCRepository.save(new Ownership(voucher.voucherId(), customer.getCustomerId()));
        }

        // If the customer don't have any voucher, then return empty list.
        assertThat(walletJDBCRepository.findAllVoucherByCustomerId(customer.getCustomerId()).isEmpty()).isFalse();
    }

    @Test
    @DisplayName("🚨 고객에 대한 할당 정보가 없다면, 고객이 가진 바우처들을 가져올 수 없다.")
    void findAllVoucherByNonExistentCustomerId() {
        assertThat(walletJDBCRepository.findAllVoucherByCustomerId(NON_EXISTENT_CUSTOMER_ID).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("🆗 바우처 id를 통해 할당 정보를 삭제할 수 있다. 단, 바우처 자체는 삭제되지 않는다.")
    void delete() {
        Voucher voucher = new Voucher(UUID.randomUUID(), 333, VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);
        Customer customer = new Customer(UUID.randomUUID(), "1개의 삭제될 바우처를 가진 주인");
        customerJDBCRepository.save(customer);

        walletJDBCRepository.save(new Ownership(voucher.voucherId(), customer.getCustomerId()));

        walletJDBCRepository.delete(voucher.voucherId());

        assertThat(walletJDBCRepository.findCustomerByVoucherId(voucher.voucherId()).isEmpty()).isTrue();
        assertThat(voucherJDBCRepository.findById(voucher.voucherId()).isPresent()).isTrue();
    }

    @Test
    @DisplayName("🚨 바우처에 대한 할당 정보가 없다면, 함께 저장된 고객 id와 바우처 id 정보를 삭제할 수 없다.")
    void deleteNonAllocatedVoucher() {
        Voucher voucher = new Voucher(UUID.randomUUID(), 333, VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);

        assertThrows(RuntimeException.class, () -> walletJDBCRepository.delete(voucher.voucherId()));
    }

    @Test
    @DisplayName("🆗 바우처 id로 바우처를 가진 고객 정보를 가져올 수 있다.")
    void findCustomerByVoucherId() {
        Voucher voucher = new Voucher(UUID.randomUUID(), 555, VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);
        Customer customer = new Customer(UUID.randomUUID(), "조회될 고객");
        customerJDBCRepository.save(customer);

        walletJDBCRepository.save(new Ownership(voucher.voucherId(), customer.getCustomerId()));

        Optional<Customer> retrievedCustomer = walletJDBCRepository.findCustomerByVoucherId(voucher.voucherId());

        assertThat(retrievedCustomer.isPresent()).isTrue();
        assertThat(retrievedCustomer.get().getCustomerId()).isEqualTo(customer.getCustomerId());
    }

    @Test
    @DisplayName("🚨 바우처에 대한 할당 정보가 없다면, 바우처를 가진 고객 정보를 가져올 수 없다.")
    void findCustomerByNonExistentVoucherId() {
        assertThat(walletJDBCRepository.findCustomerByVoucherId(NON_EXISTENT_VOUCHER_ID).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("🆗 바우처 자체를 삭제하면, 바우처 소유 정보가 사라진다.")
    void autoDeleteAfterVoucherDelete() {
        Voucher voucher = new Voucher(UUID.randomUUID(), 555, VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);
        Customer customer = new Customer(UUID.randomUUID(), "삭제될 바우처를 가진 고객");
        customerJDBCRepository.save(customer);

        walletJDBCRepository.save(new Ownership(voucher.voucherId(), customer.getCustomerId()));

        voucherJDBCRepository.delete(voucher.voucherId());

        assertThat(walletJDBCRepository.findCustomerByVoucherId(voucher.voucherId()).isEmpty()).isTrue();
    }
}
