package org.programmers.springbootbasic;

import lombok.RequiredArgsConstructor;
import org.programmers.springbootbasic.console.Dispatcher;
import org.programmers.springbootbasic.console.Input;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBasicApplication.class, args);
    }
}
