package com.prgms.springbootbasic;

import com.prgms.springbootbasic.controller.MenuController;
import com.prgms.springbootbasic.util.VoucherApplication;

public class CommanmdLineApplication {
	
	public static void main(String[] args) {
		VoucherApplication voucherApplication = new VoucherApplication();
		MenuController controller = voucherApplication.getController();
		while (controller.run());
	}
	
}
