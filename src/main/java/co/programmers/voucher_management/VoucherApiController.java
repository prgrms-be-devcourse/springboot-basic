package co.programmers.voucher_management;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
@Controller
@RestController
public class VoucherApiController {
	private final VoucherService voucherService;

	public VoucherApiController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<VoucherResponseDTO>> findAll() {
		List<VoucherResponseDTO> response = voucherService.inquiryVoucherOf();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<VoucherResponseDTO> findById(@PathVariable long id) {
		VoucherResponseDTO response = voucherService.findById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping()
	public ResponseEntity<List<VoucherResponseDTO>> findByType(@RequestParam String discountType) {
		List<VoucherResponseDTO> response = voucherService.findByType(discountType);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/search")
	public ResponseEntity<List<VoucherResponseDTO>> findByDate(@RequestParam String startDate,
			@RequestParam String endDate) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startLocalDate = LocalDate.parse(startDate, dateTimeFormatter);
		LocalDate endLocalDate = LocalDate.parse(endDate, dateTimeFormatter);
		List<VoucherResponseDTO> response = voucherService.findByDate(startLocalDate, endLocalDate);
		return ResponseEntity.ok(response);
	}

	@PostMapping()
	public ResponseEntity<VoucherResponseDTO> create(@RequestBody VoucherRequestDTO voucherRequestDTO) {
		VoucherResponseDTO response = voucherService.create(voucherRequestDTO);
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/{id}/delete")
	public ResponseEntity deleteById(@PathVariable long id) {
		voucherService.deleteById(id);
		return ResponseEntity.ok("");
	}
}
