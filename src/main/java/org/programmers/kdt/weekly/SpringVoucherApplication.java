package org.programmers.kdt.weekly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringVoucherApplication {
    public static void main(String[] args) {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        var applicationContext = SpringApplication.run(SpringVoucherApplication.class, args);
        var commandLineApplication = applicationContext.getBean(CommandLineApplication.class);
        commandLineApplication.start();
    }
}
