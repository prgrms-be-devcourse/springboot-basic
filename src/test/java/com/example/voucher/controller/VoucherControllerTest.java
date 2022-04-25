package com.example.voucher.controller;
import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.dto.VoucherListResponse;
import com.example.voucher.service.voucher.VoucherService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static com.example.voucher.domain.voucher.VoucherType.FIXED_AMOUNT_VOUCHER;
import static com.example.voucher.domain.voucher.VoucherType.PERCENT_DISCOUNT_VOUCHER;
import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("VoucherController 클래스의")
public class VoucherControllerTest {

	@InjectMocks
	private VoucherController voucherController;

	@Mock
	private VoucherService voucherService;

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class save메서드는 {

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 지원하는_바우처_타입과_정수_타입의_할인_값이_넘어온다면 {

			@Test
			@DisplayName("바우처를 생성하고 저장한다")
			void 바우처를_생성하고_저장한다() {
				voucherController.save(FIXED_AMOUNT_VOUCHER, 1000);
				verify(voucherService).save(any(VoucherType.class), anyInt());
			}
		}

		@Nested
		@TestInstance(TestInstance.Lifecycle.PER_CLASS)
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class FIXED_AMOUNT_VOUCHER_바우처_타입과_음수인_할인_값이_넘어온다면 {

			@BeforeEach
			void 음수인_할인_값_설정() {
				given(voucherService.save(any(VoucherType.class), anyInt()))
						.willThrow(new IllegalArgumentException(INVALID_INPUT.name()));
			}

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {
				assertThatThrownBy(() -> voucherController.save(FIXED_AMOUNT_VOUCHER, -1000))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(INVALID_INPUT.name());
			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class PERCENT_DISCOUNT_VOUCHER_바우처_타입과_음수인_할인_값이_넘어온다면 {

			@BeforeEach
			void 음수인_할인_값_설정() {
				given(voucherService.save(any(VoucherType.class), anyInt()))
						.willThrow(new IllegalArgumentException(INVALID_INPUT.name()));
			}

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {
				assertThatThrownBy(() -> voucherController.save(PERCENT_DISCOUNT_VOUCHER, -10))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(INVALID_INPUT.name());
			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class PERCENT_DISCOUNT_VOUCHER_바우처_타입과_백이_넘는_할인_값이_넘어온다면 {

			@BeforeEach
			void 백이_넘는_할인_값_설정() {
				given(voucherService.save(any(VoucherType.class), anyInt()))
						.willThrow(new IllegalArgumentException(INVALID_INPUT.name()));
			}

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {
				assertThatThrownBy(() -> voucherController.save(PERCENT_DISCOUNT_VOUCHER, 101))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(INVALID_INPUT.name());
			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 바우처_타입이_NULL이_넘어온다면 {

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {
				assertThatThrownBy(() -> voucherController.save(null, 10000))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(INVALID_INPUT.name());
			}
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class findAll메서드는 {

		@BeforeEach
		void 테스트를_위한_설정() {
			given(voucherService.findAll())
					.willReturn(new ArrayList<>());
		}
		
		@Test
		@DisplayName("바우처를 전체 조회하고 반환한다")
		void 바우처를_전체_조회하고_반환한다() {
			VoucherListResponse voucherListResponse = voucherController.findAll();

			verify(voucherService).findAll();
			Assertions.assertThat(voucherListResponse).isInstanceOf(VoucherListResponse.class);
		}
	}
}