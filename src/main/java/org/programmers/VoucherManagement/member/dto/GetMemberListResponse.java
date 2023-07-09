package org.programmers.VoucherManagement.member.dto;

import java.util.Collections;
import java.util.List;

public class GetMemberListResponse {
    private final List<GetMemberResponse> getMemberResponseList;
    public GetMemberListResponse(List<GetMemberResponse> getMemberResponseList){
        this.getMemberResponseList = getMemberResponseList;
    }
    public List<GetMemberResponse> getGetMemberListRes(){
        return Collections.unmodifiableList(getMemberResponseList);
    }
}
