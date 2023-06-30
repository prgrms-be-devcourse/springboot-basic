package com.prgms.voucher.voucherproject;

import com.prgms.voucher.voucherproject.domain.VoucherType;
import com.prgms.voucher.voucherproject.io.Console;
import com.prgms.voucher.voucherproject.repository.MemoryVoucherRepository;
import com.prgms.voucher.voucherproject.repository.VoucherRepository;
import com.prgms.voucher.voucherproject.service.VoucherService;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VoucherApp implements Runnable{
    private final Console console = new Console();
    private VoucherService voucherService;

    public VoucherApp(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    public static void main(String[] args) {
        new VoucherApp().run();
    }

    @Override
    public void run() {
        while(true){
            console.printMenu();
            switch (console.inputCommand().toLowerCase()){
                case "exit":
                    return;

                case "create":
                    console.printKindOfVoucher();
                    int selectedNum = console.inputSelectedVoucherType();
                    VoucherType voucherType = VoucherType.getSelectedVoucherType(selectedNum);
                    if(voucherType != null){
                        try{
                            voucherService.create(voucherType);
                        }
                        catch (Exception e){ //TODO: 잘못된 VoucherType 입력 시 예외처리 안됨
                            e.getMessage();
                        }
                    }
                    else {
                        console.printErrorMsg();
                    }
                    break;

                case "list":
                    voucherService.list();
                    break;

                default:
                    console.printErrorMsg();
            }
        }

    }
}
