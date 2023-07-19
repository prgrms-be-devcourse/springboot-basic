package org.prgrms.kdt.member.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.service.dto.ServiceCreateMemberRequest;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ServiceMemberMapper {
    @Mapping(target = "memberId", expression = "java(createUUID())")
    Member serviceRequestToMember(ServiceCreateMemberRequest request);

    default UUID createUUID(){
        return UUID.randomUUID();
    }
}
