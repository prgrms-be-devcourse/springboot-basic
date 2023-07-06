package com.prgms.springbootbasic.util;

import com.prgms.springbootbasic.AppConfig;

import com.prgms.springbootbasic.exception.NoSuchFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

public interface Application {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    Logger logger = LoggerFactory.getLogger(Application.class);

    static File file(String location) {
        try {
            Resource resource = ac.getResource(location);
            return resource.getFile();
        } catch (IOException e) {
            logger.error("파일을 찾을 수가 없습니다. file path : {}", location);
            throw new NoSuchFileException(ExceptionMessage.NO_SUCH_FILE);
        }
    }

}
