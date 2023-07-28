package org.programmers.VoucherManagement.member.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.programmers.VoucherManagement.member.application.dto.MemberCreateRequest;
import org.programmers.VoucherManagement.member.application.dto.MemberCreateResponse;
import org.programmers.VoucherManagement.member.application.dto.MemberUpdateRequest;
import org.programmers.VoucherManagement.member.presentation.dto.MemberCreateRequestData;
import org.programmers.VoucherManagement.member.presentation.dto.MemberCreateResponseData;
import org.programmers.VoucherManagement.member.presentation.dto.MemberUpdateRequestData;

@Mapper(componentModel = "spring")
public interface MemberControllerMapper {
    MemberControllerMapper INSTANCE = Mappers.getMapper(MemberControllerMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "memberStatus", target = "memberStatus")
    MemberCreateRequest dataToCreateRequest(MemberCreateRequestData data);

    @Mapping(source = "memberStatus", target = "memberStatus")
    MemberUpdateRequest dataToUpdateRequest(MemberUpdateRequestData data);

    @Mapping(source = "memberId", target = "memberId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "memberStatus", target = "memberStatus")
    MemberCreateResponseData createResponseToData(MemberCreateResponse response);
}
