package org.prgrms.vouchermanager.handler;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.domain.VoucherType;
import org.prgrms.vouchermanager.io.Input;
import org.prgrms.vouchermanager.io.Output;

import java.io.IOException;

@RequiredArgsConstructor
public class Handler {
    private final Input input;
    private final Output output;
    private final VoucherController voucherController;
    private boolean continueOrNot = true;
    public void init() throws IOException {
        while(continueOrNot){
            output.selectMenu();
            String menu = input.selectMenu();

            switch (menu){
                case "1" : voucherLauncher();
            }
            break;
        }
    }

    private void voucherLauncher() throws IOException{
        output.createVoucherMenu();
        String voucherType = input.createVoucher();
        try{
            VoucherType type = VoucherType.fromValue(voucherType);
            if(type == VoucherType.CREATE)
                voucherController.create();
            else if(type == VoucherType.LIST)
                voucherController.list();
            else if(type == VoucherType.EXIT)
                continueOrNot = false;
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}
