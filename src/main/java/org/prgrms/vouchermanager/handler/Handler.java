package org.prgrms.vouchermanager.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
                break;
                case "2" : customerLauncher();
                break;
                default: log.error("입력 값 : {}", menu + " (1과 2중 선택해야 합니다)");
            }
        }
    }

    private void voucherLauncher(){
        output.voucherInit();

        try{
            String voucherMenu = input.voucherInit();
            VoucherType menu = VoucherType.fromValue(voucherMenu);
            if(menu == VoucherType.CREATE)
                voucherCreate();
            else if(menu == VoucherType.LIST)
                voucherList();
            else if(menu == VoucherType.EXIT)
                continueOrNot = false;
        }catch (IllegalArgumentException e){
            output.print(e.getMessage());
        }
        catch (InputValueException e){
            log.error(e.getMessage() + "(create or list or exit)");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void voucherList() {
        List<Voucher> allVouchers = voucherController.list();
        allVouchers.forEach(voucher -> output.print(voucher.toString()));
    }

    private void voucherCreate(){
        output.createVoucherMenu();

        try{
            String createType = input.createVoucher();
            VoucherType voucherType = VoucherType.fromValue(createType);
            voucherController.create(voucherType);
        }catch (InputValueException e){
            log.error(e.getMessage() + "(Fixed or Percent)");
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    private void customerLauncher(){
        output.customerInit();
        try{
            String customerMenu = input.customerInit();
            VoucherType customerType = VoucherType.fromValue(customerMenu);
            if(customerType == VoucherType.LIST)
                customerList();
            else if(customerType == VoucherType.CREATE)
                customerCreate();
            else if(customerType == VoucherType.EXIT)
                continueOrNot = false;
        }catch (InputValueException e){
            log.error(e.getMessage() + "(create or list or exit)");
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    private void customerCreate() {
        output.outputCustomerName();
        try{
            String name = input.inputCustomerName();
            customerController.create(name);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void customerList() {
        List<Customer> all = customerController.list();
        all.forEach(customer -> output.print(customer.toString()));
    }
}
