package org.prgrms.springorder.repository.voucher;

import static org.prgrms.springorder.domain.ErrorMessage.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.prgrms.springorder.domain.VoucherFactory;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.prgrms.springorder.exception.FileLoadException;
import org.prgrms.springorder.exception.FileSaveException;
import org.prgrms.springorder.properties.FileVoucherProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("file")
@Component
public class FileVoucherRepository implements VoucherRepository {

	private final String filePath;
	private final File file;
	private final Map<UUID, Voucher> memory;

	public FileVoucherRepository(FileVoucherProperties fileVoucherProperties) {
		this.filePath = fileVoucherProperties.getPath();
		this.file = new File(filePath);
		this.memory = new HashMap<>();
	}

	@PostConstruct
	public void load() {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] voucherInfo = line.split(",");
				UUID id = UUID.fromString(voucherInfo[0]);
				VoucherType voucherType = VoucherType.getVoucherByName(voucherInfo[1]);
				Voucher voucher = VoucherFactory.createVoucher(voucherType, id, Double.parseDouble(voucherInfo[2]));
				memory.put(voucher.getVoucherId(), voucher);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileLoadException(FILE_LOAD_FAIL_MESSAGE, e);
		}
	}

	@PreDestroy
	public void saveFile() {

		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, false))) {
			for (Voucher voucher : memory.values()) {
				bufferedWriter.write(voucher.toString());
				bufferedWriter.flush();
				bufferedWriter.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileSaveException(FILE_SAVE_FAIL_MESSAGE, e);

		}
	}

	@Override
	public void save(Voucher voucher) {
		memory.put(voucher.getVoucherId(), voucher);
	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {
		return Optional.ofNullable(memory.get(voucherId));
	}

	@Override
	public List<Voucher> findAll() {
		return new ArrayList<>(memory.values());
	}

	@Override
	public void delete(UUID voucherId) {
		memory.remove(voucherId);
	}

	@Override
	public void update(Voucher voucher) {
		memory.replace(voucher.getVoucherId(), voucher);
	}

}