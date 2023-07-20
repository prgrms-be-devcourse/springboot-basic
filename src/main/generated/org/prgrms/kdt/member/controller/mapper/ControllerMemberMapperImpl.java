package org.prgrms.kdt.member.controller.mapper;

import javax.annotation.processing.Generated;
import org.prgrms.kdt.member.controller.dto.ControllerCreateMemberRequest;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.member.service.dto.ServiceCreateMemberRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-20T18:31:51+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class ControllerMemberMapperImpl implements ControllerMemberMapper {

    @Override
    public ServiceCreateMemberRequest controllerRequestToServiceRequest(ControllerCreateMemberRequest request) {
        if ( request == null ) {
            return null;
        }

        String name = null;
        MemberStatus status = null;

        name = request.name();
        status = request.status();

        ServiceCreateMemberRequest serviceCreateMemberRequest = new ServiceCreateMemberRequest( name, status );

        return serviceCreateMemberRequest;
    }
}
