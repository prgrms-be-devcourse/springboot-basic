package org.prgrms.kdt.model.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.entity.VoucherEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

class FileVoucherRepositoryTest {
	@ParameterizedTest
	@DisplayName("FileVoucherRepository 클래스 저장 및 불러오기 테스트")
	@MethodSource("vouchersProvider")
	void createVoucherTest(List<VoucherEntity> voucherEntities) {
		// getTestVoucherFilePath
		Path voucherFilePath = getTestVoucherFilePath();
		deleteFileIfExist(voucherFilePath);
		FileIO fileIO = getFileIO(voucherFilePath);
		ObjectMapper objectMapper = new ObjectMapper();
		VoucherRepository voucherRepository = new FileVoucherRepository(objectMapper, fileIO);

		//when
		voucherEntities.stream()
			.forEach(voucher -> voucherRepository.createVoucher(voucher));
		List<VoucherEntity> expectedVoucherEntities = voucherRepository.readAll()
			.stream()
			.collect(Collectors.toList());

		//then
		Assertions.assertThat(voucherEntities)
			.containsExactlyInAnyOrderElementsOf(expectedVoucherEntities);
	}

	private static Stream<Arguments> vouchersProvider() {
		List<VoucherEntity> vouchers1 = Arrays.asList(
			new VoucherEntity(5L, 100, VoucherType.FixedAmountVoucher),
			new VoucherEntity(6L, 10, VoucherType.PercentDiscountVoucher),
			new VoucherEntity(7L, 50, VoucherType.FixedAmountVoucher)
		);

		List<VoucherEntity> vouchers2 = Arrays.asList(
			new VoucherEntity(5L, 30, VoucherType.PercentDiscountVoucher),
			new VoucherEntity(2L, 20, VoucherType.FixedAmountVoucher),
			new VoucherEntity(4L, 10, VoucherType.FixedAmountVoucher)
		);

		List<VoucherEntity> vouchers3 = Arrays.asList(
			new VoucherEntity(3L, 10, VoucherType.FixedAmountVoucher),
			new VoucherEntity(2L, 50, VoucherType.PercentDiscountVoucher),
			new VoucherEntity(1L, 30, VoucherType.PercentDiscountVoucher)
		);

		return Stream.of(
			Arguments.of(vouchers1),
			Arguments.of(vouchers2),
			Arguments.of(vouchers3)
		);
	}

	private FileIO getFileIO(Path voucherFilePath) {
		String fileName= voucherFilePath.getFileName().toString();
		String dirPath = voucherFilePath.getParent().toString();

		FileIO fileIO = new FileIO(fileName, dirPath);
		return fileIO;
	}

	Path getTestVoucherFilePath() {
		Path currentFolderPath = Paths.get("");
		String currentFolder = currentFolderPath.toAbsolutePath().toString();
		String fileName = "voucherTest.txt";

		return Paths.get(currentFolder, fileName);
	}

	void deleteFileIfExist(Path voucherFilePath) {
		File file = new File(voucherFilePath.toString());
		if (file.exists()) {
			if (file.delete()) {
				System.out.println("Existing file deleted successfully.");
			} else {
				System.out.println("Failed to delete the existing file.");
			}
		}
	}
}