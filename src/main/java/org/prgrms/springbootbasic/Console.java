package org.prgrms.springbootbasic;

import org.prgrms.springbootbasic.controller.VoucherController;
import org.prgrms.springbootbasic.util.CommandLineInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.prgrms.springbootbasic.type.MethodType.isExist;

@Component
public class Console {

    @Value(value = "${notification.service}")
    private static String serviceNotification;

    @Value(value = "${notification.exit}")
    private static String exitNotification;
    private final VoucherController voucherController;

    @Autowired
    NotificationProperties notificationProperties;

    @Autowired
    public Console(VoucherController voucherController) {
        this.voucherController = voucherController;
    }

    public void run() {
        String input = CommandLineInput.getInput(serviceNotification);
        if (isExist(input)) {
            System.out.println(exitNotification);
            return;
        }
        System.out.println(voucherController.process());
        run();
    }

}
