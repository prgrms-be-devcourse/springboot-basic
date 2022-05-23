package com.programmers.order.controller.api;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.order.controller.dto.VoucherDto;
import com.programmers.order.converter.VoucherConverter;
import com.programmers.order.domain.Voucher;
import com.programmers.order.service.VoucherService;

@RequestMapping("/api/voucher")
@RestController
public class VoucherRestController {

	private final VoucherConverter converter;
	private final VoucherService voucherService;

	public VoucherRestController(VoucherConverter converter, VoucherService voucherService) {
		this.converter = converter;
		this.voucherService = voucherService;
	}

	@PostMapping("")
	public VoucherDto.Response create(@Valid @RequestBody VoucherDto.Create createDto) {
		Voucher convertDomain = converter.createDtoToDomain().convert(createDto);
		Voucher savedVoucher = voucherService.create(convertDomain);

		return converter.domainToResponseDto().convert(savedVoucher);
	}

	@PutMapping("")
	public VoucherDto.Response update(@Valid @RequestBody VoucherDto.Update updateDto) {
		Voucher requestUpdate = converter.updateDtoToDomain().convert(updateDto);
		Voucher updatedVoucher = voucherService.update(requestUpdate);

		return converter.domainToResponseDto().convert(updatedVoucher);
	}

	@DeleteMapping("/{voucher_id}")
	public ResponseEntity<String> delete(@PathVariable("voucher_id") UUID voucherId) {
		voucherService.deleteById(voucherId);
		return ResponseEntity.ok("삭제가 완료되었습니다.");
	}

}
