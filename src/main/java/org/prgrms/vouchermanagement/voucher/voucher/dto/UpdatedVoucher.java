package org.prgrms.vouchermanagement.voucher.voucher.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class UpdatedVoucher {

  private UUID voucherId;

  private int reduction;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime createdAt;

  public UpdatedVoucher(UUID voucherId, int reduction, LocalDateTime createdAt) {
    this.voucherId = voucherId;
    this.reduction = reduction;
    this.createdAt = createdAt;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public UUID getVoucherId() {
    return voucherId;
  }

  public void setVoucherId(UUID voucherId) {
    this.voucherId = voucherId;
  }

  public int getReduction() {
    return reduction;
  }

  public void setReduction(int reduction) {
    this.reduction = reduction;
  }
}
