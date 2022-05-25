package org.devcourse.voucher.customer.model;

import org.devcourse.voucher.application.customer.model.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class EmailTest {

    @Test
    @DisplayName("정상적인 이메일으로 생성시도시 성공하는지 테스트")
    void validEmailTest() {
        String address = "yongcheol@naver.com";

        Email email = new Email(address);
        assertThat(email.getAddress()).isEqualTo(address);
    }

    @Test
    @DisplayName("비정상적인 이메일로 생성시도시 예외가 발생하는지 테스트")
    void notValidEmailTest() {
        String[] addresses = {
                "",
                "hi",
                "hello@.com",
                "h.2.com",
                "@naver.com",
                "hmmm@",
                "test@com",
                "testtest.com",
                "test@testcom"
        };

        for (String address : addresses) {
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> new Email(address));
        }
    }

    @Test
    @DisplayName("같은 이메일을 비교할때 equals가 true를 반환하는지 테스트")
    void equalEmailTest() {
        Email stubEmail1 = new Email("test@test.com");
        Email stubEmail2 = new Email("test@test.com");

        assertThat(stubEmail1.equals(stubEmail2)).isTrue();
    }

    @Test
    @DisplayName("서로 다른 이메일을 비교할때 equals가 false를 반환하는지 테스트")
    void notEqualEmailTest() {
        Email stubEmail1 = new Email("test@test.com");
        Email stubEmail2 = new Email("test2@test.com");

        assertThat(stubEmail1.equals(stubEmail2)).isFalse();
    }

    @Test
    @DisplayName("toString을 호출시 address가 반환되는지 테스트")
    void toStringTest() {
        String stubAddress = "test@test.com";
        Email stubEmail = new Email(stubAddress);

        assertThat(stubEmail.toString()).hasToString(stubAddress);
    }
}