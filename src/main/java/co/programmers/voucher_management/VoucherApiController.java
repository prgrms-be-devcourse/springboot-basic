package co.programmers.voucher_management;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.programmers.voucher_management.voucher.dto.VoucherRequestDTO;
import co.programmers.voucher_management.voucher.dto.VoucherResponseDTO;
import co.programmers.voucher_management.voucher.service.VoucherService;

@RequestMapping("/api/v1/vouchers")
@RestController
public class VoucherApiController {
	private final VoucherService voucherService;

	public VoucherApiController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@GetMapping
	public ResponseEntity<List<VoucherResponseDTO>> find(
			@RequestParam(required = false) String type,
			@RequestParam(required = false, defaultValue = "-1") Long id
	) {
		if (id > 0) {
			return findById(id);
		}
		if (type != null) {
			return findByType(type);
		}
		return findAll();
	}

	public ResponseEntity<List<VoucherResponseDTO>> findAll() {
		List<VoucherResponseDTO> response = voucherService.inquiryVoucherOf();
		return ResponseEntity.ok(response);
	}

	private ResponseEntity<List<VoucherResponseDTO>> findById(@PathVariable long id) {
		VoucherResponseDTO response = voucherService.findById(id);
		return ResponseEntity.ok(List.of(response));
	}

	private ResponseEntity<List<VoucherResponseDTO>> findByType(@RequestParam String discountType) {
		List<VoucherResponseDTO> response = voucherService.findByType(discountType);
		return ResponseEntity.ok(response);
	}

	@GetMapping(params = {"startDate", "endDate"})
	public ResponseEntity<List<VoucherResponseDTO>> findByDate(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		List<VoucherResponseDTO> response = voucherService.findByDate(startDate, endDate);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<VoucherResponseDTO> create(@RequestBody VoucherRequestDTO voucherRequestDTO) {
		VoucherResponseDTO response = voucherService.create(voucherRequestDTO);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteById(@PathVariable long id) {
		voucherService.deleteById(id);
		return ResponseEntity.ok("");
	}
}
