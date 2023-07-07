package com.example.commandlineapplication.domain.customer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CustomerUpdateRequest {

  private final String name;
  private final String email;
}
