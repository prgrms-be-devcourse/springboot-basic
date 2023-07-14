package co.programmers.voucher_management.voucher.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.context.ActiveProfiles;

import co.programmers.voucher_management.customer.repository.CustomerFileRepository;
import co.programmers.voucher_management.customer.repository.CustomerRepository;
import co.programmers.voucher_management.exception.NoSuchDataException;
import co.programmers.voucher_management.voucher.dto.VoucherAssignDTO;
import co.programmers.voucher_management.voucher.dto.VoucherRequestDTO;
import co.programmers.voucher_management.voucher.dto.VoucherResponseDTO;
import co.programmers.voucher_management.voucher.dto.VoucherUpdateDTO;
import co.programmers.voucher_management.voucher.repository.VoucherMemoryRepository;
import co.programmers.voucher_management.voucher.repository.VoucherRepository;

@ActiveProfiles("test")
		//TODO
class VoucherServiceTest {
	VoucherRepository voucherRepository = new VoucherMemoryRepository();
	CustomerRepository customerRepository = new CustomerFileRepository("src/main/resources/customer.csv");
	VoucherService voucherService = new VoucherService(voucherRepository, customerRepository);

	@ParameterizedTest
	@CsvSource(value = {
			"fixed,999999",
			"percent,99",
			"fixed,10000",
			"percent,50"
	}, delimiter = ',')
	void create(String discountType, int discountAmount) {
		// given
		VoucherRequestDTO voucherRequestDTO = new VoucherRequestDTO(discountType, discountAmount);
		// when
		VoucherResponseDTO response = voucherService.create(voucherRequestDTO);
		// then
		assertThat(response.getDiscountType(), is(equalTo(discountType)));
		assertThat(response.getDiscountAmount(), is(equalTo(discountAmount)));
	}

	@ParameterizedTest
	@CsvSource(value = {
			"fixed,999999",
			"percent,99",
			"fixed,10000",
			"percent,50"
	}, delimiter = ',')
	@DisplayName("바우처 목록 전체를 조회한다")
	void inquiryVoucherOfAll(String discountType, int discountAmount) {
		// given
		VoucherRequestDTO voucherRequestDTO = new VoucherRequestDTO(discountType, discountAmount);
		voucherService.create(voucherRequestDTO);
		// when
		List<VoucherResponseDTO> response = voucherService.inquiryVoucherOf();
		// then
		assertThat(discountType, is(equalTo(response.get(0).getDiscountType())));
		assertThat(discountAmount, is(equalTo(response.get(0).getDiscountAmount())));
	}

	@Test
	@DisplayName("소유한 고객의 아이디로 바우처를 검색한다")
	void iquiryVoucherOfByCustomerId() {
		// given
		String discountType = "fixed";
		int discountAmount = 50000;
		long customerId = 1L;
		VoucherRequestDTO voucherRequestDTO = new VoucherRequestDTO(discountType, discountAmount);
		VoucherResponseDTO voucher = voucherService.create(voucherRequestDTO);
		long voucherId = voucher.getId();
		// when
		voucherService.assignVoucher(new VoucherAssignDTO(customerId, voucherId));
		List<VoucherResponseDTO> voucherResponseDTOs = voucherService.inquiryVoucherOf(customerId);
		// then
		assertThat(voucherResponseDTOs.stream().
						filter(v -> v.getId() == voucherId).
						anyMatch(v -> v.getCustomerId() == customerId),
				is(true));
	}

	@ParameterizedTest
	@CsvSource(value = {
			"fixed,999999,percent,10",
			"percent,99,fixed,999999",
			"fixed,10000,percent,1",
			"percent,50,fixed,10000"
	}, delimiter = ',')
	@DisplayName("바우처의 할인 타입과 금액을 수정한다")
	void update(String prevType, int prevAmount, String updatedType, int updatedAmount) {
		// given
		VoucherRequestDTO voucherRequestDTO = new VoucherRequestDTO(prevType, prevAmount);
		VoucherResponseDTO voucher = voucherService.create(voucherRequestDTO);
		long id = voucher.getId();
		// when
		VoucherUpdateDTO voucherUpdateDTO = new VoucherUpdateDTO(id, updatedType, updatedAmount);
		// then
		VoucherResponseDTO updated = voucherService.update(voucherUpdateDTO);
		assertThat(updated.getDiscountType(), is(equalTo(updatedType)));
		assertThat(updated.getDiscountAmount(), is(equalTo(updatedAmount)));
	}

	@ParameterizedTest
	@CsvSource(value = {
			"fixed,999999",
			"percent,99",
			"fixed,10000",
			"percent,50"
	}, delimiter = ',')
	@DisplayName("주어진 아이디에 해당하는 바우처를 삭제한다")
	void deleteById(String discountType, int discountAmount) {
		//given
		VoucherRequestDTO voucherRequestDTO = new VoucherRequestDTO(discountType, discountAmount);
		VoucherResponseDTO voucher = voucherService.create(voucherRequestDTO);
		long id = voucher.getId();

		// when
		voucherService.deleteById(id);

		// then
		assertThrows(NoSuchDataException.class, () -> voucherService.deleteById(id));
	}

	@Test
	@DisplayName("바우처 아이디와 고객 아이디가 주어질 때 고객에게 해당 바우처를 할당한다")
	void assignVoucher() {
		// given
		String discountType = "fixed";
		int discountAmount = 50000;
		VoucherRequestDTO voucherRequestDTO = new VoucherRequestDTO(discountType, discountAmount);
		VoucherResponseDTO voucher = voucherService.create(voucherRequestDTO);
		long voucherId = voucher.getId();
		long customerId = 1L;

		// when
		voucherService.assignVoucher(new VoucherAssignDTO(customerId, voucherId));
		List<VoucherResponseDTO> voucherResponseDTOs = voucherService.inquiryVoucherOf(customerId);

		// then
		assertThat(voucherResponseDTOs.stream().
						filter(v -> v.getId() == voucherId).
						anyMatch(v -> v.getCustomerId() == customerId),
				is(true));
	}
}
