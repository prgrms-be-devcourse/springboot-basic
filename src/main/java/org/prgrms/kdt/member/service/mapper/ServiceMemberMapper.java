package org.prgrms.kdt.member.service.mapper;

import org.prgrms.kdt.global.Generator;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.service.dto.CreateMemberRequest;
import org.springframework.stereotype.Component;

@Component
public class ServiceMemberMapper {
    private final Generator generator;

    public ServiceMemberMapper(Generator generator) {
        this.generator = generator;
    }

    public Member convertMember(CreateMemberRequest request){
        return new Member(generator.generateId(), request.name(), request.status());
    }
}
