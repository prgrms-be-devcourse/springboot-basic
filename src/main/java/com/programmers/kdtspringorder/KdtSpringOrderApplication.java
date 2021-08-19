package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.io.console.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KdtSpringOrderApplication {

    private static final Logger logger = LoggerFactory.getLogger(KdtSpringOrderApplication.class);

    public static void main(String[] args) {
        Console console = new Console();
        new CommandLineApplication(console, console).run();

    }
}
