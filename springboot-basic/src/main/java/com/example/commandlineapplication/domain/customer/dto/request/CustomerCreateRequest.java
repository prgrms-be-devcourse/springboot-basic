package com.example.commandlineapplication.domain.customer.dto.request;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CustomerCreateRequest {

  private final UUID customerId;
  private final String name;
  private final String email;
}
