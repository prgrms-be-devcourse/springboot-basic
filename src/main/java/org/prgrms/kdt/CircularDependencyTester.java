package org.prgrms.kdt;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class A {
    private final B b;

    A(B b) {
        this.b = b;
    }
}

class B {
    private final A a;
    B(A a) {
        this.a = a;
    }
}

// 순환참조가 이뤄짐
@Configuration
class CircularConfig {
    @Bean
    public A a(B b) {
        return new A(b);
    }
    @Bean
    public B b (A a) {
        return new B(a);
    }
}

public class CircularDependencyTester {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(CircularConfig.class);
    }
}
