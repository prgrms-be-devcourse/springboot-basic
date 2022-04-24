package org.prgms.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.prgms.io.FileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CustomerTest {

    @Autowired
    private FileReader fileReader;

    @Test
    @DisplayName("고객 블랙리스트 조회를 통한 Customer객체 테스트")
    void customerTest() throws Exception {
        List<Customer> customers = fileReader.readFile();

        assertThat(customers).extracting(Customer::name, Customer::email).containsExactlyInAnyOrder(
                tuple("홍길동", "hongil@gmail.com"),
                tuple("박철수", "cheolsu@naver.com"),
                tuple("김지영", "jeeyoung@yahoo.co.kr")
        );
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("고객 id는 null 일 수 없음")
    void idNullTest(UUID id) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Customer(id, "name", "email@email"));
    }


    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("고객 이름은 null이거나 비어있을 수 없음")
    void nameNullTest(String name) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Customer(UUID.randomUUID(), name, "email@email"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("고객 이메일은 null이거나 비어있을 수 없음")
    void nullTest(String email) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Customer(UUID.randomUUID(), "name", email));
    }
}