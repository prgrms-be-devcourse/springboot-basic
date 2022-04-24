package org.programs.kdt.Command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.programs.kdt.Utils.EnumInterface;
import org.programs.kdt.Utils.EnumMenuInterface;

@AllArgsConstructor
@Getter
public enum CommandMenu implements EnumInterface, EnumMenuInterface {

    EXIT("0", "메뉴 종료"), VOUCHER("1", "바우처 메뉴"),
    CUSTOMER("2", "유저메뉴"), WALLET("3", "지갑메뉴"), ERROR("error", "없는 번호입니다.");
    private String type;
    private final String description;

    public static CommandMenu findCommand(String type) {
        try {
           return EnumInterface.find(type, values());
        } catch (RuntimeException e) {
            return CommandMenu.ERROR;
        }
    }

    public static String toMenu(){
        return EnumMenuInterface.toMenu(values());
    }

}
