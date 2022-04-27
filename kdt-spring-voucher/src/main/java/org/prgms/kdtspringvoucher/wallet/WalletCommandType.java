package org.prgms.kdtspringvoucher.wallet;

public enum WalletCommandType {
    ASSIGN("assign"),
    CUSTOMER("customer"),
    DELETE("delete"),
    VOUCHER("voucher");

    private String walletCommandType;

    WalletCommandType(String walletCommandType) {
        this.walletCommandType = walletCommandType;
    }

    public String getCommandType() {
        return walletCommandType;
    }
}
