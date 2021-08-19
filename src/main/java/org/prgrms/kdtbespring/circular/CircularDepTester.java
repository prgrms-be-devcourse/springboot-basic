package org.prgrms.kdtbespring.circular;

import org.prgrms.kdtbespring.IgnoreComponentScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class A{
    private B b;

    A(B b){
        this.b = b;
    }
}

class B{
    private A a;

    B(A a){
        this.a = a;
    }
}

@Configuration
@IgnoreComponentScan
class CircularConfig{
    @Bean
    public A a(B b){
        return new A(b);
    }

    @Bean
    public B b(A a){
        return new B(a);
    }
}

// 순환 참조 예시를 위한 클래스
public class CircularDepTester {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(CircularConfig.class);
    }
}
