package org.prgrms.kdt;

import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.voucher.VoucherManagement;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        applicationContext.getBean(VoucherManagement.class).run();
//        ConfigurableEnvironment environment = applicationContext.getEnvironment();
//        String property = environment.getProperty("kdt.file-path");
//        System.out.println("property = " + property);
//        Resource resource = applicationContext.getResource("file:./voucher-list.txt");
//        System.out.println("resource.getClass().getCanonicalName() = " + resource.getClass().getCanonicalName());
//        File file = resource.getFile();
//        List<String> strings = Files.readAllLines(Path.of(file.getPath()));
//        System.out.println("strings = " + strings);
//        VoucherRepository bean = applicationContext.getBean(VoucherRepository.class);
//        System.out.println("bean.getClass().getCanonicalName() = " + bean.getClass().getCanonicalName());
    }
}
