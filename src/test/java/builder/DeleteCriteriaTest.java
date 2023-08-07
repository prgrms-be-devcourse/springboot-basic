package builder;

import com.prgms.voucher.voucherproject.domain.customer.Customer;
import com.prgms.voucher.voucherproject.domain.voucher.Voucher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static builder.Operator.Type.EQ;
import static com.prgms.voucher.voucherproject.util.JdbcUtils.UUID_TO_BIN;

@Transactional
class DeleteCriteriaTest {
    @Test
    void deleteQueryWhereBuilder_test() {
        Delete delete = Delete.from(Customer.class)
                .where(
                        Where.builder("email", EQ, "abc1234@naver.com")
                                .build()
                )
                .build();

        Assertions.assertEquals("DELETE FROM customer WHERE email = abc1234@naver.com", delete.getQuery());
        System.out.println(delete.getQuery());
    }

    @Test
    void deleteQueryWhereBuilder_voucher_test() {
        //   DELETE FROM voucher WHERE voucher_id = ?
        Delete delete = Delete.from(Voucher.class)
                .where(
                        Where.builder("voucher_id", EQ, UUID_TO_BIN(UUID.randomUUID()))
                                .build()
                )
                .build();

        Assertions.assertEquals("DELETE FROM voucher WHERE voucher_id = [B@212bf671", delete.getQuery());
        System.out.println(delete.getQuery());
    }
}
