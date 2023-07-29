package org.prgrms.kdt.member.controller.mapper;

import org.mapstruct.Mapper;
import org.prgrms.kdt.member.controller.dto.CreateMemberApiRequest;
import org.prgrms.kdt.member.service.dto.CreateMemberRequest;

@Mapper(componentModel = "spring")
public interface ControllerMemberMapper {
    CreateMemberRequest controllerRequestToServiceRequest(CreateMemberApiRequest request);
}
