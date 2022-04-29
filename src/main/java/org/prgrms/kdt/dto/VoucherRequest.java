package org.prgrms.kdt.dto;

import java.util.UUID;

public class VoucherRequest {

  public record CreateRequest(int type, long amount) {

  }

  public record AssignRequest(UUID customerId) {

  }
}