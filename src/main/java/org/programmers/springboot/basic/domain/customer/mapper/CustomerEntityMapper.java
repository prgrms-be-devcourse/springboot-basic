package org.programmers.springboot.basic.domain.customer.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.programmers.springboot.basic.domain.customer.dto.CustomerRequestDto;
import org.programmers.springboot.basic.domain.customer.dto.CustomerResponseDto;
import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.util.generator.UUIDGenerator;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {

    CustomerEntityMapper INSTANCE = Mappers.getMapper(CustomerEntityMapper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "customerType", constant = "NORMAL")
    CustomerRequestDto mapToRequestDto(String name, String email);

    CustomerResponseDto mapToResponseDto(Customer customer);

    @Mapping(target = "customerId", source = "customerId")
    CustomerRequestDto mapToRequestDtoWithUUID(String customerId);

    @Mapping(target = "customerId", expression = "java(mapToCustomerId(uuidGenerator))")
    Customer mapToEntityWithGenerator(CustomerRequestDto customerRequestDto, @Context UUIDGenerator uuidGenerator);

    @Mapping(target = "customerId", expression = "java(mapToCustomerId(uuidGenerator))")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "customerType", constant = "NORMAL")
    Customer mapToEntityWithGeneratorForAllArgs(CustomerRequestDto customerRequestDto, @Context UUIDGenerator uuidGenerator);

    default UUID map(String value) {
        return UUID.fromString(value);
    }

    default UUID mapToCustomerId(@Context UUIDGenerator uuidGenerator) {
        return uuidGenerator.generateUUID();
    }
}
