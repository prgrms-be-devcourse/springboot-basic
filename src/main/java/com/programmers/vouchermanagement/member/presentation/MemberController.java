package com.programmers.vouchermanagement.member.presentation;

import com.programmers.vouchermanagement.exception.FileIOException;
import com.programmers.vouchermanagement.member.application.MemberService;
import com.programmers.vouchermanagement.member.dto.MemberResponseDto;
import com.programmers.vouchermanagement.member.exception.MemberNotFoundException;
import com.programmers.vouchermanagement.utils.ConsoleOutputManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final ConsoleOutputManager consoleOutputManager;
    private final MemberService memberService;

    public MemberController(ConsoleOutputManager consoleOutputManager, MemberService memberService) {
        this.consoleOutputManager = consoleOutputManager;
        this.memberService = memberService;
    }

    public void readAllBlackList() {

        consoleOutputManager.printBlackList();

        try {
            List<MemberResponseDto> memberResponseDtos = memberService.readAllBlackList();
            consoleOutputManager.printMemberInfo(memberResponseDtos);

        } catch (MemberNotFoundException | FileIOException e) {
            logger.error(e.getMessage(), e);

            consoleOutputManager.printReturnMain(e.getMessage());
        }
    }
}
