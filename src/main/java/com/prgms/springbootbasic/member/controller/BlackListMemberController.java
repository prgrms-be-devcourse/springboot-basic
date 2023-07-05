package com.prgms.springbootbasic.member.controller;

import com.prgms.springbootbasic.member.model.BlackList;
import com.prgms.springbootbasic.member.model.Member;
import com.prgms.springbootbasic.global.ui.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BlackListMemberController implements MemberController {

    private static final Logger logger = LoggerFactory.getLogger(BlackListMemberController.class);
    private final Console console;
    private final BlackList blackList;

    public BlackListMemberController(Console console, BlackList blackList) {
        this.console =console;
        this.blackList = blackList;
    }

    @Override
    public boolean run() {
        try {
            List<Member> members = blackList.findBlackList();
            logger.info("블랙리스트의 고객을 조회하는데 성공했습니다. size : {}", members.size());
            console.showMemberList(members);
        } catch (Exception e) {
            console.showExceptionMessage(e.getMessage());
        }
        return true;
    }

}
