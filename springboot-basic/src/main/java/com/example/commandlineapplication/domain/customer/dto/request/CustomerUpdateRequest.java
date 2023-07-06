package com.example.commandlineapplication.domain.customer.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomerUpdateRequest {

  private final String name;
  private final String email;
}
