package com.programmers.order.controller.api;

import javax.validation.Valid;

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

}
