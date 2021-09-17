package com.prgrms.w3springboot.io;

import java.util.List;

import com.prgrms.w3springboot.voucher.Voucher;

public interface Output {
	void printInit();

	void printTypeChoice();

	void printDiscountAmountChoice();

	void printCreatedVoucher(Voucher createdVoucher);

	void printVoucherList(List<Voucher> voucherList);

	void printExit();

	void printInvalidMessage();

	void printUpdateVoucherChoice();

	void printUpdateAmountChoice();

	void printUpdatedVoucher(Voucher updatedVoucher);

	void printDeleteChoice();

	void printDeleteDone();
}
