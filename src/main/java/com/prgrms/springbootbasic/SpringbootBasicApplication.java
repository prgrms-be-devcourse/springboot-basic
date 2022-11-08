package com.prgrms.springbootbasic;

import com.prgrms.springbootbasic.app.VoucherApplication;
import com.prgrms.springbootbasic.common.config.AppConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringbootBasicApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        VoucherApplication console = applicationContext.getBean(VoucherApplication.class);
        console.runLifecycle();
    }

}
