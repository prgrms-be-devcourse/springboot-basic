package org.prgms;

import org.prgms.black.BlackProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.text.MessageFormat;

@SpringBootApplication
@ComponentScan(basePackageClasses = {BlackProperties.class})
public class KdtApplication {

    public static void main(String[] args) {
        var springApplication = new SpringApplication(KdtApplication.class);
        springApplication.setAdditionalProfiles("dev");
        var context = springApplication.run();

        var BlackProperties = context.getBean(BlackProperties.class);
        System.out.println(MessageFormat.format("getVersion -> {0}", BlackProperties.getVersion()));
        System.out.println(MessageFormat.format("getTitile -> {0}", BlackProperties.getTitle()));

    }
}

