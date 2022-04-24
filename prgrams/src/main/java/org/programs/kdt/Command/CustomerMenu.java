package org.programs.kdt.Command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.programs.kdt.Utils.EnumInterface;
import org.programs.kdt.Utils.EnumMenuInterface;

@Getter
@AllArgsConstructor
public enum CustomerMenu implements EnumInterface, EnumMenuInterface {

    RETURN("0", "되돌아가기"), CREATE("1", "유저 생성"),
    FIND_ALL("2", "전체 유저 보기"), FIND_EMAIL("3", "이메일검색"),
    UPDATE("4", "유저 업데이트"), DELETE("5", "유저 삭제"), BLACKLIST("6", "블랙리스트 등록"),
    FIND_BLACKLIST("7", "블랙리스트 조회"),
    ERROR("error", "없는 번호입니다.");

    private final String type;
    private final String description;

    public static CustomerMenu find(String type) {
        try {
            return EnumInterface.find(type, values());
        } catch (RuntimeException e) {
            return CustomerMenu.ERROR;
        }
    }

    public static String toMenu(){
        return EnumMenuInterface.toMenu(values());
    }
}
