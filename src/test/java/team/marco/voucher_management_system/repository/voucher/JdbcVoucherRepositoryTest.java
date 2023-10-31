package team.marco.voucher_management_system.repository.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import team.marco.voucher_management_system.domain.customer.Customer;
import team.marco.voucher_management_system.domain.voucher.FixedAmountVoucher;
import team.marco.voucher_management_system.domain.voucher.PercentDiscountVoucher;
import team.marco.voucher_management_system.domain.voucher.Voucher;
import team.marco.voucher_management_system.repository.custromer.JdbcCustomerRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("prod")
class JdbcVoucherRepositoryTest {
    @Autowired
    private JdbcVoucherRepository voucherRepository;

    @Autowired
    private JdbcCustomerRepository customerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        String query = "DELETE FROM vouchers";
        jdbcTemplate.update(query);
    }

    @Test
    @DisplayName("바우처 생성 시 생성된 바우처를  반환")
    void 쿠폰_생성_성공() {
        // 1,000원 할인 쿠폰 생성
        int discountAmount = 1_000;
        Voucher voucher = new FixedAmountVoucher(discountAmount);
        Voucher saved = voucherRepository.save(voucher);

        // 생성된 바우처 반환
        assertThat(saved).isEqualTo(voucher);
    }

    @Test
    void 전체_쿠폰_목록_조회_성공() {
        // 1,000원 할인 쿠폰, 10% 할인 쿠폰 생성
        int discountAmount = 1_000;
        Voucher voucher = new FixedAmountVoucher(discountAmount);
        voucherRepository.save(voucher);

        int discountPercent = 10;
        Voucher voucher2 = new PercentDiscountVoucher(discountPercent);
        voucherRepository.save(voucher2);

        // 전체 쿠폰 목록 조회
        List<Voucher> vouchers = voucherRepository.findAll();

        // 저장된 2개의 쿠폰이 조회
        assertThat(vouchers).hasSize(2);
    }

    @Test
    void 쿠폰_아이디로_조회_성공() {
        // 1,000원 할인 쿠폰 생성
        int discountAmount = 1_000;
        Voucher voucher = new FixedAmountVoucher(discountAmount);
        voucherRepository.save(voucher);

        // 쿠폰 번호로 조회
        Voucher found = voucherRepository.findById(voucher.getId()).get();

        // 저장한 쿠폰과 동일한 쿠폰 반환
        assertThat(found.getId()).isEqualTo(voucher.getId());
    }

    @Test
    void 쿠폰_소지자_아이디로_조회_성공() {
        // 쿠폰 소지자와 함께 1,000원 할인 쿠폰 생성
        int discountAmount = 1_000;
        Customer customer = createCustomer("customer", "customer@gmail.com");
        customerRepository.insert(customer);

        Voucher voucher = new FixedAmountVoucher(discountAmount, customer.getId());
        voucherRepository.save(voucher);

        // 쿠폰 소지자 아이디로 조회
        List<Voucher> found = voucherRepository.findByOwner(customer.getId());

        // 저장한 쿠폰과 동일한 쿠폰 반환
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getId()).isEqualTo(voucher.getId());
    }

    @Test
    void 쿠폰_정보_수정_성공() {
        // 1,000원 할인 쿠폰 생성
        int discountAmount = 1_000;
        Voucher voucher = new FixedAmountVoucher(discountAmount);
        voucherRepository.save(voucher);

        // 쿠폰 소지자 할당 (쿠폰 정보 수정)
        Customer customer = createCustomer("customer", "customer@gmail.com");
        customerRepository.insert(customer);
        voucher.assigneOwner(customer.getId());

        Voucher updated = voucherRepository.update(voucher);

        // 변경된 쿠폰 정보가 저장
        assertThat(updated.getId()).isEqualTo(voucher.getId());
        assertThat(updated.getOwnerId()).isEqualTo(customer.getId());
    }

    @Test
    void 쿠폰_번호로_삭제_성공() {
        // 1,000원 할인 쿠폰 생성
        int discountAmount = 1_000;
        Voucher voucher = new FixedAmountVoucher(discountAmount);
        voucherRepository.save(voucher);

        // 쿠폰 삭제
        voucherRepository.deleteById(voucher.getId());

        // 쿠폰이 삭제됨
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers).hasSize(0);
    }

    private Customer createCustomer(String name, String email) {
        return new Customer
                .Builder(name, email)
                .build();
    }

}