package org.voucherProject.voucherProject.voucher.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.customer.repository.CustomerRepository;
import org.voucherProject.voucherProject.voucher.entity.FixedAmountVoucher;
import org.voucherProject.voucherProject.voucher.entity.PercentDiscountVoucher;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class VoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setup() {
        voucherRepository.deleteAll();
        customerRepository.deleteAll();

        customer = new Customer(UUID.randomUUID(), "aaa", "a@naver.com", "1234");
        customerRepository.save(customer);

        voucher = new FixedAmountVoucher(UUID.randomUUID(), 2, customer.getCustomerId());
        voucherRepository.save(voucher);

    }

    Customer customer;
    Voucher voucher;

    @Test
    @DisplayName("바우처 조회")
    public void findVoucher() throws Exception {
        assertThat(voucherRepository.findById(voucher.getVoucherId()).isPresent()).isTrue();
    }

    @Test
    @DisplayName("없는 바우처 조회")
    public void findVoidVoucher() throws Exception {
        assertThat(voucherRepository.findById(UUID.randomUUID()).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("같은 바우처아이디 중복 저장")
    public void saveSameVoucherId() throws Exception {
        Voucher voucherWithSameId = new FixedAmountVoucher(voucher.getVoucherId(), 41, UUID.randomUUID());
        assertThrows(RuntimeException.class, () -> voucherRepository.save(voucherWithSameId));
    }

    @Test
    @DisplayName("바우처 전체 조회")
    public void findAll() throws Exception {
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 3, UUID.randomUUID());
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 2, UUID.randomUUID());
        Voucher voucher3 = new FixedAmountVoucher(UUID.randomUUID(), 1, UUID.randomUUID());
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);

        List<Voucher> findAllVoucher = voucherRepository.findAll();

        assertThat(findAllVoucher.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("고객이 보유한 바우처를 조회")
    public void findByCustomerId() throws Exception {
        List<Voucher> vouchersByCustomer = voucherRepository.findByCustomerId(customer.getCustomerId());
        assertThat(vouchersByCustomer.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("바우처 업데이트")
    public void update() throws Exception {
        voucher.useVoucher();
        Voucher updateVoucher = voucherRepository.update(voucher);

        assertThat(updateVoucher.getVoucherStatus()).isEqualTo(VoucherStatus.EXPIRED);
    }

    @Test
    @DisplayName("고객이 보유한 바우처를 1개 제거")
    public void deleteOneVoucherByCustomerId() throws Exception {
        voucherRepository.deleteOneByCustomerId(customer.getCustomerId(),voucher.getVoucherId());

        Optional<Voucher> byId = voucherRepository.findById(voucher.getVoucherId());
        assertThat(byId.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("바우처를 1개 제거, 잘못된 customerId -> 예외 발생")
    public void deleteWrongCustomerId() throws Exception {
        Assertions.assertThrows(RuntimeException.class,
                () -> voucherRepository.deleteOneByCustomerId(UUID.randomUUID(), voucher.getVoucherId()));
    }

    @Test
    @DisplayName("바우처를 1개 제거, 잘못된 voucherId -> 예외 발생")
    public void deleteWrongVoucherId() throws Exception {
        Assertions.assertThrows(RuntimeException.class,
                () -> voucherRepository.deleteOneByCustomerId(customer.getCustomerId(), UUID.randomUUID()));
    }

    @Test
    @DisplayName("바우처를 1개 제거, 잘못된 customerId, voucherId -> 예외 발생")
    public void deleteWrongCustomerIdAndVoucherId() throws Exception {
        Assertions.assertThrows(RuntimeException.class,
                () -> voucherRepository.deleteOneByCustomerId(UUID.randomUUID(), UUID.randomUUID()));
    }
}
