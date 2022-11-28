package org.prgrms.springorder.service.customer;

import java.util.List;
import java.util.stream.Collectors;

import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.repository.customer.FileBlackListRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class BlackListService {

	private final FileBlackListRepository fileBlackListRepository;

	public BlackListService(FileBlackListRepository fileBlackListRepository) {
		this.fileBlackListRepository = fileBlackListRepository;
	}

	public List<String> getBlackList() {
		return fileBlackListRepository.findAll().stream().map(Customer::toString).collect(Collectors.toList());
	}

}
