package org.programmers.VoucherManagement.member.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.programmers.VoucherManagement.member.application.dto.MemberCreateRequest;
import org.programmers.VoucherManagement.member.application.dto.MemberCreateResponse;
import org.programmers.VoucherManagement.member.domain.Member;

@Mapper(componentModel = "spring")
public interface MemberServiceMapper {

    MemberServiceMapper INSTANCE = Mappers.getMapper(MemberServiceMapper.class);

    @Mapping(source = "memberId", target = "memberId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "memberStatus", target = "memberStatus")
    MemberCreateResponse domainToCreateResponse(Member member);

    @Mapping(target = "memberId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "memberStatus", target = "memberStatus")
    Member createRequestToDomain(MemberCreateRequest request);

}
