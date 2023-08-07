package builder;

import com.prgms.voucher.voucherproject.domain.customer.Customer;
import com.prgms.voucher.voucherproject.domain.voucher.FixedAmountVoucher;
import com.prgms.voucher.voucherproject.domain.voucher.PercentDiscountVoucher;
import com.prgms.voucher.voucherproject.repository.customer.CustomerRepository;
import com.prgms.voucher.voucherproject.repository.customer.JdbcCustomerRepository;
import com.prgms.voucher.voucherproject.repository.voucher.JdbcVoucherRepository;
import com.prgms.voucher.voucherproject.repository.voucher.VoucherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.UUID;

@ActiveProfiles("test")
@JdbcTest
@ContextConfiguration(classes = {JdbcCustomerRepository.class, JdbcVoucherRepository.class}) // 인터페이스라 못 불러오는걸 직접 불러옴
class InsertCriteriaTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Test
    void InsertQueryBuilder_customer_test() {
        Customer customer = new Customer(UUID.randomUUID(), "temp@naver.com", "tempName", LocalDateTime.now());
        customerRepository.save(customer);
    }

    @Test
    void InsertQueryBuilder_fixedVoucher_test2() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(400L);
        voucherRepository.save(fixedAmountVoucher);
    }

    @Test
    void InsertQueryBuilder_percentVoucher_test2() {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(15);
        voucherRepository.save(percentDiscountVoucher);
    }
}
