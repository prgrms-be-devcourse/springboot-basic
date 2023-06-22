package org.prgrms.kdt;

import org.prgrms.kdt.voucher.VoucherManagement;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
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
