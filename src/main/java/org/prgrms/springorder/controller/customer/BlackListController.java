package org.prgrms.springorder.controller.customer;

import java.util.List;

import org.prgrms.springorder.service.customer.BlackListService;
import org.springframework.stereotype.Component;

@Component
public class BlackListController {

	private final BlackListService blackListService;

	public BlackListController(BlackListService blackListService) {
		this.blackListService = blackListService;
	}

	public List<String> getBlackList() {
		return blackListService.getBlackList();
	}
}
