package org.voucherProject.voucherProject.voucher.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.customer.repository.CustomerDao;
import org.voucherProject.voucherProject.customer.service.CustomerService;
import org.voucherProject.voucherProject.voucher.entity.FixedAmountVoucher;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherStatus;
import org.voucherProject.voucherProject.voucher.entity.VoucherType;
import org.voucherProject.voucherProject.voucher.repository.VoucherDao;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class VoucherServicTest {

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
        @DisplayName("없는 바우처 찾기 -> 예외 발생")
        public void failure() throws Exception {
            assertThrows(IllegalArgumentException.class,
                    () -> voucherService.findById(UUID.randomUUID()));
        }

        @Test
        @DisplayName("바우처 id로 Null 입력 -> 예외(NPE) 발생")
        public void inputNull() throws Exception {
            assertThrows(NullPointerException.class, () -> voucherService.findById(null));
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
        @DisplayName("고객 아이디를 정확히 입력할 경우 -> 성공")
        public void findByCustomer() throws Exception {
            List<Voucher> byCustomer = voucherService.findByCustomer(customer);
            assertThat(byCustomer.size()).isEqualTo(1);
        }

        @Test
        @DisplayName("바우처 없는 고객이 가진 바우처 조회 -> 결과 0")
        public void findByVoidCustomer() throws Exception {
            Customer newCustomer = new Customer(UUID.randomUUID(), "bbb", "bbb@naver.com", "1234");

            List<Voucher> byCustomer = voucherService.findByCustomer(newCustomer);
            assertThat(byCustomer.size()).isEqualTo(0);
        }

        @Test
        @DisplayName("고객에 Null 입력으로 바우처 조회 -> NPE 발생")
        public void findByNullCustomer() throws Exception {
            assertThrows(NullPointerException.class, () -> voucherService.findByCustomer(null));
        }
    }

    @Nested
    @DisplayName("고객이 가진 바우처 제거")
    class deleteOneVoucherByCustomer {

        @Test
        @DisplayName("고객의 아이디와 바우처의 아이디를 정확하게 입력 -> 제거 성공")
        public void deleteOneVoucherByCustomer() throws Exception {
            voucherService.deleteOneVoucherByCustomer(customer.getCustomerId(), voucher.getVoucherId());
        }

        @Test
        @DisplayName("고객이 가진 바우처 한개 제거 -> 고객이 가진 바우처가 아니었을 때 -> 예외 발생")
        public void deleteOneVoucherByWrongCustomer() throws Exception {
            Customer newCustomer = new Customer(UUID.randomUUID(), "bbb", "bbb@naver.com", "1234");
            assertThrows(RuntimeException.class, () -> voucherService.deleteOneVoucherByCustomer(newCustomer.getCustomerId(), voucher.getVoucherId()));
        }
    }

    @Test
    @DisplayName("바우처 업데이트")
    public void updateVoucher() throws Exception {
        voucher.useVoucher();
        Voucher updateVoucher = voucherService.updateVoucher(this.voucher);
        assertThat(updateVoucher.getVoucherStatus()).isEqualTo(VoucherStatus.EXPIRED);
    }

    @Test
    @DisplayName("바우처 타입으로 바우처들을 조회")
    public void findByVoucherType() throws Exception {

        List<Voucher> byFixedVoucherType = voucherService.findByVoucherType(VoucherType.FIXED);
        assertThat(byFixedVoucherType.size()).isEqualTo(1);
        List<Voucher> byPercentVoucherType = voucherService.findByVoucherType(VoucherType.PERCENT);
        assertThat(byPercentVoucherType.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Null 바우처 타입 입력")
    public void findByNullVoucherType() throws Exception {
        assertThrows(NullPointerException.class, () -> voucherService.findByVoucherType(null));
    }

    @Nested
    @DisplayName("바우처 사용")
    class useVoucher {
        @Test
        @DisplayName("바우처 정상적인 사용 -> 성공")
        public void useVoucher() throws Exception {
            voucherService.useVoucher(voucher);
            assertThat(voucherService.findById(voucher.getVoucherId()).getVoucherStatus()).isEqualTo(VoucherStatus.EXPIRED);
        }

        @Test
        @DisplayName("바우처 사용에 null 입력 -> NPE")
        public void useNull() throws Exception {
            assertThrows(NullPointerException.class, () -> voucherService.useVoucher(null));
        }

        @Test
        @DisplayName("바우처 사용에 만료된 바우처 입력 -> RTE")
        public void useExpiredVoucher() throws Exception {
            voucherService.useVoucher(voucher);
            assertThrows(RuntimeException.class, () -> voucherService.useVoucher(voucher));
        }
    }

    @Nested
    @DisplayName("바우처 사용 취소")
    class cancelVoucher {
        @Test
        @DisplayName("바우처 정상적인 취소")
        public void useVoucher() throws Exception {
            voucher.useVoucher();
            voucherService.cancelVoucher(voucher);
            assertThat(voucherService.findById(voucher.getVoucherId()).getVoucherStatus()).isEqualTo(VoucherStatus.VALID);
        }

        @Test
        @DisplayName("바우처 사용취소에 null 입력 -> NPE")
        public void useNull() throws Exception {
            voucher.useVoucher();
            assertThrows(NullPointerException.class, () -> voucherService.cancelVoucher(null));
        }

        @Test
        @DisplayName("바우처 사용 취소에 유효한 바우처 입력 -> RTE")
        public void useExpiredVoucher() throws Exception {
            voucher.useVoucher();
            voucherService.cancelVoucher(voucher);
            assertThrows(RuntimeException.class, () -> voucherService.cancelVoucher(voucher));
        }
    }
}
