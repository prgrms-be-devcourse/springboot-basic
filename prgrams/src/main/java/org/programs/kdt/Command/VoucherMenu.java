package org.programs.kdt.Command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.programs.kdt.Utils.EnumInterface;
import org.programs.kdt.Utils.EnumMenuInterface;

@AllArgsConstructor
@Getter
public enum VoucherMenu implements EnumInterface, EnumMenuInterface {
    RETURN("0", "되돌아가기"), CREATE("1", "바우처 생성"),
    FIND_ALL("2", "전체 바우처 보기"), FIND_PERCENT("3", "Percent 바우처 조회"),
    FIND_FIX("4", "FixedAmount 바우처 조회"), DELETE("5", "바우처 삭제"),
    ERROR("error", "없는 번호입니다.");
    private final String type;
    private final String description;

    public static VoucherMenu find(String type) {
        try {
            return EnumInterface.find(type, values());
        } catch (RuntimeException e) {
            return VoucherMenu.ERROR;
        }
    }

    public static String toMenu(){
        return EnumMenuInterface.toMenu(values());
    }
}
