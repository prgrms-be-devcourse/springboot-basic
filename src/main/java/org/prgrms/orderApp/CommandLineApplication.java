package org.prgrms.orderApp;

import org.prgrms.orderApp.CMDProgramForOrderApp.console.Script;
import org.prgrms.orderApp.CMDProgramForOrderApp.util.CheckInvalid;
import org.prgrms.orderApp.CMDProgramForOrderApp.util.MonguDbFuntionType;
import org.prgrms.orderApp.config.AppConfiguration;
import org.prgrms.orderApp.CMDProgramForOrderApp.service.CMDProgramService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(AppConfiguration.class);
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var cmdProgramService = applicationContext.getBean(CMDProgramService.class);
        var checkInvalid = applicationContext.getBean(CheckInvalid.class);
        // app start
        voucherProgramApp(cmdProgramService, checkInvalid);
    }

    public static void voucherProgramApp(CMDProgramService cmdProgramService, CheckInvalid checkInvalid){
        int exit_flag = -1;
        String userSelectedMenu ;
        // 본 프로그램 안내
        cmdProgramService.introduceApp();

        try {
            while (true) {
                Thread.sleep(500);
                switch (cmdProgramService.selectedMainMenu()) {
                    case "CREATE_VOUCHER":
                        cmdProgramService.createVoucher();
                        break;
                    case "VOUCHER_LIST":
                        cmdProgramService.showVouchers();
                        break;
                    case "CREATE_ORDER":
                        cmdProgramService.createOrder();
                        break;
                    case "ORDER_LIST" :
                        cmdProgramService.showOrders();
                        break;
                    case "CONNECT_FILE_DB":

                        userSelectedMenu = cmdProgramService.selectedMonguMainMenu();
                        checkInvalid.checkInteger(userSelectedMenu);
                        switch (MonguDbFuntionType.getMenuName(Integer.parseInt(userSelectedMenu))){
                            case "COLLECTION_CONNECT" :
                                break;
                            case "COLLECTION_CREATE" :
                                cmdProgramService.createCollection();
                                break;
                            case "COLLECTION_DROP" :
                                cmdProgramService.dropCollection();
                                break;
                            default:
                                cmdProgramService.SelectedInvalidMenuNumber();
                        }
                        break;
                    case "EXIT":
                        if (cmdProgramService.exit().equals("yes")) {
                            exit_flag = 1;
                        }
                        break;
                    default:
                        cmdProgramService.selectedInvalidMenu();
                        break;
                }
                if (exit_flag == 1) {
                    break;
                }
            }
        }catch(Exception e){
            cmdProgramService.apologizeMessage();
            System.out.println(e);
        }finally {
            cmdProgramService.sayBye();
        }

    }

}
