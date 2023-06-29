package com.prgms.springbootbasic.util;

import com.prgms.springbootbasic.AppConfig;
import com.prgms.springbootbasic.controller.MenuController;
import com.prgms.springbootbasic.controller.VoucherController;
import com.prgms.springbootbasic.controller.VoucherCreateController;
import com.prgms.springbootbasic.controller.VoucherListController;
import com.prgms.springbootbasic.controller.MemberController;
import com.prgms.springbootbasic.controller.BlackListMemberController;

import com.prgms.springbootbasic.exception.NoSuchFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileNotFoundException;

public interface VoucherApplication {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    Logger logger = LoggerFactory.getLogger(VoucherApplication.class);

    static MenuController menuController() {
        return ac.getBean(MenuController.class);
    }

    static MemberController blackListMemberController() { return ac.getBean(BlackListMemberController.class); }
    static VoucherController voucherCreateController() { return ac.getBean(VoucherCreateController.class); }

    static VoucherController voucherListController() { return ac.getBean(VoucherListController.class); }

    static File file(String location) throws FileNotFoundException {
        try {
            Resource resource = ac.getResource(location);
            return resource.getFile();
        } catch (Exception e) {
            logger.error("파일을 찾을 수가 없습니다. file path : {}", location);
            throw new NoSuchFileException(ExceptionMessage.NO_SUCH_FILE);
        }

    }

}
