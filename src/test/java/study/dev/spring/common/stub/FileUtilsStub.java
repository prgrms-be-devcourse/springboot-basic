package study.dev.spring.common.stub;

import java.util.List;
import java.util.UUID;

import study.dev.spring.common.utils.FileUtils;
import study.dev.spring.voucher.domain.VoucherType;
import study.dev.spring.voucher.infrastructure.dto.VoucherInfo;

public class FileUtilsStub implements FileUtils {

	@Override
	public boolean isSupported(String filePath) {
		return true;
	}

	@Override
	public <T> List<Object> readFile(final String filePath, final Class<T> type) {
		return List.of(
			new VoucherInfo(
				UUID.randomUUID(),
				"voucher",
				VoucherType.FIXED,
				1000
			)
		);
	}

	@Override
	public void writeFile(final String filePath, final List<Object> data) {
	}
}
