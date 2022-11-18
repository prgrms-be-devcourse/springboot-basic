package org.prgrms.springbootbasic.type;

import org.prgrms.springbootbasic.controller.BlackListManagementController;
import org.prgrms.springbootbasic.controller.Controller;
import org.prgrms.springbootbasic.controller.VoucherController;

import java.util.Arrays;
import java.util.Optional;

public enum ServiceType {
    VOUCHER_SERVICE("1", VoucherController.class), BLACK_LIST_MANAGEMENT_SERVICE("2", BlackListManagementController.class);

    private final String number;
    private final Class<? extends Controller> controller;

    ServiceType(String number, Class<? extends Controller> controller) {
        this.number = number;
        this.controller = controller;
    }

    public static Class<? extends Controller> number2ControllerClass(String number) {
        Optional<? extends Class<? extends Controller>> first = Arrays.stream(ServiceType.values())
                .filter(x -> x.number.equals(number))
                .map(x -> x.controller).findFirst();

        if (first.isEmpty()) {
            return null;
        }
        return first.get();
    }
}
