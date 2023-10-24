package org.prgrms.prgrmsspring.domain;

import org.prgrms.prgrmsspring.controller.ApplicationController;
import org.prgrms.prgrmsspring.controller.CustomerController;
import org.prgrms.prgrmsspring.controller.VoucherController;
import org.prgrms.prgrmsspring.controller.WalletController;

import java.util.function.Consumer;

public enum Command {
    EXIT("exit the program.", controller -> ((VoucherController) controller).exit(), VoucherController.class),
    CREATE_VOUCHER("create a new voucher.", controller -> ((VoucherController) controller).create(), VoucherController.class),
    UPDATE_VOUCHER("update a voucher.", controller -> ((VoucherController) controller).update(), VoucherController.class),
    DELETE_VOUCHER("delete a voucher.", controller -> ((VoucherController) controller).delete(), VoucherController.class),
    LIST_ALL_VOUCHERS("list all vouchers.", controller -> ((VoucherController) controller).list(), VoucherController.class),
    CREATE_CUSTOMER("create a new customer.", controller -> ((CustomerController) controller).create(), CustomerController.class),
    UPDATE_CUSTOMER("update a customer", controller -> ((CustomerController) controller).update(), CustomerController.class),
    DELETE_CUSTOMER("delete a customer", controller -> ((CustomerController) controller).delete(), CustomerController.class),
    LIST_ALL_CUSTOMERS("list all customers.", controller -> ((CustomerController) controller).findAll(), CustomerController.class),
    LIST_ALL_BLACK("list all blacklist customers.", controller -> ((CustomerController) controller).findAllBlackList(), CustomerController.class),
    CREATE_WALLET("allocate voucher to specific customer", controller -> ((WalletController) controller).create(), WalletController.class),
    FIND_VOUCHER_BY_CUSTOMER("find voucher by customer", controller -> ((WalletController) controller).findCustomerVouchers(), WalletController.class),
    DELETE_VOUCHER_BY_CUSTOMER("delete all vouchers by customer", controller -> ((WalletController) controller).deleteCustomerVouchers(), WalletController.class),
    FIND_CUSTOMER_HAVING_VOUCHER("find customer who having voucher.", controller -> ((WalletController) controller).findCustomerHasVoucher(), WalletController.class);
    private final String document;
    private final Consumer<ApplicationController> consumer;
    private final Class<? extends ApplicationController> controllerClass;


    Command(String document, Consumer<ApplicationController> consumer, Class<? extends ApplicationController> controllerClass) {
        this.document = document;
        this.consumer = consumer;
        this.controllerClass = controllerClass;
    }

    public String getDocument() {
        return document;
    }

    public Class<? extends ApplicationController> getControllerClass() {
        return controllerClass;
    }

    public static Command of(String name) throws IllegalArgumentException {
        return valueOf(name.toUpperCase());
    }

    public void run(ApplicationController controller) {
        this.consumer.accept(controller);
    }

    public static boolean isExit(Command command) {
        return command.equals(Command.EXIT);
    }
}
