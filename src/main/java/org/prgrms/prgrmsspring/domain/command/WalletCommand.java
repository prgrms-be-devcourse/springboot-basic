package org.prgrms.prgrmsspring.domain.command;

import org.prgrms.prgrmsspring.controller.ApplicationController;
import org.prgrms.prgrmsspring.controller.WalletController;

import java.util.function.Consumer;

public enum WalletCommand implements Command {
    CREATE("allocate voucher to specific customer", WalletController::create),
    FIND_VOUCHER("find voucher by customer", WalletController::findCustomerVouchers),
    DELETE("delete all vouchers by customer", WalletController::deleteCustomerVouchers),
    FIND_CUSTOMER("find customer who having voucher", WalletController::findCustomerHasVoucher);

    private final String document;
    private final Consumer<WalletController> consumer;

    WalletCommand(String document, Consumer<WalletController> consumer) {
        this.document = document;
        this.consumer = consumer;
    }

    @Override
    public String getDocument() {
        return document;
    }

    @Override
    public void run(ApplicationController controller) {
        this.consumer.accept((WalletController) controller);
    }
}
