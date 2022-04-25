package me.programmers.springboot.basic.springbootbasic.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Component
public class BlackListCustomer implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(BlackListCustomer.class);
    private static final String csvFile = "file:customer_blacklist.csv";

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public void showBlackList() {
        try {
            Resource resource = context.getResource(csvFile);
            List<String> blackList = Files.readAllLines(resource.getFile().toPath());
            System.out.println("customer blacklist " + blackList.stream().reduce("", (a, b) -> a + "\n" + b));
        } catch (IOException e) {
            logger.error("해당 파일을 찾을 수 없습니다. " + csvFile);
        }
    }

}
