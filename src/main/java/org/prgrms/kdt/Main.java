package org.prgrms.kdt;

import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.repository.MemoryVoucherRepository;
import org.prgrms.kdt.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        applicationContext.getBean(VoucherManagement.class).run();
    }
}
