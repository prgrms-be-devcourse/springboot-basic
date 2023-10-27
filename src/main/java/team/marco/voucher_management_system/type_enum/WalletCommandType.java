package team.marco.voucher_management_system.type_enum;

public enum WalletCommandType {
    EXIT, SUPPLY, VOUCHER_LIST, RETURN, CUSTOMER_LIST;

    public static WalletCommandType selectCommand(int index) {
        WalletCommandType[] walletCommandTypes = values();

        if (index < 0 || index >= walletCommandTypes.length) {
            throw new IllegalArgumentException("올바르지 않은 입력입니다.");
        }

        return walletCommandTypes[index];
    }
}
