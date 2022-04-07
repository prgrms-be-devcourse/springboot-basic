package org.prgrms.deukyun.voucherapp.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 바우처 애플리케이션 - 애플리케이션의 템플릿
 */
@Component
public class VoucherApp {

    private final Logger logger = LoggerFactory.getLogger(VoucherApp.class);
    private final VoucherAppRunner voucherAppRunner;

    public VoucherApp(VoucherAppRunner voucherAppRunner) {
        this.voucherAppRunner = voucherAppRunner;
    }

    public void run() {
        logger.info("app launched");
        boolean isBreak = false;
        do {
            try{
                voucherAppRunner.run();
                isBreak = voucherAppRunner.isExit();
            }catch (RuntimeException ex){
                System.out.println(ex.getMessage());
            }catch (Exception ex){
                System.out.println("알 수 없는 에러 발생. 프로그램 종료");
                isBreak = true;
            }
        }while(!isBreak);
    }


    private boolean isExit() {
        return false;
    }
}
