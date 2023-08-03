package org.prgrms.kdt.model.repository.file;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.VoucherRepository;
import org.prgrms.kdt.util.FileIO;

import com.fasterxml.jackson.databind.ObjectMapper;

class FileVoucherRepositoryTest {
	@ParameterizedTest
	@DisplayName("파일에서 voucher를 저장할 수 있다.")
	@MethodSource("vouchersProvider")
	void createVoucherTest(List<VoucherEntity> voucherEntities) {
		//given
		Path currentFolderPath = Paths.get("");
		java.lang.String currentFolder = currentFolderPath.toAbsolutePath().toString();
		java.lang.String fileName = "voucherTest.txt";
		Path voucherFilePath = Paths.get(currentFolder, fileName);

		File file = new File(voucherFilePath.toString());
		if (file.exists()) {
			if (file.delete()) {
				System.out.println("Existing file deleted successfully.");
			} else {
				System.out.println("Failed to delete the existing file.");
			}
		}

		FileIO fileIO = getFileIO(voucherFilePath);
		ObjectMapper objectMapper = new ObjectMapper();
		VoucherRepository voucherRepository = new FileVoucherRepository(objectMapper, fileIO);

		//when
		voucherEntities.stream()
			.forEach(voucher -> voucherRepository.saveVoucher(voucher));
		List<VoucherEntity> expectedVoucherEntities = voucherRepository.findAllEntities()
			.stream()
			.collect(Collectors.toList());

		//then
		Assertions.assertThat(voucherEntities)
			.containsExactlyInAnyOrderElementsOf(expectedVoucherEntities);
	}

	private static Stream<Arguments> vouchersProvider() {
		List<VoucherEntity> vouchers1 = Arrays.asList(
			new VoucherEntity(5L, 100, "FixedAmountVoucher"),
			new VoucherEntity(6L, 10, "PercentDiscountVoucher"),
			new VoucherEntity(7L, 50, "FixedAmountVoucher")
		);

		List<VoucherEntity> vouchers2 = Arrays.asList(
			new VoucherEntity(5L, 30, "PercentDiscountVoucher"),
			new VoucherEntity(2L, 20, "FixedAmountVoucher"),
			new VoucherEntity(4L, 10, "FixedAmountVoucher")
		);

		List<VoucherEntity> vouchers3 = Arrays.asList(
			new VoucherEntity(3L, 10, "FixedAmountVoucher"),
			new VoucherEntity(2L, 50, "PercentDiscountVoucher"),
			new VoucherEntity(1L, 30, "PercentDiscountVoucher")
		);

		return Stream.of(
			Arguments.of(vouchers1),
			Arguments.of(vouchers2),
			Arguments.of(vouchers3)
		);
	}

	private FileIO getFileIO(Path voucherFilePath) {
		java.lang.String fileName = voucherFilePath.getFileName().toString();
		java.lang.String dirPath = voucherFilePath.getParent().toString();

		FileIO fileIO = new FileIO(fileName, dirPath);
		return fileIO;
	}
}