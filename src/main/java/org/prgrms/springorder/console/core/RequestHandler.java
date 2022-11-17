package org.prgrms.springorder.console.core;

import static org.prgrms.springorder.console.core.Command.BLACKLIST;
import static org.prgrms.springorder.console.core.Command.CREATE;
import static org.prgrms.springorder.console.core.Command.CREATE_CUSTOMER;
import static org.prgrms.springorder.console.core.Command.DELETE_CUSTOMER_VOUCHERS;
import static org.prgrms.springorder.console.core.Command.EXIT;
import static org.prgrms.springorder.console.core.Command.GET_CUSTOMER_VOUCHERS;
import static org.prgrms.springorder.console.core.Command.GET_VOUCHER_WITH_CUSTOMER;
import static org.prgrms.springorder.console.core.Command.LIST;
import static org.prgrms.springorder.console.core.Command.POST_CUSTOMER_VOUCHERS;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import org.prgrms.springorder.console.io.Request;
import org.prgrms.springorder.console.io.Response;
import org.prgrms.springorder.domain.customer.api.CustomerControllerDecorator;
import org.prgrms.springorder.domain.voucher.api.VoucherControllerDecorator;
import org.springframework.stereotype.Component;

@Component
public class RequestHandler {

    private final CustomerControllerDecorator customerController;

    private final VoucherControllerDecorator voucherController;

    private final Map<Command, Function<Request, Response>> requestMap;

    public RequestHandler(
        CustomerControllerDecorator customerController1,
        VoucherControllerDecorator voucherController1) {
        this.customerController = customerController1;
        this.voucherController = voucherController1;

        this.requestMap = new EnumMap<>(Command.class);

        requestMap.put(EXIT, o -> {ConsoleRunningStatus.stop();return null;});
        requestMap.put(CREATE, voucherController::createVoucher);
        requestMap.put(LIST, request -> voucherController.findAllVoucher());
        requestMap.put(BLACKLIST, request -> customerController.findAllCustomers());
        requestMap.put(POST_CUSTOMER_VOUCHERS, customerController::allocateVoucher);
        requestMap.put(GET_CUSTOMER_VOUCHERS, customerController::findAllVouchers);
        requestMap.put(DELETE_CUSTOMER_VOUCHERS, customerController::deleteVoucher);
        requestMap.put(GET_VOUCHER_WITH_CUSTOMER, voucherController::findCustomerWithVoucher);
        requestMap.put(CREATE_CUSTOMER, customerController1::createCustomer);

    }

    public Function<Request, Response> handle(Command command) {
        return requestMap.get(command);
    }


}
