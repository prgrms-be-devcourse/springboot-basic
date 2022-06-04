package org.programs.kdt.Command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.programs.kdt.Utils.EnumInterface;
import org.programs.kdt.Utils.EnumMenuInterface;

@Getter
@AllArgsConstructor
public enum WalletMenu implements EnumInterface, EnumMenuInterface {
    RETURN("0", "되돌아가기"), CREATE("1", "바우처 생성"),
    FIND_ALL("2", "전체 바우처지갑 보기"), FIND_CUSTOMER("3" , "유저가 가진 바우처 지갑검색"),
    FIND_VOUCHER("4", "바우처로 바우처 지갑 검색"), DELETE("5", "단일 바우처지갑 삭제"),
    ERROR("error", "잘못된 메뉴입니다.");

    private final String type;

    private final String description;

    public static WalletMenu find(String type) {
        try {
            return EnumInterface.find(type, values());
        } catch (RuntimeException e) {
            return WalletMenu.ERROR;
        }
    }

    public static String toMenu(){
        return EnumMenuInterface.toMenu(values());
    }
}
