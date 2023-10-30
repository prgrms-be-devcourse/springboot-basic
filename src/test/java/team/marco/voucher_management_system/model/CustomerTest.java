package team.marco.voucher_management_system.model;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.time.LocalDateTime;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CustomerTest {
    private static final String INVALID_NAME_MESSAGE = "이름은 공백일 수 없습니다.";
    private static final String INVALID_EMAIL_MESSAGE = "이메일 형식이 올바르지 않습니다.";

    private Customer generateCustomer() {
        return new Customer("test", "test@test.test");
    }

    @Nested
    @DisplayName("고객 생성 이름 테스트")
    class TestCreationName {
        @Test
        @DisplayName("이름이 빈 문자열이 아니면 생성에 성공해야 한다.")
        void success() {
            // given
            String name = "test";
            String email = "test@test.test";

            // when
            ThrowingCallable targetMethod = () -> new Customer(name, email);

            // then
            assertThatNoException().isThrownBy(targetMethod);
        }

        @Test
        @DisplayName("이름이 빈 문자열이면 생성에 실패해야 한다.")
        void fail() {
            String name = "";
            String email = "test@test.test";

            // when
            ThrowingCallable targetMethod = () -> new Customer(name, email);

            // then
            assertThatIllegalArgumentException().isThrownBy(targetMethod)
                    .withMessage(INVALID_NAME_MESSAGE);
        }
    }

    @Nested
    @DisplayName("고객 생성 이메일 테스트")
    class TestCreationEmail {
        @Test
        @DisplayName("이메일이 형식에 맞으면 생성에 성공해야 한다.")
        void success() {
            // given
            String name = "test";
            String email = "test@test.test";

            // when
            ThrowingCallable targetMethod = () -> new Customer(name, email);

            // then
            assertThatNoException().isThrownBy(targetMethod);
        }

        @Test
        @DisplayName("이메일이 형식에 맞지 않으면 생성에 실패해야 한다.")
        void fail() {
            String name = "test";
            String email = "test";

            // when
            ThrowingCallable targetMethod = () -> new Customer(name, email);

            // then
            assertThatIllegalArgumentException().isThrownBy(targetMethod)
                    .withMessage(INVALID_EMAIL_MESSAGE);
        }
    }

    @Nested
    @DisplayName("고객 이름 변경 테스트")
    class TestChangeName {
        @Test
        @DisplayName("이름이 빈 문자열이 아니면 변경에 성공해야 한다.")
        void success() {
            // given
            Customer customer = generateCustomer();
            String name = "test2";

            // when
            customer.changeName(name);

            // then
            String customerName = customer.getName();

            assertThat(customerName, is(name));
        }

        @Test
        @DisplayName("이름이 빈 문자열이면 변경에 실패해야 한다.")
        void fail() {
            Customer customer = generateCustomer();
            String name = "";

            // when
            ThrowingCallable targetMethod = () -> customer.changeName(name);

            // then
            assertThatIllegalArgumentException().isThrownBy(targetMethod)
                    .withMessage(INVALID_NAME_MESSAGE);
        }
    }

    @Test
    @DisplayName("로그인을 하면 lastLoginAt 필드 값이 바뀌어야 한다.")
    void testLogin() {
        // given
        Customer customer = generateCustomer();
        LocalDateTime beforeLastLoginAt = customer.getLastLoginAt();

        // when
        customer.login();

        // then
        LocalDateTime afterLastLoginAt = customer.getLastLoginAt();

        assertThat(afterLastLoginAt, notNullValue());
        assertThat(afterLastLoginAt, is(not(beforeLastLoginAt)));
    }
}
