package org.prgms.voucher.ui;

public enum PromotionType {
    VOUCHER("voucher", 1);

    private final String promotionType;
    private final int choiceNumber;

    PromotionType(String promotionType, int choiceNumber) {
        this.promotionType = promotionType;
        this.choiceNumber = choiceNumber;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public int getChoiceNumber() {
        return choiceNumber;
    }
}
