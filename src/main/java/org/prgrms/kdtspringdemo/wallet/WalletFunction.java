package org.prgrms.kdtspringdemo.wallet;

import org.prgrms.kdtspringdemo.voucher.controller.VoucherController;
import org.prgrms.kdtspringdemo.wallet.controller.WalletController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public enum WalletFunction {
    FIND_BY_CUSTOMER("findbycustomerid", "고객이 가진 지갑 속 바우처 조회", WalletController::printVouchersByCustomerId),
    DELETE_VOUCHER("deletevoucher", "customerId, voucherId로 바우처 삭제", WalletController::deleteVoucherByVoucherId);
    private final String fun;
    private final String description;
    private final Consumer<WalletController> walletControllerConsumer;
    private final static Logger logger = LoggerFactory.getLogger(WalletFunction.class);

    WalletFunction(String fun, String description, Consumer<WalletController> walletControllerConsumer) {
        this.fun = fun;
        this.description = description;
        this.walletControllerConsumer = walletControllerConsumer;
    }

    public static WalletFunction findByCode(String fun) {
        String lowerFun = fun.toLowerCase();
        return Arrays.stream(values())
                .filter(option -> option.fun.equals(lowerFun))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("해당 명령어가 존재하지 않습니다.");
                    return new NoSuchElementException("해당 명령어가 존재하지 않습니다.");
                });
    }

    public void execute(WalletController walletController) {
        this.walletControllerConsumer.accept(walletController);
    }
}
