package com.prgms.voucher.voucherproject;

import com.prgms.voucher.voucherproject.domain.MenuType;
import com.prgms.voucher.voucherproject.domain.VoucherType;
import com.prgms.voucher.voucherproject.io.Console;
import com.prgms.voucher.voucherproject.repository.MemoryVoucherRepository;
import com.prgms.voucher.voucherproject.service.VoucherService;
import java.util.InputMismatchException;

public class VoucherApp implements Runnable {
    private int EXIT_NUM = 1;
    private final Console console = new Console();

    private final VoucherService voucherService;


    public VoucherApp(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    public static void main(String[] args) {
        new VoucherApp(new VoucherService(new MemoryVoucherRepository())).run();
    }

    @Override
    public void run() {

        while (EXIT_NUM > 0) {
            console.printMenu();

            try {
                switch (MenuType.getSelectedMenuType(console.inputCommand().toLowerCase())) {
                    case EXIT:
                        EXIT_NUM = -1;
                        console.printMsg("프로그램을 종료합니다.");
                        break;

                    case CREATE:
                        console.printKindOfVoucher();
                        int selectedNum = console.inputSelectedVoucherType();
                        console.bufferDeleted();

                        try {
                            VoucherType voucherType = VoucherType.getSelectedVoucherType(selectedNum);
                            voucherService.create(voucherType);
                        } catch (InputMismatchException e) {
                            System.out.println(e.getLocalizedMessage());
                        }

                        break;

                    case LIST:
                        voucherService.list();
                        break;
                }
            } catch (InputMismatchException e) {
                console.printErrorMsg();
            }

        }

    }
}
