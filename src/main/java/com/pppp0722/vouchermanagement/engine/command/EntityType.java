package com.pppp0722.vouchermanagement.engine.command;

public enum EntityType {
    MEMBER,
    VOUCHER,
    WALLET,
    NONE;

    public static EntityType getEntityType(String input) {
        switch (input) {
            case "member":
                return EntityType.MEMBER;
            case "voucher":
                return EntityType.VOUCHER;
            case "wallet":
                return EntityType.WALLET;
            default:
                return EntityType.NONE;
        }
    }
}
