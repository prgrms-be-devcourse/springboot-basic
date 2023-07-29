package org.prgrms.kdt.member.controller.mapper;

import javax.annotation.processing.Generated;
import org.prgrms.kdt.member.controller.dto.CreateMemberApiRequest;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.member.service.dto.CreateMemberRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-30T02:19:39+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class ControllerMemberMapperImpl implements ControllerMemberMapper {

    @Override
    public CreateMemberRequest convertRequest(CreateMemberApiRequest request) {
        if ( request == null ) {
            return null;
        }

        String name = null;
        MemberStatus status = null;

        name = request.name();
        status = request.status();

        CreateMemberRequest createMemberRequest = new CreateMemberRequest( name, status );

        return createMemberRequest;
    }
}
