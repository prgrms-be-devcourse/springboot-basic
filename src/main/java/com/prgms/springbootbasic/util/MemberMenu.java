package com.prgms.springbootbasic.util;

import com.prgms.springbootbasic.controller.MemberController;
import com.prgms.springbootbasic.exception.NoSuchMenuException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public enum MemberMenu {

    BLACKLIST("list", VoucherApplication.blackListMemberController());

    private static final List<MemberMenu> MEMBER_MENU = Arrays.stream(MemberMenu.values()).toList();
    private static final Logger logger = LoggerFactory.getLogger(MemberMenu.class);
    private final String command;
    private final MemberController memberController;

    MemberMenu(String command, MemberController memberController) {
        this.command = command;
        this.memberController = memberController;
    }

    public MemberController getMemberController() {
        return memberController;
    }

    public static MemberMenu of(String command) {
        return MEMBER_MENU.stream()
                .filter(m -> m.command.equals(command))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("해당하는 메뉴가 없습니다. 입력 값 : {}", command);
                    return new NoSuchMenuException(ExceptionMessage.NO_SUCH_MENU);
                });
    }

}
