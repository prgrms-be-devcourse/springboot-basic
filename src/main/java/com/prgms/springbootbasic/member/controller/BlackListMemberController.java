package com.prgms.springbootbasic.member.controller;

import com.prgms.springbootbasic.member.model.BlackList;
import com.prgms.springbootbasic.member.model.Member;
import com.prgms.springbootbasic.global.ui.Console;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BlackListMemberController implements MemberController {

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
            console.showMemberList(members);
        } catch (Exception e) {
            console.showExceptionMessage(e.getMessage());
        }
        return true;
    }

}
