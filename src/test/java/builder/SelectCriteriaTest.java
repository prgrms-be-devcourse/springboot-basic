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
class SelectCriteriaTest {
    @Test
    void selectQueryAllBuilder_test() {
        Select selectQueryAll = Select.builder()
                .select(Customer.class)
                .from(Customer.class)
                .build();

        Assertions.assertEquals("SELECT customerId, email, name, createdAt FROM customer",
                selectQueryAll.getQuery());
        System.out.println(selectQueryAll.getQuery());
    }

    @Test
    void selectQueryAllBuilder_voucher_test() {
        Select selectQueryAll = Select.builder()
                .select("*")
                .from(Voucher.class)
                .build();

        Assertions.assertEquals("SELECT * FROM voucher", selectQueryAll.getQuery());
        System.out.println(selectQueryAll.getQuery());
    }

    @Test
    void selectQueryWhereBuilder_voucher_test() {
        Select select = Select.builder()
                .select("*")
                .from(Voucher.class)
                .where(
                        Where.builder("voucher_id", EQ, UUID_TO_BIN(UUID.randomUUID()))
                                .build()
                )
                .build();

        Assertions.assertEquals("SELECT * FROM voucher WHERE voucher_id = [B@3c87521", select.getQuery());
        System.out.println(select.getQuery());
    }




    @Test
    void selectQueryWhereBuilder_test() {
        Select selectQuery = Select.builder()
                .select(Customer.class)
                .from(Customer.class)
                .where(
                        Where.builder("email", EQ, "abc1234@naver.com")
                                .build()
                )
                .build();

        Assertions.assertEquals("SELECT customerId, email, name, createdAt FROM customer WHERE email = abc1234@naver.com",
                selectQuery.getQuery());
        System.out.println(selectQuery.getQuery());
    }
}
