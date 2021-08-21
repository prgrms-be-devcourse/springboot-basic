package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.io.console.Console;
import com.programmers.kdtspringorder.order.OrderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.text.MessageFormat;
import java.util.List;

@SpringBootApplication
public class KdtSpringOrderApplication {

    private static final Logger logger = LoggerFactory.getLogger(KdtSpringOrderApplication.class);

    public static void main(String[] args) {
        Console console = new Console();
        new CommandLineApplication(console, console).run();


    }
}
