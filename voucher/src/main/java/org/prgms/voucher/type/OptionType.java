package org.prgms.voucher.type;

public enum OptionType {
    FIXED_AMOUNT("fixed amount", "fix", 1),
    PERCENT_AMOUNT("percent amount", "percent", 2);

    private final String optionType;
    private final String shortOptionType;
    private final int choiceNumber;

    OptionType(String optionType, String shortOptionType, int choiceNumber) {
        this.optionType = optionType;
        this.shortOptionType = shortOptionType;
        this.choiceNumber = choiceNumber;
    }

    public String getOptionType() {
        return optionType;
    }

    public String getShortOptionType() {
        return shortOptionType;
    }

    public int getChoiceNumber() {
        return choiceNumber;
    }
}
