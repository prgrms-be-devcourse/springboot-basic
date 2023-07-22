package org.prgrms.kdt.member.service.mapper;

import java.util.UUID;
import javax.annotation.processing.Generated;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.member.service.dto.CreateMemberServiceRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-22T15:51:04+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class ServiceMemberMapperImpl implements ServiceMemberMapper {

    @Override
    public Member serviceRequestToMember(CreateMemberServiceRequest request) {
        if ( request == null ) {
            return null;
        }

        String name = null;
        MemberStatus status = null;

        name = request.name();
        status = request.status();

        UUID memberId = createUUID();

        Member member = new Member( memberId, name, status );

        return member;
    }
}
