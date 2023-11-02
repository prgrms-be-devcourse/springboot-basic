package org.prgrms.kdtspringdemo.customer;

import org.prgrms.kdtspringdemo.customer.controller.CustomerController;
import org.prgrms.kdtspringdemo.voucher.controller.VoucherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public enum CustomerFunction {
    CREATE("create", "고객 등록", CustomerController::insert),
    LIST_ALL_CUSTOMERS("list", "고객 목록", CustomerController::printAllCustomers),
    EXIT("exit", "voucher mode 종료", CustomerController::endCustomerMode);

    private final String fun;
    private final String description;
    private final Consumer<CustomerController> customerControllerConsumer;
    private final static Logger logger = LoggerFactory.getLogger(CustomerFunction.class);

    CustomerFunction(String fun, String description, Consumer<CustomerController> customerControllerConsumer) {
        this.fun = fun;
        this.description = description;
        this.customerControllerConsumer = customerControllerConsumer;
    }

    public static CustomerFunction findByCode(String fun) {
        String lowerFun = fun.toLowerCase();
        return Arrays.stream(values())
                .filter(option -> option.fun.equals(lowerFun))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("해당 명령어가 존재하지 않습니다.");
                    return new NoSuchElementException("해당 명령어가 존재하지 않습니다.");
                });
    }

    public void execute(CustomerController customerController) {
        this.customerControllerConsumer.accept(customerController);
    }
}
