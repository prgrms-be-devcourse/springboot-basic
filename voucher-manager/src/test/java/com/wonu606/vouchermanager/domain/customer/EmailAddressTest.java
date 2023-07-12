package com.wonu606.vouchermanager.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wonu606.vouchermanager.domain.customer.emailAddress.EmailAddress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("EmailAddress 테스트")
class EmailAddressTest {

    @DisplayName("생성 시_유효한 이메일주소이라면_객체를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"hello@daum.net", "hello@daum.co.kr", "test@example.com"})
    public void constructor_ValidEmailAddress_Created(String validAddress) {
        // When
        EmailAddress actualEmailAddress = new EmailAddress(validAddress);

        // Then
        assertThat(actualEmailAddress.getAddress()).isEqualTo(validAddress);
    }

    @DisplayName("생성 시_잘못된 이메일주소이라면_예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"hello.@daum.net", "hello@daum..co.kr", "test@example.com1"})
    public void constructor_InvalidEmailAddress_Exception(String invalidAddress) {
        // When & Then
        assertThatThrownBy(() -> new EmailAddress(invalidAddress))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 이메일 주소입니다: " + invalidAddress);
    }
}
