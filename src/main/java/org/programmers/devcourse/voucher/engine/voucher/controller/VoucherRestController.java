package org.programmers.devcourse.voucher.engine.voucher.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.VoucherService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class VoucherRestController {

  private final VoucherService voucherService;

  public VoucherRestController(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @GetMapping("/api/v1/vouchers")
  public List<VoucherDto> getAllVouchers() {
    return voucherService.getAllVouchers()
        .stream()
        .map(VoucherDto::from)
        .collect(Collectors.toList());
  }

  @GetMapping("/api/v1/vouchers/type/{type}")
  public List<VoucherDto> getAllVouchersByType(@PathVariable String type) {
    return voucherService.getVouchersByType(type).stream().map(VoucherDto::from)
        .collect(Collectors.toUnmodifiableList());
  }

  @GetMapping("/api/v1/vouchers/created-at/{createdAt}")
  public List<VoucherDto> getAllVouchersByCreatedAt(@PathVariable("createdAt") String dateString) {
    try {
      var parsedDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyyMMdd"))
          .atStartOfDay();
      return voucherService.getVouchersByCreatedAt(parsedDate)
          .stream()
          .map(VoucherDto::from)
          .collect(Collectors.toList());

    } catch (DateTimeParseException exception) {
      log.error("Parsing Date Time Failed", exception);
      throw new VoucherException("날짜 입력이 잘못되었습니다.", exception);
    }
  }

  @GetMapping("/api/v1/vouchers/id/{voucherId}")
  public VoucherDto getVoucherById(@PathVariable UUID voucherId) {
    return VoucherDto.from(voucherService.getVoucherById(voucherId));
  }

  @PostMapping("/api/v1/vouchers")
  public VoucherDto registerVoucher(@RequestBody VoucherRegistrationDto voucherRegistrationDto) {
    var voucher = voucherService.create(voucherRegistrationDto.getVoucherType(),
        voucherRegistrationDto.getDiscountDegree());
    return VoucherDto.from(voucher);
  }

  @DeleteMapping("/api/v1/vouchers/{voucherId}")
  public Map<String, UUID> deleteVoucher(@PathVariable UUID voucherId) {
    voucherService.remove(voucherId);
    return Map.of("id", voucherId);
  }
}
