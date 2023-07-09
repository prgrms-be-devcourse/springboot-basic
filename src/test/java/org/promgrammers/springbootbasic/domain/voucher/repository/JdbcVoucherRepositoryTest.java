package org.promgrammers.springbootbasic.domain.voucher.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.repository.impl.JdbcCustomerRepository;
import org.promgrammers.springbootbasic.domain.voucher.model.FixedAmountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.PercentDiscountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.repository.impl.JdbcVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yaml")
@ActiveProfiles("test")
class JdbcVoucherRepositoryTest {

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @Autowired
    JdbcCustomerRepository customerRepository;

    @AfterEach
    void init() {
        voucherRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처 할당 성공")
    void assignVoucherToCustomerSuccessTest() throws Exception {

        // given
        Customer customer = customerRepository.save(new Customer(UUID.randomUUID(), "A"));
        Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10));

        //when
        assertDoesNotThrow(() -> voucherRepository.assignVoucherToCustomer(customer.getCustomerId(), voucher.getVoucherId()));

        //then
        List<Voucher> customerVouchers = voucherRepository.findAllByCustomerId(customer.getCustomerId());
        assertThat(customerVouchers.size()).isEqualTo(1);
        assertThat(customerVouchers.get(0).getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    @Test
    @DisplayName("고객 바우처 조회 - 성공")
    void getCustomerVouchersSuccessTest() {

        // given
        Customer customer = customerRepository.save(new Customer(UUID.randomUUID(), "A"));
        Voucher voucher1 = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10));
        Voucher voucher2 = voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 20));

        voucherRepository.assignVoucherToCustomer(customer.getCustomerId(), voucher1.getVoucherId());
        voucherRepository.assignVoucherToCustomer(customer.getCustomerId(), voucher2.getVoucherId());

        // when
        List<Voucher> customerVouchers = voucherRepository.findAllByCustomerId(customer.getCustomerId());

        // then
        assertThat(customerVouchers.size()).isEqualTo(2);
        assertThat(customerVouchers.stream().map(Voucher::getVoucherId).collect(Collectors.toList()))
                .containsExactlyInAnyOrder(voucher1.getVoucherId(), voucher2.getVoucherId());
    }

    @Test
    @DisplayName("고객 바우처 조회 - 고객이 바우처를 보유하지 않을 때")
    void getCustomerVouchersNoVouchersTest() {
        // given
        UUID customerId = UUID.randomUUID();

        // when
        List<Voucher> customerVouchers = voucherRepository.findAllByCustomerId(customerId);

        // then
        assertThat(customerVouchers.isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("고객 바우처 제거 - 성공")
    void removeCustomerVoucherSuccessTest() {

        // given
        Customer customer = customerRepository.save(new Customer(UUID.randomUUID(), "A"));
        Voucher voucher1 = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10));
        Voucher voucher2 = voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 20));

        voucherRepository.assignVoucherToCustomer(customer.getCustomerId(), voucher1.getVoucherId());
        voucherRepository.assignVoucherToCustomer(customer.getCustomerId(), voucher2.getVoucherId());

        // when
        assertDoesNotThrow(() -> voucherRepository.removeVoucherFromCustomer(customer.getCustomerId(), voucher1.getVoucherId()));

        // then
        List<Voucher> customerVouchers = voucherRepository.findAllByCustomerId(customer.getCustomerId());
        assertThat(customerVouchers.size()).isEqualTo(1);
        assertThat(customerVouchers.get(0).getVoucherId()).isEqualTo(voucher2.getVoucherId());
    }


    @Test
    @DisplayName("저장 성공 - 바우처 저장")
    void insertSuccessTest() throws Exception {

        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 10;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);

        //when
        Voucher savedVoucher = assertDoesNotThrow(() -> voucherRepository.insert(voucher));

        //then
        assertNotNull(savedVoucher);
        assertThat(savedVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(savedVoucher.getVoucherType()).isEqualTo(voucher.getVoucherType());
        assertThat(savedVoucher.getAmount()).isEqualTo(voucher.getAmount());
    }

    @Test
    @DisplayName("저장 실패 - 중복 바우처 저장")
    void insertDuplicateVoucherTest() throws Exception {

        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 10;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);

        //when
        assertDoesNotThrow(() -> voucherRepository.insert(voucher));

        //then
        assertThrows(DataAccessException.class, () -> voucherRepository.insert(voucher));
    }

    @Test
    @DisplayName("단건 조회 성공 - 바우처 조회")
    void findByIdSuccessTest() throws Exception {

        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 10;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        Voucher savedVoucher = assertDoesNotThrow(() -> voucherRepository.insert(voucher));

        //when
        Optional<Voucher> repositoryById = assertDoesNotThrow(() -> voucherRepository.findById(savedVoucher.getVoucherId()));
        Voucher foundVoucher = repositoryById.get();

        //then
        assertThat(repositoryById.isPresent()).isEqualTo(true);
        assertThat(foundVoucher.getVoucherId()).isEqualTo(savedVoucher.getVoucherId());
        assertThat(foundVoucher.getVoucherType()).isEqualTo(savedVoucher.getVoucherType());
        assertThat(foundVoucher.getAmount()).isEqualTo(savedVoucher.getAmount());
    }

    @Test
    @DisplayName("예외 테스트 - 존재하지 않는 바우처 조회")
    void findByIdNonexistentVoucherTest() throws Exception {

        // given
        UUID nonExistentVoucherId = UUID.randomUUID();

        // when
        Optional<Voucher> repositoryById = assertDoesNotThrow(() -> voucherRepository.findById(nonExistentVoucherId));

        // then
        assertThat(repositoryById.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("전체 조회 성공 - 바우처 전체 조회")
    void findAllSuccessTest() throws Exception {

        //given
        int saveCount = 5;
        for (int i = 1; i <= saveCount; i++) {
            Voucher voucher;
            if (i % 2 == 0) {
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), i);
                voucherRepository.insert(voucher);
            } else {
                voucher = new FixedAmountVoucher(UUID.randomUUID(), i);
                voucherRepository.insert(voucher);
            }
        }

        //when
        List<Voucher> voucherList = assertDoesNotThrow(() -> voucherRepository.findAll());

        //then
        assertThat(voucherList.isEmpty()).isEqualTo(false);
        assertThat(voucherList.size()).isEqualTo(saveCount);
    }

    @Test
    @DisplayName("업데이트 성공 - 바우처 업데이트")
    void updateSuccessTest() throws Exception {

        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 10;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        Voucher savedVoucher = assertDoesNotThrow(() -> voucherRepository.insert(voucher));

        //when
        long updateAmount = 20;
        FixedAmountVoucher updateVoucher = new FixedAmountVoucher(savedVoucher.getVoucherId(), updateAmount);
        assertDoesNotThrow(() -> voucherRepository.update(updateVoucher));
        Optional<Voucher> updatedVoucherById = assertDoesNotThrow(() -> voucherRepository.findById(updateVoucher.getVoucherId()));

        //then
        assertThat(updatedVoucherById.isPresent()).isEqualTo(true);
        assertThat(updatedVoucherById.get().getAmount()).isEqualTo(updateAmount);
        assertThat(updatedVoucherById.get().getVoucherId()).isEqualTo(savedVoucher.getVoucherId());
    }

    @Test
    @DisplayName("전체 삭제 - 바우처 전체 삭제")
    void deleteAllSuccessTest() throws Exception {

        //given
        int saveCount = 5;
        for (int i = 1; i <= saveCount; i++) {
            Voucher voucher;
            if (i % 2 == 0) {
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), i);
                voucherRepository.insert(voucher);
            } else {
                voucher = new FixedAmountVoucher(UUID.randomUUID(), i);
                voucherRepository.insert(voucher);
            }
        }

        //when
        assertDoesNotThrow(() -> voucherRepository.deleteAll());
        List<Voucher> voucherList = assertDoesNotThrow(() -> voucherRepository.findAll());

        //then
        assertThat(voucherList.size()).isEqualTo(0);
        assertThat(voucherList.isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("단건 삭제 성공 - 바우처ID로 삭제")
    void deleteByIdSuccessTest() throws Exception {

        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 10;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        Voucher savedVoucher = assertDoesNotThrow(() -> voucherRepository.insert(voucher));

        //when
        assertDoesNotThrow(() -> voucherRepository.deleteById(savedVoucher.getVoucherId()));

        //then
        Optional<Voucher> deletedVoucher = voucherRepository.findById(savedVoucher.getVoucherId());
        assertThat(deletedVoucher.isPresent()).isEqualTo(false);
    }


}