package programmers.org.kdt;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import programmers.org.kdt.engine.CommandLine;
import programmers.org.kdt.engine.io.Console;
import programmers.org.kdt.engine.io.Console_terminal;
import programmers.org.kdt.engine.voucher.VoucherService;

public class CommandLineApplication {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        // Voucher
        var voucherService = applicationContext.getBean(VoucherService.class);

        // Console
        var console = new Console();
        //var console = new Console_terminal();

        //run
        var commandLine = new CommandLine(voucherService, console);
        commandLine.run();

        //close
        console.close();
        applicationContext.close();
    }
}
