package org.programmers.devcourse.voucher.engine.voucher.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.programmers.devcourse.voucher.engine.voucher.VoucherService;
import org.programmers.devcourse.voucher.engine.voucher.VoucherType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/voucher")
public class VoucherRestController {

  private final VoucherService voucherService;

  public VoucherRestController(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @GetMapping("")
  public List<VoucherDto> getAllVouchers() {
    return voucherService.getAllVouchers()
        .stream()
        .map(VoucherDto::from)
        .collect(Collectors.toList());
  }

  @GetMapping("/type/{type}")
  public List<VoucherDto> getAllVouchersByType(@PathVariable String type) {
    // TODO: 바우처 필터 기능을 컨트롤러에서 담당하는 게 맞을까? Repository에 맡기는 것이 나을까?
    return voucherService.getAllVouchers()
        .stream()
        .filter(voucher -> VoucherType.mapToTypeId(voucher).equals(type))
        .map(VoucherDto::from)
        .collect(Collectors.toList());
  }

  @GetMapping("/created-at/{createdAt}")
  public List<VoucherDto> getAllVouchersByTypes(@PathVariable("createdAt") String dateString) {
    // 특정 일자 기준으로 필터링
    var parsedDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay();
    return voucherService.getAllVouchers()
        .stream()
        .filter(voucher -> voucher.getCreatedAt().isAfter(parsedDate))
        .map(VoucherDto::from)
        .collect(Collectors.toList());
  }

  @GetMapping("/id/{voucherId}")
  public VoucherDto getVoucherById(@PathVariable UUID voucherId) {
    return VoucherDto.from(voucherService.getVoucherById(voucherId));
  }

  @PostMapping("")
  public VoucherDto registerVoucher(@RequestBody VoucherRegistrationDto voucherRegistrationDto) {
    var voucher = voucherService.create(voucherRegistrationDto.getVoucherType(), voucherRegistrationDto.getDiscountDegree());
    return VoucherDto.from(voucher);
  }

  @DeleteMapping("/{voucherId}")
  public Map<String, UUID> deleteVoucher(@PathVariable UUID voucherId) {
    voucherService.remove(voucherId);
    return Map.of("RemovedVoucherId", voucherId);
  }
}
