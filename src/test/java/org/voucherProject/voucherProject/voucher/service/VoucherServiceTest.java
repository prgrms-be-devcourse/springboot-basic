package org.voucherProject.voucherProject.voucher.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.customer.repository.CustomerDao;
import org.voucherProject.voucherProject.customer.service.CustomerService;
import org.voucherProject.voucherProject.voucher.entity.FixedAmountVoucher;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherStatus;
import org.voucherProject.voucherProject.voucher.repository.VoucherDao;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class VoucherServiceTest {

    @Autowired
    VoucherService voucherService;
    @Autowired
    CustomerService customerService;
    @Autowired
    VoucherDao voucherRepository;
    @Autowired
    CustomerDao customerRepository;

    @BeforeEach
    void clear() {
        voucherRepository.deleteAll();
        customerRepository.deleteAll();

        customer = new Customer(UUID.randomUUID(), "aaa", "a@naver.com", "1234");
        voucher = new FixedAmountVoucher(UUID.randomUUID(), 2, customer.getCustomerId());

        customerService.save(customer);
        voucherService.save(voucher);
    }

    Customer customer;
    Voucher voucher;

    @Nested
    @DisplayName("바우처 찾기")
    class findVoucher {

        @Test
        @DisplayName("성공")
        public void success() throws Exception {
            Voucher findVoucher = voucherService.findById(voucher.getVoucherId());
            assertThat(findVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
        }

        @Test
        @DisplayName("없는 바우처 찾기 -> 예외")
        public void failure() throws Exception {
            assertThrows(IllegalArgumentException.class,
                    () -> voucherService.findById(UUID.randomUUID()));
        }
    }

    @Test
    @DisplayName("같은 바우처 저장")
    public void saveSameVoucher() throws Exception {
        assertThrows(RuntimeException.class, () -> voucherService.save(voucher));
    }

    @Test
    @DisplayName("전체 조회")
    public void findAll() throws Exception {
        assertThat(voucherService.findAll().size()).isEqualTo(1);
    }


    @Nested
    @DisplayName("고객이 가진 바우처 조회")
    class findByCustomer {

        @Test
        @DisplayName("성공")
        public void findByCustomer() throws Exception {
            List<Voucher> byCustomer = voucherService.findByCustomer(customer);
            assertThat(byCustomer.size()).isEqualTo(1);
        }

        @Test
        @DisplayName("바우처 없는 고객이 가진 바우처 조회")
        public void findByVoidCustomer() throws Exception {
            Customer newCustomer = new Customer(UUID.randomUUID(), "bbb", "bbb@naver.com", "1234");

            List<Voucher> byCustomer = voucherService.findByCustomer(newCustomer);
            assertThat(byCustomer.size()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("고객이 가진 바우처 조회")
    class deleteOneVoucherByCustomer {

        @Test
        @DisplayName("성공")
        public void deleteOneVoucherByCustomer() throws Exception {
            voucherService.deleteOneVoucherByCustomer(customer, voucher);
        }

        @Test
        @DisplayName("고객이 가진 바우처 한개 제거 -> 고객이 가진 바우처가 아니었을 때")
        public void deleteOneVoucherByWrongCustomer() throws Exception {
            Customer newCustomer = new Customer(UUID.randomUUID(), "bbb", "bbb@naver.com", "1234");
            assertThrows(RuntimeException.class, () -> voucherService.deleteOneVoucherByCustomer(newCustomer, voucher));
        }
    }

    @Test
    @DisplayName("바우처 업데이트")
    public void updateVoucher() throws Exception {
        voucher.useVoucher();
        Voucher updateVoucher = voucherService.updateVoucher(this.voucher);
        assertThat(updateVoucher.getVoucherStatus()).isEqualTo(VoucherStatus.EXPIRED);
    }
}
