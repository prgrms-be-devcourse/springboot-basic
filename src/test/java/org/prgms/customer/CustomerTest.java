package org.prgms.customer;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.io.FileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig
class CustomerTest {
    @Configuration
    @ComponentScan(basePackages = "org.prgms.io")
    static class Config {
    }

    @Autowired
    private FileReader fileReader;

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("고객 블랙리스트 조회를 통한 Customer객체 테스트")
    void customerTest() throws Exception {
        List<Customer> customers = fileReader.readFile();
        MatcherAssert.assertThat(customers.get(0).name(), Matchers.is("홍길동"));
        MatcherAssert.assertThat(customers.get(1).name(), Matchers.is("박철수"));
        MatcherAssert.assertThat(customers.get(2).name(), Matchers.is("김지영"));
    }
}