package org.devcourse.springbasic;

import org.devcourse.springbasic.menu.RunByMenu;
import org.devcourse.springbasic.menu.MenuType;
import org.devcourse.springbasic.io.IODevice;
import org.devcourse.springbasic.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoucherApplication {

    private final IODevice ioDevice;
    private final VoucherService voucherService;

    public VoucherApplication(IODevice ioDevice, VoucherService voucherService) {
        this.ioDevice = ioDevice;
        this.voucherService = voucherService;
    }

    public void run() {

        MenuType menu = MenuType.NONE;
        do {

            try {
                ioDevice.outputMenus();
                menu = ioDevice.inputMenu();

                voucherService.runBy(menu);



            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            }


        } while (menu != MenuType.EXIT);

    }

}