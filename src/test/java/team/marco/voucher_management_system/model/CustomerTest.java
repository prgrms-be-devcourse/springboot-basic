package team.marco.voucher_management_system.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;
import static org.testcontainers.shaded.org.hamcrest.Matchers.is;
import static org.testcontainers.shaded.org.hamcrest.Matchers.not;
import static org.testcontainers.shaded.org.hamcrest.Matchers.notNullValue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CustomerTest {

    private Customer generateCustomer() {
        return new Customer("test", "test@test.test");
    }

    @Nested
    @DisplayName("고객 생성 이름 테스트")
    class TestCreationName {
        @Test
        @DisplayName("이름이 빈 문자열이 아니면 생성에 성공해야한다.")
        void success() {
            // given
            String name = "test";
            String email = "test@test.test";

            // when
            // then
            new Customer(name, email);
        }

        @Test
        @DisplayName("이름이 빈 문자열이면 생성에 실패해야한다.")
        void fail() {
            String name = "";
            String email = "test@test.test";

            // when
            // then
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, email));
        }
    }

    @Nested
    @DisplayName("고객 생성 이메일 테스트")
    class TestCreationEmail {
        @Test
        @DisplayName("이메일이 형식에 맞으면 생성에 성공해야한다.")
        void success() {
            // given
            String name = "test";
            String email = "test@test.test";

            // when
            // then
            new Customer(name, email);
        }

        @Test
        @DisplayName("이메일이 형식에 맞지 않으면 생성에 실패해야한다.")
        void fail() {
            String name = "test";
            String email = "test";

            // when
            // then
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, email));
        }
    }

    @Nested
    @DisplayName("고객 이름 변경 테스트")
    class TestChangeName {
        @Test
        @DisplayName("이름이 빈 문자열이 아니면 변경에 성공해야한다.")
        void success() {
            // given
            Customer customer = generateCustomer();
            String name = "test2";

            // when
            // then
            customer.changeName(name);
        }

        @Test
        @DisplayName("이름이 빈 문자열이면 변경에 실패해야한다.")
        void fail() {
            Customer customer = generateCustomer();
            String name = "";

            // when
            // then
            assertThrows(IllegalArgumentException.class, () -> customer.changeName(name));
        }
    }

    @Test
    @DisplayName("로그인을 하면 lastLoginAt 필드 값이 바뀌어야한다.")
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
