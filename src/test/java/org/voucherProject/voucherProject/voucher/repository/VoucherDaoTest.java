package org.voucherProject.voucherProject.voucher.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.customer.repository.CustomerDao;
import org.voucherProject.voucherProject.voucher.entity.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class VoucherDaoTest {

    @Autowired
    VoucherDao voucherDao;

    @Autowired
    CustomerDao customerRepository;

    @BeforeEach
    void setup() {
        voucherDao.deleteAll();
        customerRepository.deleteAll();

        customer = new Customer(UUID.randomUUID(), "aaa", "a@naver.com", "1234");
        customerRepository.save(customer);

        voucher = new FixedAmountVoucher(UUID.randomUUID(), 2, customer.getCustomerId());
        voucherDao.save(voucher);

    }

    Customer customer;
    Voucher voucher;


    @Nested
    @DisplayName("바우처 생성")
    class createVoucher {

        private final int MIN_DISCOUNT_AMOUNT = 0;
        private final int MAX_DISCOUNT_AMOUNT = 10000;

        private final int MIN_DISCOUNT_PERCENT = 0;
        private final int MAX_DISCOUNT_PERCENT = 100;

        @Test
        @DisplayName("Fixed 생성 성공")
        public void createFixedVoucher() throws Exception {
            VoucherType.FIXED.createVoucher(1, UUID.randomUUID());
        }

        @Test
        @DisplayName("Percent 생성 성공")
        public void createPercentVoucher() throws Exception {
            VoucherType.PERCENT.createVoucher(1, UUID.randomUUID());
        }

        @Test
        @DisplayName("Fixed(-1) 생성 -> 실패")
        public void createMinusFixedVoucher() throws Exception {
            assertThrows(IllegalArgumentException.class, () -> VoucherType.FIXED.createVoucher(-1, UUID.randomUUID()));
        }

        @Test
        @DisplayName("Percent(-1) 생성 -> 실패")
        public void createMaxPercentVoucher() throws Exception {
            assertThrows(IllegalArgumentException.class, () -> VoucherType.PERCENT.createVoucher(-1, UUID.randomUUID()));
        }

        @Test
        @DisplayName("Fixed(Max) 생성 -> 실패")
        public void createMaxFixedVoucher() throws Exception {
            assertThrows(IllegalArgumentException.class, () -> VoucherType.FIXED.createVoucher(MAX_DISCOUNT_AMOUNT+1, UUID.randomUUID()));
        }

        @Test
        @DisplayName("Percent(Max) 생성 -> 실패")
        public void createMinusPercentVoucher() throws Exception {
            assertThrows(IllegalArgumentException.class, () -> VoucherType.PERCENT.createVoucher(MAX_DISCOUNT_PERCENT+1, UUID.randomUUID()));
        }
    }

    @Nested
    @DisplayName("할인 결과")
    class discount {
        @Test
        @DisplayName("고정할인 결과 성공")
        public void discountFixed() {
            Voucher voucher = VoucherType.FIXED.createVoucher(1000, UUID.randomUUID());
            long discount = voucher.discount(10000);
            assertThat(discount).isEqualTo(9000);
        }

        @Test
        @DisplayName("퍼센트할인 결과 성공")
        public void discountPercent() {
            Voucher voucher = VoucherType.PERCENT.createVoucher(10, UUID.randomUUID());
            long discount = voucher.discount(10000);
            assertThat(discount).isEqualTo(9000);
        }
        @Test
        @DisplayName("고정할인 결과 결과값이 0보다 작을 때 -> 실패")
        public void FixedBigDiscount() {
            Voucher voucher = VoucherType.FIXED.createVoucher(1001, UUID.randomUUID());
            assertThrows(IllegalArgumentException.class, () -> voucher.discount(1000));
        }
    }

    @Nested
    @DisplayName("바우처 조회")
    class findVoucherId {
        @Test
        @DisplayName("유효한 바우처 아이디 존재 -> 성공")
        public void findVoucher() throws Exception {
            assertThat(voucherDao.findById(voucher.getVoucherId()).isPresent()).isTrue();
        }

        @Test
        @DisplayName("바우처 아이디가 db에 존재하지 않을 때 -> 실패")
        public void findVoidVoucher() throws Exception {
            assertThat(voucherDao.findById(UUID.randomUUID()).isEmpty()).isTrue();
        }

        @Test
        @DisplayName("바우처 아이디가 db에 존재하지 않을 때 -> 실패")
        public void findNull() throws Exception {
            assertThrows(RuntimeException.class, () -> voucherDao.findById(null));
        }
    }

    @Test
    @DisplayName("같은 바우처아이디 중복 저장")
    public void saveSameVoucherId() throws Exception {
        Voucher voucherWithSameId = new FixedAmountVoucher(voucher.getVoucherId(), 41, UUID.randomUUID());
        assertThrows(RuntimeException.class, () -> voucherDao.save(voucherWithSameId));
    }

    @Test
    @DisplayName("바우처 전체 조회")
    public void findAll() throws Exception {
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 3, UUID.randomUUID());
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 2, UUID.randomUUID());
        Voucher voucher3 = new FixedAmountVoucher(UUID.randomUUID(), 1, UUID.randomUUID());
        voucherDao.save(voucher1);
        voucherDao.save(voucher2);
        voucherDao.save(voucher3);

        List<Voucher> findAllVoucher = voucherDao.findAll();

        assertThat(findAllVoucher.size()).isEqualTo(4);
    }

    @Nested
    @DisplayName("고객 아이디로 조회")
    class findByCustomerId {
        @Test
        @DisplayName("고객이 보유한 바우처를 조회")
        public void findByCustomerId() throws Exception {
            List<Voucher> vouchersByCustomer = voucherDao.findByCustomerId(customer.getCustomerId());
            assertThat(vouchersByCustomer.size()).isEqualTo(1);
        }

        @Test
        @DisplayName("고객이 보유한 바우처를 조회 -> 없을때 -> 조회수 0")
        public void findByVoidCustomerId() throws Exception {
            List<Voucher> vouchersByCustomer = voucherDao.findByCustomerId(UUID.randomUUID());
            assertThat(vouchersByCustomer.size()).isEqualTo(0);
        }

        @Test
        @DisplayName("고객이 보유한 바우처를 조회 -> null -> 예외 발생")
        public void findByNullCustomerId() throws Exception {
            assertThrows(RuntimeException.class, () -> voucherDao.findByCustomerId(null));
        }
    }

    @Test
    @DisplayName("바우처 업데이트 (바우처 상태가 valid -> expired), 그 외는 변화 없음")
    public void update() throws Exception {
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 20, customer.getCustomerId());
        Voucher voucher3 = new FixedAmountVoucher(UUID.randomUUID(), 30, customer.getCustomerId());
        Voucher voucher4 = new FixedAmountVoucher(UUID.randomUUID(), 25, customer.getCustomerId());
        voucherDao.save(voucher2);
        voucherDao.save(voucher3);
        voucherDao.save(voucher4);

        voucher.useVoucher();
        Voucher updateVoucher = voucherDao.update(voucher);

        //voucher 만 상태 변화
        assertThat(voucherDao.findById(voucher.getVoucherId()).get().getVoucherStatus()).isEqualTo(VoucherStatus.EXPIRED);

        //그 외는 변화 없음
        assertThat(voucherDao.findById(voucher2.getVoucherId()).get().getVoucherStatus()).isEqualTo(VoucherStatus.VALID);
        assertThat(voucherDao.findById(voucher3.getVoucherId()).get().getVoucherStatus()).isEqualTo(VoucherStatus.VALID);
        assertThat(voucherDao.findById(voucher4.getVoucherId()).get().getVoucherStatus()).isEqualTo(VoucherStatus.VALID);
    }

    @Nested
    @DisplayName("고객이 가진 바우처 제거")
    class deleteVoucher {
        @Test
        @DisplayName("바우처 제거 성공")
        public void deleteOneVoucherByCustomerId() throws Exception {
            voucherDao.deleteOneByCustomerId(customer.getCustomerId(),voucher.getVoucherId());

            Optional<Voucher> byId = voucherDao.findById(voucher.getVoucherId());
            assertThat(byId.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("바우처 아이디는 유효,고객 아이디가 없는 아이디 -> 예외 발생")
        public void deleteWrongCustomerId() throws Exception {
            Assertions.assertThrows(RuntimeException.class,
                    () -> voucherDao.deleteOneByCustomerId(UUID.randomUUID(), voucher.getVoucherId()));
        }

        @Test
        @DisplayName("바우처 아이디는 없고, 고객 아이디는 유효할 때 -> 예외 발생")
        public void deleteWrongVoucherId() throws Exception {
            Assertions.assertThrows(RuntimeException.class,
                    () -> voucherDao.deleteOneByCustomerId(customer.getCustomerId(), UUID.randomUUID()));
        }

        @Test
        @DisplayName("바우처, 고객 아이디가 없는 아이디일 때")
        public void deleteWrongCustomerIdAndVoucherId() throws Exception {
            Assertions.assertThrows(RuntimeException.class,
                    () -> voucherDao.deleteOneByCustomerId(UUID.randomUUID(), UUID.randomUUID()));
        }
        @Test
        @DisplayName("바우처 아이디는 유효,고객 아이디가 Null -> 예외 발생")
        public void deleteNUllCustomerId() throws Exception {
            Assertions.assertThrows(RuntimeException.class,
                    () -> voucherDao.deleteOneByCustomerId(null, voucher.getVoucherId()));
        }

        @Test
        @DisplayName("바우처 아이디는 Null, 고객 아이디는 유효할 때 -> 예외 발생")
        public void deleteNullVoucherId() throws Exception {
            Assertions.assertThrows(RuntimeException.class,
                    () -> voucherDao.deleteOneByCustomerId(customer.getCustomerId(), null));
        }

        @Test
        @DisplayName("바우처, 고객 아이디가 Null일 때")
        public void deleteNullCustomerIdAndVoucherId() throws Exception {
            Assertions.assertThrows(RuntimeException.class,
                    () -> voucherDao.deleteOneByCustomerId(null, null));
        }
    }

    @Nested
    @DisplayName("바우처 타입으로 찾기")
    class findByVoucherType {
        @Test
        @DisplayName("바우처 타입으로 찾기 -> 성공")
        public void findByVoucherType() throws Exception {
            Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 20, customer.getCustomerId());
            Voucher voucher3 = new PercentDiscountVoucher(UUID.randomUUID(), 30, customer.getCustomerId());
            Voucher voucher4 = new PercentDiscountVoucher(UUID.randomUUID(), 25, customer.getCustomerId());
            Voucher voucher5 = new PercentDiscountVoucher(UUID.randomUUID(), 25, customer.getCustomerId());
            voucherDao.save(voucher2);
            voucherDao.save(voucher3);
            voucherDao.save(voucher4);
            voucherDao.save(voucher5);

            List<Voucher> byFixedVoucherType = voucherDao.findByVoucherType(VoucherType.FIXED);
            assertThat(byFixedVoucherType.size()).isEqualTo(2);

            List<Voucher> byPercentVoucherType = voucherDao.findByVoucherType(VoucherType.PERCENT);
            assertThat(byPercentVoucherType.size()).isEqualTo(3);
        }

        @Test
        @DisplayName("바우처 타입 null 입력 -> 예외 발생(NPE)")
        public void findByNullVoucherType() throws Exception {
            assertThrows(NullPointerException.class, () -> voucherDao.findByVoucherType(null));
        }
        @Test
        @DisplayName("이상한 바우처 타입 입력 -> 예외 발생 (IllegalArgumentException)")
        public void findByWrongVoucherType() throws Exception {
            assertThrows(IllegalArgumentException.class, () -> voucherDao.findByVoucherType(VoucherType.valueOf("None")));
        }
    }

    @Nested
    @DisplayName("A~B 사이에 생성된 바우처 조회")
    class findByCreatedAtBetween {
        @Test
        @DisplayName("정확한 입력 형식(yyyy-MM-dd)을 맞춰서 -> 성공")
        public void findByCreatedAtBetween() throws Exception {

            Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 20, customer.getCustomerId());
            Voucher voucher3 = new PercentDiscountVoucher(UUID.randomUUID(), 30, customer.getCustomerId());
            Voucher voucher4 = new PercentDiscountVoucher(UUID.randomUUID(), 25, customer.getCustomerId());
            Voucher voucher5 = new PercentDiscountVoucher(UUID.randomUUID(), 25, customer.getCustomerId());

            voucherDao.save(voucher2);
            voucherDao.save(voucher3);
            voucherDao.save(voucher4);
            voucherDao.save(voucher5);

            String date1 = "2022-01-31";
            String date2 = "2099-12-12";
            List<Voucher> byCreateAtBetween = voucherDao.findByCreatedAtBetween(date1, date2);
            List<Voucher> byCreateAtBetween2 = voucherDao.findByCreatedAtBetween(date2, date1);

            assertThat(byCreateAtBetween.size()).isEqualTo(5);
            assertThat(byCreateAtBetween2.size()).isEqualTo(5);

        }

        @Test
        @DisplayName("입력 형식이 LocalDateTime -> 실패")
        public void findByCreatedAtBetweenWrongInput() throws Exception {

            String date1 = "2022-01-31 00:00:00";
            String date2 = "2099-12-12";
            assertThrows(RuntimeException.class, () -> voucherDao.findByCreatedAtBetween(date1, date2));
            assertThrows(RuntimeException.class, () -> voucherDao.findByCreatedAtBetween(date2, date1));
        }

        @Test
        @DisplayName("입력 형식이 null -> 실패")
        public void findByCreatedAtBetweenNullInput() throws Exception {

            String date1 = null;
            String date2 = "2099-12-12";
            assertThrows(RuntimeException.class, () -> voucherDao.findByCreatedAtBetween(date1, date2));
            assertThrows(RuntimeException.class, () -> voucherDao.findByCreatedAtBetween(date2, date1));
        }

        @Test
        @DisplayName("입력 형식이 yyyy.MM.dd -> 실패")
        public void findByCreatedAtBetweenWrongInput2() throws Exception {

            String date1 = "2011.01.01";
            String date2 = "2099-12-12";
            assertThrows(RuntimeException.class, () -> voucherDao.findByCreatedAtBetween(date1, date2));
            assertThrows(RuntimeException.class, () -> voucherDao.findByCreatedAtBetween(date2, date1));
        }
    }

    @Nested
    @DisplayName("바우처 타입으로 찾기")
    class useVoucher {
        @Test
        @DisplayName("유효한 바우처를 사용 -> 바우처 상태는 Expired")
        public void useVoucher() throws Exception {
            voucher.useVoucher();
            assertThat(voucher.getVoucherStatus()).isEqualTo(VoucherStatus.EXPIRED);
        }

        @Test
        @DisplayName("바우처를 사용했다가 취소 -> 바우처 상태는 Valid")
        public void cancelVoucher() throws Exception {
            voucher.useVoucher();
            voucher.cancelVoucher();
            assertThat(voucher.getVoucherStatus()).isEqualTo(VoucherStatus.VALID);
        }

        @Test
        @DisplayName("이미 사용된 바우처를 사용 -> 예외 발생")
        public void useExpiredVoucher() throws Exception {
            voucher.useVoucher();
            assertThrows(IllegalArgumentException.class, () -> voucher.useVoucher());
        }

        @Test
        @DisplayName("사용되지 않은 바우처를 취소 -> 예외 발생")
        public void cancelValidVoucher() throws Exception {
            assertThrows(IllegalArgumentException.class, () -> voucher.cancelVoucher());
        }
    }
}
