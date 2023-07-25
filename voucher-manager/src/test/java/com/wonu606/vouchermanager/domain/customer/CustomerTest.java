package com.wonu606.vouchermanager.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;

import com.wonu606.vouchermanager.domain.customer.email.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Customer 테스트")
class CustomerTest {

    @DisplayName("생성 시_이메일 주소가 들어오면_객체가 생성된다.")
    @ParameterizedTest
    @ValueSource(strings = {"hello@daum.net", "hello@daum.co.kr", "test@example.com"})
    public void constructor_EmailAddress_Success() {
        // Given
        String validAddress = "test@example.com";
        Email email = new Email(validAddress);

        // When
        Customer customer = new Customer(email, "tempNickName");

        // Then
        assertThat(customer.getEmailAddress()).isEqualTo(email.getAddress());
    }
}
