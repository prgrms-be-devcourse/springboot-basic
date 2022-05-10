package com.programmers.order.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
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
	public VoucherDto.Response create(@RequestBody VoucherDto.Create createDto) {
		Voucher convertDomain = converter.createDtoToDomain().convert(createDto);
		Voucher voucher = voucherService.create(convertDomain);
		return converter.domainToResponseDto().convert(voucher);
	}
}
