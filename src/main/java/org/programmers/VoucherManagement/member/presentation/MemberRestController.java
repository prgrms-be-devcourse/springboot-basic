package org.programmers.VoucherManagement.member.presentation;

import jakarta.validation.Valid;
import org.programmers.VoucherManagement.global.response.BaseResponse;
import org.programmers.VoucherManagement.member.application.MemberService;
import org.programmers.VoucherManagement.member.application.dto.MemberGetResponses;
import org.programmers.VoucherManagement.member.presentation.dto.MemberCreateRequestData;
import org.programmers.VoucherManagement.member.presentation.dto.MemberCreateResponseData;
import org.programmers.VoucherManagement.member.presentation.dto.MemberUpdateRequestData;
import org.programmers.VoucherManagement.member.presentation.mapper.MemberControllerMapper;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.programmers.VoucherManagement.global.response.SuccessCode.DELETE_MEMBER_SUCCESS;
import static org.programmers.VoucherManagement.global.response.SuccessCode.UPDATE_MEMBER_SUCCESS;

@RestController
@RequestMapping("/member")
public class MemberRestController {
    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping()
    public BaseResponse<MemberGetResponses> getAllMembers() {
        return new BaseResponse<>(memberService.getAllMembers());
    }


    @GetMapping("/black-list")
    public BaseResponse<MemberGetResponses> getAllBlackMembers() {
        return new BaseResponse<>(memberService.getAllBlackMembers());
    }

    @PostMapping("")
    public BaseResponse<MemberCreateResponseData> createMember(@Valid @RequestBody MemberCreateRequestData data) {
        return new BaseResponse<>(MemberControllerMapper.INSTANCE.createResponseToData(
                memberService.createMember(MemberControllerMapper.INSTANCE.dataToCreateRequest(data))
        ));
    }

    @PatchMapping("/{memberId}")
    public BaseResponse<String> updateMember(@PathVariable String memberId, @Valid @RequestBody MemberUpdateRequestData data) {
        UUID memberUUID = UUID.fromString(memberId);
        memberService.updateMember(memberUUID, MemberControllerMapper.INSTANCE.dataToUpdateRequest(data));

        return new BaseResponse<>(UPDATE_MEMBER_SUCCESS);
    }

    @DeleteMapping("/{memberId}")
    public BaseResponse<String> deleteMember(@PathVariable String memberId) {
        UUID memberUUID = UUID.fromString(memberId);
        memberService.deleteMember(memberUUID);

        return new BaseResponse<>(DELETE_MEMBER_SUCCESS);
    }
}
