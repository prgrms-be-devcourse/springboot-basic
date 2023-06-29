package org.programmers.VoucherManagement.member.dto;

import java.util.Collections;
import java.util.List;

public class GetMemberListRes {
    private final List<GetMemberRes> getMemberResList;
    public GetMemberListRes(List<GetMemberRes> getMemberResList){
        this.getMemberResList = getMemberResList;
    }
    public List<GetMemberRes> getGetMemberListRes(){
        return Collections.unmodifiableList(getMemberResList);
    }
}
