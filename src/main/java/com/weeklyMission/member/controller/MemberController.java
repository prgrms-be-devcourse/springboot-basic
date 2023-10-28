package com.weeklyMission.member.controller;

import com.weeklyMission.member.dto.MemberRequest;
import com.weeklyMission.member.dto.MemberResponse;
import com.weeklyMission.member.service.MemberService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public MemberResponse create(MemberRequest member){
        return memberService.save(member);
    }

    public List<MemberResponse> findAll(){
        return memberService.findAll();
    }

    public MemberResponse findById(String id){
        return memberService.findById(id);
    }

    public void deleteById(String id){
        memberService.deleteById(id);
    }
}
