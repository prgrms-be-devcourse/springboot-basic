package org.prgrms.kdtspringorder.io.enums.implementation;

import org.prgrms.kdtspringorder.io.enums.abstraction.Option;

public enum CreationOption implements Option {
    FIXED("fixed"),
    PERCENT("percent");

    private final String optionName;

    CreationOption(String optionName) {
        this.optionName = optionName;
    }

    @Override
    public String getOptionName() {
        return optionName;
    }
}
