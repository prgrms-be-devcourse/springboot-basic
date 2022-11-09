package org.prgrms.springorder.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.prgrms.springorder.domain.Voucher;
import org.prgrms.springorder.domain.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("file")
@Repository
public class FileVoucherRepository implements VoucherRepository {

	private final String filePath;
	private final File file;

	public FileVoucherRepository(@Value("${repository.file.voucher.save-path}") String filePath) {
		this.filePath = filePath;
		file = new File(filePath);

	}

	@Override
	public void save(Voucher voucher) {

		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, file.exists()))) {

			bufferedWriter.newLine();
			bufferedWriter.write(voucher.toString());
			bufferedWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Voucher> findAll() {

		List<Voucher> voucherList = new ArrayList<>();

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] voucherInfo = line.split(",");
				UUID id = UUID.fromString(voucherInfo[0]);
				VoucherType voucherType = VoucherType.getVoucherByName(voucherInfo[1]);
				Voucher voucher = voucherType.voucherMaker(id, Long.parseLong(voucherInfo[2]));
				voucherList.add(voucher);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return voucherList;
	}

}