package org.prgrms.kdt.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 순환의존 되지 않게 해야함 아래는 오류!
class A{
    private final B b;
    A(B b){
        this.b = b;
    }
}

class B{
    private final A a;
    B(A a){
        this.a = a;
    }
}

// Bean을 등록
//@Configuration
class CircularConfig {
    @Bean
    public A a(B b) {
        return new A(b);
    }

    @Bean
    B b(A a){
        return new B(a);
    }
}

public class CircularDepTester {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(CircularConfig.class);
    }


}
