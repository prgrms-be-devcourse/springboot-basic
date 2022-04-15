package org.prgrms.kdt;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.InputConsole;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.io.OutputConsole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);
        Input input = new InputConsole();
        Output output = new OutputConsole();
        new CommandLineRunner(applicationContext, input, output).run();
    }
}
