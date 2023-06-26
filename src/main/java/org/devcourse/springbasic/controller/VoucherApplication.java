package org.devcourse.springbasic.controller;

import org.devcourse.springbasic.io.ConsoleDevice;
import org.devcourse.springbasic.io.ErrorMsgPrinter;
import org.devcourse.springbasic.controller.menu.RunByMenu;
import org.devcourse.springbasic.controller.menu.MenuType;
import org.devcourse.springbasic.io.IODevice;
import org.devcourse.springbasic.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class VoucherApplication {

    private final Map<String, IODevice> ioDeviceMap = new ConcurrentHashMap<>();
    private final VoucherService voucherService;

    public VoucherApplication(VoucherService voucherService) {
        ioDeviceMap.put("ConsoleDevice", new ConsoleDevice());
        this.voucherService = voucherService;
    }

    public void run(String deviceType) {

        IODevice ioDevice = ioDeviceMap.get(deviceType);
        MenuType menu = MenuType.EXIT;

        do {
            try {
                ioDevice.outputMenus();
                menu = ioDevice.inputMenu();
                RunByMenu runByMenu = menu.getFunctionToRunByMenu().apply(ioDevice, voucherService);
                runByMenu.run();

            } catch (IllegalArgumentException e) {
                ErrorMsgPrinter.print(e.getMessage());
            }
        } while (!menu.isExit());
    }
}
