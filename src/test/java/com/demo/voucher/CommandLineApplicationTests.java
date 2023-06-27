package com.demo.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootTest
class CommandLineApplicationTests {

    @Test
    @DisplayName("Spring IoC 컨테이너에서 Controller, Repository, Service Bean 객체들이 생성되어 있는지 검증하는 테스트")
    void contextLoads() {
        // given
        String[] beansExpected = {"voucherController", "memoryVoucherRepository", "voucherServiceImpl"};

        // when
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CommandLineApplication.class);
        String[] beanNames = applicationContext.getBeanDefinitionNames();

        // then
        Assertions.assertThat(beanNames)
                .containsAll(Arrays.stream(beansExpected).collect(Collectors.toList()));
    }

}
