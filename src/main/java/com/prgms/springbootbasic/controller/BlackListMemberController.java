package com.prgms.springbootbasic.controller;

import com.prgms.springbootbasic.model.BlackList;
import com.prgms.springbootbasic.model.Member;
import com.prgms.springbootbasic.ui.Console;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BlackListMemberController implements MemberController {

    private final Console console;

    public BlackListMemberController(Console console) {
        this.console =console;
    }

    @Override
    public boolean run() {
        try {
            BlackList blackList = new BlackList();
            List<Member> members = blackList.findBlackList();
            console.showMemberList(members);
        } catch (Exception e) {
            console.showExceptionMessage(e.getMessage());
        }
        return true;
    }

}
