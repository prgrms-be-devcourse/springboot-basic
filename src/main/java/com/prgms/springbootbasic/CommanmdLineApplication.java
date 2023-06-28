package com.prgms.springbootbasic;

import com.prgms.springbootbasic.controller.MenuController;
import com.prgms.springbootbasic.util.VoucherApplication;

public class CommanmdLineApplication {
	
	public static void main(String[] args) {
		MenuController controller = VoucherApplication.menuController();
		while (controller.run());
	}
	
}
