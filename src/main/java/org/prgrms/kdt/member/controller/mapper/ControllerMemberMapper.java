package org.prgrms.kdt.member.controller.mapper;

import org.mapstruct.Mapper;
import org.prgrms.kdt.member.controller.dto.ControllerCreateMemberRequest;
import org.prgrms.kdt.member.service.dto.ServiceCreateMemberRequest;

@Mapper(componentModel = "spring")
public interface ControllerMemberMapper {
    ServiceCreateMemberRequest controllerRequestToServiceRequest(ControllerCreateMemberRequest request);
}
