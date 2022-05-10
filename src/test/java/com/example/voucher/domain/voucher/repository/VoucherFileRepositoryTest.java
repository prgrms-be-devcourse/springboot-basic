package com.example.voucher.domain.voucher.repository;

import com.example.voucher.domain.voucher.FixedAmountVoucher;
import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.util.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.example.voucher.domain.voucher.VoucherType.EMPTY;
import static com.example.voucher.exception.ErrorMessage.*;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("VoucherFileRepository 클래스의")
public class VoucherFileRepositoryTest {

	private static final String PATH = "test-voucherList.csv";

	private final VoucherRepository voucherRepository = new VoucherFileRepository(PATH);

	@BeforeEach
	void 테스트를_위한_파일_경로_설정() {

	}
	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class save메서드는 {

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 바우처가_넘어온다면 {

			private Voucher createdVoucher;

			@BeforeEach
			void 테스트_위한_설정() {
				createdVoucher = new FixedAmountVoucher(1L, 1000);
			}

			@AfterEach
			void 생성된_파일_삭제() {
				new File(PATH).delete();
			}


			@Test
			@DisplayName("바우처를 저장하고 저장된 바우처를 반환한다")
			void 바우처를_저장하고_저장된_바우처를_반환한다() {
				try (MockedStatic<FileUtils> mockedStatic = mockStatic(FileUtils.class)) {
					Voucher savedVoucher = voucherRepository.save(createdVoucher);
					mockedStatic.verify(() -> FileUtils.writeFile(anyString(), anyString()));
					assertThat(savedVoucher).isEqualTo(createdVoucher);
				}
			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 바우처_저장중에_문제가_발생한다면 {

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {

				try (MockedStatic<FileUtils> mockedStatic = mockStatic(FileUtils.class)) {
					mockedStatic.when(() -> FileUtils.writeFile(anyString(), anyString()))
							.thenThrow(new IllegalArgumentException(FILE_WRITE_ERROR.getMessage()));

					assertThatThrownBy(() -> voucherRepository.save(new FixedAmountVoucher(null, 100)))
							.isInstanceOf(IllegalArgumentException.class)
							.hasMessage(FILE_WRITE_ERROR.getMessage());
				}
			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 바우처가_null이_넘어온다면 {

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {
				assertThatThrownBy(() -> voucherRepository.save(null))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(SERVER_ERROR.getMessage());
			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 바우처_타입이_EMPTY라면 {

			private Voucher createdVoucher;

			private class VoucherStub extends Voucher{
				public VoucherStub(VoucherType voucherType, Long voucherId, int discountAmount, LocalDateTime createdAt, LocalDateTime updatedAt) {
					super(voucherType, voucherId, discountAmount, createdAt, updatedAt);
				}
				@Override
				public int discount(int beforeDiscount) {
					return 0;
				}
			}

			@BeforeEach
			void 바우처_생성() {
				createdVoucher = new VoucherStub(EMPTY, null, 1000, now(), now());
			}

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {

				assertThatThrownBy(() -> voucherRepository.save(createdVoucher))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(SERVER_ERROR.getMessage());
			}
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class findAll메서드는 {

		List<String> fileStringList;

		@BeforeEach
		void 테스트_위한_설정() {
			fileStringList = Arrays.asList(new FixedAmountVoucher(1L, 1000).toString());
		}

		@Test
		@DisplayName("바우처를 전체 조회하고 반환한다")
		void 바우처를_전체_조회하고_반환한다() {
			try (MockedStatic<FileUtils> mockedStatic = mockStatic(FileUtils.class)) {
				mockedStatic.when(() -> FileUtils.readFile(anyString()))
						.thenReturn(fileStringList);

				List<Voucher> vouchers = voucherRepository.findAll();
				mockedStatic.verify(() -> FileUtils.readFile(anyString()));
				assertThat(vouchers.size()).isEqualTo(1);
				assertThat(vouchers.get(0).toString()).isEqualTo(fileStringList.get(0));
			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 저장된_바우처가_하나도_없다면 {

			@Test
			@DisplayName("빈 리스트를 반환한다")
			void 빈_리스트를_반환한다() {
				try (MockedStatic<FileUtils> mockedStatic = mockStatic(FileUtils.class)) {
					mockedStatic.when(() -> FileUtils.readFile(anyString()))
							.thenReturn(new ArrayList<>());

					List<Voucher> vouchers = voucherRepository.findAll();

					mockedStatic.verify(() -> FileUtils.readFile(anyString()));
					assertThat(vouchers.size()).isEqualTo(0);
				}
			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 바우처_조회중에_문제가_발생한다면 {

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {

				try (MockedStatic<FileUtils> mockedStatic = mockStatic(FileUtils.class)) {
					mockedStatic.when(() -> FileUtils.readFile(anyString()))
							.thenThrow(new IllegalArgumentException(FILE_READ_ERROR.getMessage()));

					assertThatThrownBy(() -> voucherRepository.findAll())
							.isInstanceOf(IllegalArgumentException.class)
							.hasMessage(FILE_READ_ERROR.getMessage());
				}
			}
		}
	}
}
