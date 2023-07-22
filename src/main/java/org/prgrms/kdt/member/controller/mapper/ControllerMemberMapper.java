package org.prgrms.kdt.member.controller.mapper;

import org.mapstruct.Mapper;
import org.prgrms.kdt.member.controller.dto.CreateMemberControllerRequest;
import org.prgrms.kdt.member.service.dto.CreateMemberServiceRequest;

@Mapper(componentModel = "spring")
public interface ControllerMemberMapper {
    CreateMemberServiceRequest controllerRequestToServiceRequest(CreateMemberControllerRequest request);
}
