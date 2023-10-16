package com.prgrms.voucher_manage.console;

import com.prgrms.voucher_manage.domain.voucher.controller.VoucherController;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsoleManager implements ApplicationRunner {
    private final OutputUtil outputUtil;
    private final InputUtil inputUtil;
    private final VoucherController voucherController;


    @Override
    public void run(ApplicationArguments args){
        String menu = "";
        while (!menu.equals("exit")){
            try{
                outputUtil.printMenu();
                menu = inputUtil.getStringInput();
                selectMenu(menu);

            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void selectMenu(String menu) throws Exception {
        switch (menu){
            case "create" -> setVoucherInfo();
            case "list" -> voucherController.showVoucherList();
        }
    }

    public void setVoucherInfo() throws Exception {
        outputUtil.printVoucherSelect();
        VoucherType voucherType = VoucherType.matchVoucherType(inputUtil.getStringInput());

        outputUtil.printMessage("Type discount amount of voucher");
        Long discountAmount = inputUtil.getLongInput();

        voucherController.createVoucher(voucherType, discountAmount);
    }
}
