package org.prgrms.orderApp.CMDApplication;

import org.prgrms.orderApp.CMDApplication.service.BasicCMDService;
import org.prgrms.orderApp.CMDApplication.service.MonguDbCMDService;
import org.prgrms.orderApp.CMDApplication.service.OrderCMDService;
import org.prgrms.orderApp.CMDApplication.service.VoucherCMDService;
import org.prgrms.orderApp.CMDApplication.util.CheckInvalid;
import org.prgrms.orderApp.CMDApplication.util.MonguDbFuntionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CMDApplication {

    @Autowired
    private BasicCMDService basicCMDService;

    @Autowired
    private MonguDbCMDService monguDbCMDService;

    @Autowired
    private OrderCMDService orderCMDService;

    @Autowired
    private VoucherCMDService voucherCMDService;

    @Autowired
    private CheckInvalid checkInvalid;


    public void CMDAppStart(){
        int exit_flag = -1;
        String userSelectedMenu ;
        // 본 프로그램 안내
        basicCMDService.introduceApp();

        try {
            while (true) {
                Thread.sleep(500);
                switch (basicCMDService.selectedMainMenu()) {
                    case "CREATE_VOUCHER":
                        voucherCMDService.createVoucher();
                        break;
                    case "VOUCHER_LIST":
                        voucherCMDService.showVouchers();
                        break;
                    case "CREATE_ORDER":
                        orderCMDService.createOrder();
                        break;
                    case "ORDER_LIST" :
                        orderCMDService.showOrders();
                        break;
                    case "CONNECT_FILE_DB":

                        userSelectedMenu = monguDbCMDService.selectedMonguMainMenu();
                        checkInvalid.checkInteger(userSelectedMenu);
                        switch (MonguDbFuntionType.getMenuName(Integer.parseInt(userSelectedMenu))){
                            case "COLLECTION_CONNECT" :
                                break;
                            case "COLLECTION_CREATE" :
                                monguDbCMDService.createCollection();
                                break;
                            case "COLLECTION_DROP" :
                                monguDbCMDService.dropCollection();
                                break;
                            default:
                                basicCMDService.SelectedInvalidMenuNumber();
                        }
                        break;
                    case "EXIT":
                        if (basicCMDService.exit().equals("yes")) {
                            exit_flag = 1;
                        }
                        break;
                    default:
                        basicCMDService.selectedInvalidMenu();
                        break;
                }
                if (exit_flag == 1) {
                    break;
                }
            }
        }catch(Exception e){
            basicCMDService.apologizeMessage();
            System.out.println(e);
        }finally {
            basicCMDService.sayBye();
        }
    }

}
