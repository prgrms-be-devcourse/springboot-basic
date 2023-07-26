package org.prgrms.kdt.member.controller.mapper;

import javax.annotation.processing.Generated;
import org.prgrms.kdt.member.controller.dto.CreateMemberControllerRequest;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.member.service.dto.CreateMemberServiceRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-26T15:46:22+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class ControllerMemberMapperImpl implements ControllerMemberMapper {

    @Override
    public CreateMemberServiceRequest controllerRequestToServiceRequest(CreateMemberControllerRequest request) {
        if ( request == null ) {
            return null;
        }

        String name = null;
        MemberStatus status = null;

        name = request.name();
        status = request.status();

        CreateMemberServiceRequest createMemberServiceRequest = new CreateMemberServiceRequest( name, status );

        return createMemberServiceRequest;
    }
}
