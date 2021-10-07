package org.prgrms.orderApp.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
/**
 * Created by yunyun on 2021/10/06.
 */
class CustomerTest {

    @Test
    @DisplayName("customer 의 이름 필드 만을 변경할 수 있다.")
    void changeNameTest() {
        // Given
        var customer = new Customer(UUID.randomUUID(), "test_0907", "default@test.com", LocalDateTime.now(), LocalDateTime.now());

        // When
        var updateName = "updateName";
        customer.changeName(updateName);

        // Then
        assertThat(customer.getName(), is(updateName));
    }

    @Test
    @DisplayName("login 함수가 호출되면, lastLoginAt 필드의 값은 업데이트된 시각으로 저장된다.")
    void loginTest() {
        // Given
        var createdDate = LocalDateTime.now();
        var customer = new Customer(UUID.randomUUID(), "test_0907", "default@test.com", createdDate, createdDate);

        // When
        customer.login();

        // Then
        assertThat(customer.getCreatedAt(), is(createdDate));
        assertThat(customer.getLastLoginAt(), not(createdDate));
    }

    @Test
    @DisplayName("name 필드는 null 을 허용하지 않는다.")
    void validatingNameForNull(){
        // Given
        String name = null;

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer(UUID.randomUUID(), name, "default@test.com", LocalDateTime.now(), LocalDateTime.now());
        });
    }

    @Test
    @DisplayName("name 필드는 1자 이상이어야 한다.")
    void validatingNameForLength(){
        // Given
        String name = "";

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer(UUID.randomUUID(), name, "default@test.com", LocalDateTime.now(), LocalDateTime.now());
        });
    }

    @Test
    @DisplayName("email 필드는 이메일 형식만 허용된다.")
    void validatingEmailForFormat(){
        // Given
        String emptyEmail = "";
        String emailWithoutAt = "test"; // At = @ , emailWithout@ 이라고 변수명을 짓고 싶었습니다.
        String emailExample = "test@test"; // 적절한 변수명을 작성하지 못하였습니다. 작성하고 싶었던 변수명의 의미는 @ 뒤에 글자가 없는 이메일 형식
        String emailInKorean = "이메일@test.com";

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer(UUID.randomUUID(), "test_0907", emptyEmail, LocalDateTime.now(), LocalDateTime.now());
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer(UUID.randomUUID(), "test_0907", emailWithoutAt, LocalDateTime.now(), LocalDateTime.now());
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer(UUID.randomUUID(), "test_0907", emailExample, LocalDateTime.now(), LocalDateTime.now());
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer(UUID.randomUUID(), "test_0907", emailInKorean, LocalDateTime.now(), LocalDateTime.now());
        });
    }

}