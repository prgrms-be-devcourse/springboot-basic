package com.example.commandlineapplication.domain.customer.dto.request;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomerCreateRequest {

  private final UUID customerId;
  private final String name;
  private final String email;
}
