package com.prgms.springbootbasic;

import com.prgms.springbootbasic.global.MenuController;
import com.prgms.springbootbasic.global.util.Application;

public class CommanmdLineApplication {
	
	public static void main(String[] args) {
		MenuController controller = Application.menuController();
		while (controller.run());
	}
	
}
