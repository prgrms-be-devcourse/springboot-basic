package org.prgrms.springorder.controller.voucher;

import java.util.List;
import java.util.UUID;

import org.prgrms.springorder.controller.dto.VoucherResponseDto;
import org.prgrms.springorder.service.voucher.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoucherRestController {

	private final VoucherService voucherService;

	public VoucherRestController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@GetMapping("/api/v1/voucher")
	public ResponseEntity<List<VoucherResponseDto>> getList() {
		List<VoucherResponseDto> voucherList = voucherService.getList();
		return ResponseEntity.ok(voucherList);
	}

	@GetMapping("/api/v1/voucher/{voucherId}")
	public ResponseEntity<VoucherResponseDto> get(@PathVariable(name = "voucherId") UUID id) {
		VoucherResponseDto voucherResponseDto = voucherService.findById(id);
		return ResponseEntity.ok(voucherResponseDto);
	}

	@PostMapping("/api/v1/voucher")
	public ResponseEntity<Void> post(@RequestBody VoucherResponseDto voucherResponseDto) {
		voucherService.createVoucher(voucherResponseDto.getVoucherType(),voucherResponseDto.getValue());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/api/v1/voucher")
	public ResponseEntity<Void> delete(@PathVariable(name = "voucherId") UUID id) {
		voucherService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
