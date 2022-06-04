package org.programs.kdt.Customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.programs.kdt.Utils.EnumInterface;

@RequiredArgsConstructor
@Getter
public enum CustomerType implements EnumInterface {
    NORMAL("normal"), BLACKLIST("blacklist");

    private final String type;

    public static CustomerType find(String type) {
        return EnumInterface.find(type, values());
    }

}
