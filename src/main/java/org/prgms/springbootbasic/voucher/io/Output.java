package org.prgms.springbootbasic.voucher.io;

import java.util.List;
import java.util.Map;

import org.prgms.springbootbasic.voucher.vo.Voucher;

public interface Output {
	void print(String content);
	void printProgramManual();
	void printCreateVoucherManual();
	void printVoucherList(Map<String, List<Voucher>> voucherListByType);
}
