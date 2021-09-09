package org.prgrms.kdt;

import org.prgrms.kdt.command.CommandLineApplication;
import org.prgrms.kdt.voucher.repository.FileVoucherRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplicationContext {
    public static void main(final String[] args) {
        final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        applicationContext.getBean(CommandLineApplication.class).run(applicationContext.getBean(FileVoucherRepository.class));
        applicationContext.close();
    }
}
