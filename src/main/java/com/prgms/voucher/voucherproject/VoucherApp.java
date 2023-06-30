package com.prgms.voucher.voucherproject;

import com.prgms.voucher.voucherproject.domain.MenuType;
import com.prgms.voucher.voucherproject.domain.VoucherType;
import com.prgms.voucher.voucherproject.io.Console;
import com.prgms.voucher.voucherproject.repository.MemoryVoucherRepository;
import com.prgms.voucher.voucherproject.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;

import static com.prgms.voucher.voucherproject.domain.MenuType.EXIT;

public class VoucherApp implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(VoucherApp.class);
    private int EXIT_NUM = 1;
    private final Console console = new Console();

    private final VoucherService voucherService;


    public VoucherApp(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    public static void main(String[] args) {
        new VoucherApp(new VoucherService(new MemoryVoucherRepository())).run();
    }

    String menuName;

    @Override
    public void run() {

        while (EXIT_NUM > 0) {
            console.printMenu();
            menuName = console.inputCommand().toLowerCase();

            try {
                switch (MenuType.getSelectedMenuType(menuName)) {
                    case EXIT -> {
                        EXIT_NUM = -1;
                        console.printMsg("프로그램을 종료합니다.");
                    }
                    case CREATE -> {
                        console.printKindOfVoucher();
                        int selectedNum = console.inputSelectedVoucherType();
                        console.bufferDeleted();
                        try {
                            VoucherType voucherType = VoucherType.getSelectedVoucherType(selectedNum);
                            voucherService.create(voucherType);
                        } catch (InputMismatchException e) {
                            logger.error("VoucherType InputMismatchException -> {}", selectedNum);
                            System.out.println(e.getLocalizedMessage());
                        }
                    }
                    case LIST -> voucherService.list();
                }
            } catch (InputMismatchException e) {
                logger.error("MenuType InputMismatchException -> {}", menuName);
                console.printErrorMsg();
            }

        }

    }
}
