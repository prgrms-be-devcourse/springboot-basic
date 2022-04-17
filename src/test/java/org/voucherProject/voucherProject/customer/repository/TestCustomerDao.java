package org.voucherProject.voucherProject.customer.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherType;
import org.voucherProject.voucherProject.voucher.repository.VoucherDao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestCustomerDao {

    @Autowired
    CustomerDao customerRepository;

    @Autowired
    VoucherDao voucherRepository;

    @BeforeEach
    void setUp() {
        voucherRepository.deleteAll();
        customerRepository.deleteAll();

        customer = new Customer(UUID.randomUUID(), "aaa", "aaa@naver.com", "1234");
        customerRepository.save(customer);
    }

    Customer customer;

    @Nested
    @DisplayName("id로 조회")
    class findById {
        @Test
        @DisplayName("성공")
        void findById() {
            Optional<Customer> findById = customerRepository.findById(customer.getCustomerId());
            assertThat(findById.isPresent()).isTrue();
        }

        @Test
        @DisplayName("실패")
        void findByWrongId() {
            Optional<Customer> findById = customerRepository.findById(UUID.randomUUID());
            assertThat(findById.isPresent()).isFalse();
        }
    }

    @Nested
    @DisplayName("이름으로 조회")
    class findByName {
        @Test
        @DisplayName("성공")
        void findByName() {
            Optional<Customer> byName = customerRepository.findByName("aaa");
            assertThat(byName.isPresent()).isTrue();
        }

        @Test
        @DisplayName("실패")
        void findByVoidName() {
            Optional<Customer> byName = customerRepository.findByName("");
            assertThat(byName.isPresent()).isFalse();
        }
    }

    @Nested
    @DisplayName("E-Mail으로 조회")
    class findByEmail {
        @Test
        @DisplayName("성공")
        void findByEmail() {
            Optional<Customer> byEmail = customerRepository.findByEmail("aaa@naver.com");
            assertThat(byEmail.isPresent()).isTrue();
        }

        @Test
        @DisplayName("실패")
        void findByVoidEmail() {
            Optional<Customer> byEmail = customerRepository.findByEmail("bbb@.com");
            assertThat(byEmail.isPresent()).isFalse();
        }
    }

    @Test
    @DisplayName("전체 조회")
    void findAll() {
        List<Customer> all = customerRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("특정 바우터 타입을 가지고있는 고객 조회")
    public void findByVoucherType() throws Exception {
        Customer customer2 = new Customer(UUID.randomUUID(), "bbb", "bbb@.com", "1234");
        Customer customer3 = new Customer(UUID.randomUUID(), "ccc", "ccc@.com", "1234");

        customerRepository.save(customer2);
        customerRepository.save(customer3);

        Voucher voucher1 = VoucherType.FIXED.createVoucher(10, customer.getCustomerId());
        Voucher voucher2 = VoucherType.PERCENT.createVoucher(11, customer.getCustomerId());
        Voucher voucher3 = VoucherType.FIXED.createVoucher(12, customer2.getCustomerId());
        Voucher voucher4 = VoucherType.PERCENT.createVoucher(13, customer2.getCustomerId());
        Voucher voucher5 = VoucherType.FIXED.createVoucher(12, customer3.getCustomerId());
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);
        voucherRepository.save(voucher4);
        voucherRepository.save(voucher5);

        List<Customer> byVoucherType1 = customerRepository.findByVoucherType(VoucherType.FIXED);
        List<Customer> byVoucherType2 = customerRepository.findByVoucherType(VoucherType.PERCENT);

        assertThat(byVoucherType1.size()).isEqualTo(3);
        assertThat(byVoucherType2.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("업데이트")
    public void update() throws Exception {
        customer.updatePassword("4321");
        customerRepository.update(customer);
        Customer updateCustomer = customerRepository.findById(this.customer.getCustomerId()).get();

        assertThat(updateCustomer.getPassword()).isEqualTo("4321");
    }
}
