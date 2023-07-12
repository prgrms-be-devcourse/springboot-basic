package com.programmers.springbootbasic.wallet.domain;

import com.programmers.springbootbasic.exception.InvalidRequestValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

public enum WalletMenu {
    ASSIGN_VOUCHER("1"),
    SEARCH_CUSTOMER("2"),
    SEARCH_VOUCHER("3"),
    DELETE_VOUCHER("4");

    private static final Logger log = LoggerFactory.getLogger(WalletMenu.class);

    private final String number;

    WalletMenu(String number) {
        this.number = number;
    }

    public static WalletMenu findWalletMenu(String walletMenuRequest) {
        checkWalletMenuRequestEmpty(walletMenuRequest);

        return Arrays.stream(WalletMenu.values())
                .filter(walletMenu -> Objects.equals(walletMenu.number, walletMenuRequest))
                .findAny()
                .orElseThrow(() -> {
                    log.error("The invalid wallet menu request found. request value = {}", walletMenuRequest);
                    return new InvalidRequestValueException("[ERROR] 요청하신 메뉴 값이 유효하지 않습니다.");
                });
    }

    private static void checkWalletMenuRequestEmpty(String walletMenuRequest) {
        if (walletMenuRequest.isEmpty()) {
            log.error("The wallet menu request not found");
            throw new InvalidRequestValueException("[ERROR] 메뉴 요청 값이 비었습니다.");
        }
    }
}
