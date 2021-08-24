package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.io.console.Console;
import com.programmers.kdtspringorder.order.OrderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

import java.text.MessageFormat;
import java.util.List;

@SpringBootApplication
public class KdtSpringOrderApplication {

    public static void main(String[] args) {
        new SpringApplication(KdtSpringOrderApplication.class).run();
    }
}
