package kr.co.springbootweeklymission.member.api;

import kr.co.springbootweeklymission.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberRestController {
    private final MemberService memberService;
}
