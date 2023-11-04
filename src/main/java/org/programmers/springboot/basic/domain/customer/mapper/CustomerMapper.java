package org.programmers.springboot.basic.domain.customer.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.programmers.springboot.basic.domain.customer.dto.CustomerRequestDto;
import org.programmers.springboot.basic.domain.customer.dto.CustomerResponseDto;
import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.domain.customer.entity.vo.Email;
import org.programmers.springboot.basic.util.generator.UUIDGenerator;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", expression = "java(mapToEmail(email))")
    @Mapping(target = "customerType", constant = "NORMAL")
    CustomerRequestDto mapToRequestDto(String name, String email);

    @Mapping(target = "customerId", source = "customerId")
    CustomerRequestDto mapToRequestDtoWithUUID(String customerId);

    CustomerResponseDto mapToResponseDto(Customer customer);

    @Mapping(target = "customerId", expression = "java(generateUUID(uuidGenerator))")
    Customer mapTopEntityWithGenerator(CustomerRequestDto customerRequestDto, @Context UUIDGenerator uuidGenerator);

    @Mapping(target = "customerId", expression = "java(generateUUID(uuidGenerator))")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "customerType", constant = "NORMAL")
    Customer mapToEntityAllArgsWithGenerator(CustomerRequestDto customerRequestDto, @Context UUIDGenerator uuidGenerator);

    default UUID mapToUUID(String value) {
        return UUID.fromString(value);
    }

    default UUID generateUUID(@Context UUIDGenerator uuidGenerator) {
        return uuidGenerator.generateUUID();
    }

    default Email mapToEmail(String email) {
        return Email.valueOf(email);
    }
}
