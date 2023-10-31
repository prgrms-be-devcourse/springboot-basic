package devcourse.springbootbasic;

import devcourse.springbootbasic.repository.customer.JdbcCustomerRepository;
import devcourse.springbootbasic.repository.voucher.JdbcVoucherRepository;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JdbcCustomerRepository.class, JdbcVoucherRepository.class})
public abstract class JdbcRepositoryTest {
}
