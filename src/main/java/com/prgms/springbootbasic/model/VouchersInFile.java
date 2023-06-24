package com.prgms.springbootbasic.model;

import com.prgms.springbootbasic.util.VoucherType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Primary
public class VouchersInFile implements VouchersStorage {

	private static File file = new File("c:\\github\\springboot-basic\\src\\main\\resources\\data.csv");
	
	@Override
	public void save(Voucher voucher) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		String voucherOfCsv = voucher.formatOfCSV();
		bw.write(voucherOfCsv);
		bw.newLine();
		if (bw != null) {
			bw.flush();
			bw.close();
		}
	}
	
	@Override
	public List<Voucher> findAll() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		List<Voucher> result = new ArrayList<>();
		while ((line = br.readLine()) != null) {
			String[] voucherOfCSV= line.split(" ");
			Voucher voucher = changeToVoucher(voucherOfCSV);
			result.add(voucher);
		}
		br.close();
		return result;
	}
	
	private Voucher changeToVoucher(String[] voucherOfCSV) {
		String type = voucherOfCSV[0];
		UUID voucherId = UUID.fromString(voucherOfCSV[1]);
		Long number = Long.parseLong(voucherOfCSV[2]);
		VoucherType voucherType = VoucherType.of(type);
		return voucherType.exsistVoucher(voucherId, number);
	}
}
