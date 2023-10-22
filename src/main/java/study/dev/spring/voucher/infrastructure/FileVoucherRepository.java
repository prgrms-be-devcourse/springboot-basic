package study.dev.spring.voucher.infrastructure;

import static study.dev.spring.common.exception.GlobalErrorCode.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PreDestroy;
import study.dev.spring.common.exception.GlobalException;
import study.dev.spring.common.utils.FileUtils;
import study.dev.spring.voucher.domain.Voucher;
import study.dev.spring.voucher.domain.VoucherRepository;
import study.dev.spring.voucher.infrastructure.dto.VoucherData;
import study.dev.spring.voucher.infrastructure.dto.VoucherMapper;

@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository {

	private final FileUtils fileUtils;
	private final String filePath;
	private final Map<UUID, Voucher> storage;

	public FileVoucherRepository(
		final List<FileUtils> fileUtilsList,
		@Value("${voucher.file.path}") final String filePath
	) {
		this.storage = new ConcurrentHashMap<>();
		this.filePath = filePath;

		this.fileUtils = getFileUtils(fileUtilsList, filePath);
		copyFileData(filePath);
	}

	@PreDestroy
	public void saveData() {
		List<VoucherData> voucherData = storage.values()
			.stream()
			.map(VoucherMapper::toVoucherData)
			.toList();

		fileUtils.writeFile(filePath, new ArrayList<>(voucherData));
	}

	@Override
	public void save(final Voucher voucher) {
		storage.put(voucher.getUuid(), voucher);
	}

	@Override
	public Optional<Voucher> findById(final UUID uuid) {
		return Optional.ofNullable(storage.get(uuid));
	}

	@Override
	public List<Voucher> findAll() {
		return new ArrayList<>(storage.values());
	}

	private FileUtils getFileUtils(
		final List<FileUtils> fileUtilsList,
		final String filePath
	) {
		return fileUtilsList.stream()
			.filter(fileUtil -> fileUtil.isSupported(filePath))
			.findFirst()
			.orElseThrow(() -> new GlobalException(UNSUPPORTED_EXT));
	}

	private void copyFileData(final String filePath) {
		List<Object> fileData = fileUtils.readFile(filePath, VoucherData.class);
		fileData.forEach(data -> {
			VoucherData voucherData = (VoucherData)data;
			storage.put(voucherData.getUuid(), VoucherMapper.toVoucher(voucherData));
		});
	}
}
