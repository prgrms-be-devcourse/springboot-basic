package org.prgrms.kdtspringdemo.domain.voucher.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VoucherFileRepositoryTest {

    @Test
	@DisplayName("save()호출 시 FileUtils.writeFile()을 호출합니다.")
	public void save() {
	}

	@Test
	@DisplayName("save()호출 시 FileUtils.writeFile()에 예외가 발생하면 예외가 전달됩니다.")
	public void saveWithFileUtilsException() {
	}

	@Test
	@DisplayName("findAll()호출 시 FileUtils.getFileString()을 호출합니다.")
	public void findAll() {
	}

	@Test
	@DisplayName("findAll()호출 시 FileUtils.getFileString()에 예외가 발생하면 예외가 전달됩니다.")
	public void findAllWithFileUtilsException() {
	}
}
