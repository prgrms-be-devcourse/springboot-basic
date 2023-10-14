package org.prgrms.vouchermanager.handler;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.voucher.VoucherType;
import org.prgrms.vouchermanager.exception.InputValueException;
import org.prgrms.vouchermanager.io.Input;
import org.prgrms.vouchermanager.io.Output;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class Handler {
    private final Input input;
    private final Output output;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private boolean continueOrNot = true;
    public void init() throws IOException {
        while(continueOrNot){
            output.selectMenu();
            String menu = input.selectMenu();

            switch (menu){
                case "1" : voucherLauncher();
                case "2" : customerLauncher();
                default: output.print("1과 2중 선택해주세요");
            }
        }
    }

    private void voucherLauncher() throws IOException{
        output.voucherInit();
        String voucherMenu = input.voucherInit();
        try{
            VoucherType menu = VoucherType.fromValue(voucherMenu);
            if(menu == VoucherType.CREATE)
                voucherCreate();
            else if(menu == VoucherType.LIST)
                voucherList();
            else if(menu == VoucherType.EXIT)
                continueOrNot = false; //프로그램 종료
        }catch (IllegalArgumentException e){
            output.print(e.getMessage());
        }
        catch (InputValueException e){
            output.print(e.getMessage());
        }
    }

    private void voucherList() {
        List<Voucher> allVouchers = voucherController.list();
        allVouchers.forEach(voucher -> output.printVoucherInfo(voucher.toString()));
    }

    private void voucherCreate() throws IOException{
        output.createVoucherMenu();
        String createType = input.createVoucher();
        try{
            VoucherType voucherType = VoucherType.fromValue(createType);
            voucherController.create(voucherType);
        }catch (InputValueException e){
            System.out.println(e.getMessage());
        }
    }


    private void customerLauncher() throws IOException{
        output.customerInit();
        String customerMenu = input.customerInit();
        try{
            VoucherType customerType = VoucherType.fromValue(customerMenu);
            if(customerType == VoucherType.LIST)
                customerList();
            else if(customerType == VoucherType.EXIT)
                continueOrNot = false;
        }catch (InputValueException e){
            output.print(e.getMessage());
        }

    }

    private void customerList() {
        List<Customer> all = customerController.findAll();
    }
}
