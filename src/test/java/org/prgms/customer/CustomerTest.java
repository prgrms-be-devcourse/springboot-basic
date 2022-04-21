package org.prgms.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.io.FileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

@SpringJUnitConfig(value = CustomerTest.Config.class)
class CustomerTest {

    @Configuration
    @ComponentScan(basePackages = "org.prgms.io")
    static class Config {

    }

    @Autowired
    private FileReader fileReader;

    @Test
    @DisplayName("고객 블랙리스트 조회를 통한 Customer객체 테스트")
    void customerTest() throws Exception {
        List<Customer> customers = fileReader.readFile();
        Assertions.assertThat(customers.get(0).name()).isEqualTo("홍길동");
        Assertions.assertThat(customers.get(1).name()).isEqualTo("박철수");
        Assertions.assertThat(customers.get(2).name()).isEqualTo("김지영");
    }

    @Test
    @DisplayName("Null 인자, 빈 공백으로 Customer 초기화 시킬 수 없음")
    void nullTest() {
        Assertions.assertThatThrownBy(() -> new Customer(null, null, null))
                .isInstanceOf(NullPointerException.class);
        Assertions.assertThatThrownBy(() -> new Customer(UUID.randomUUID(), "", ""))
                .isInstanceOf(IllegalArgumentException.class);
    }
}