package org.prgrms.springorder.repository.customer;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.customer.CustomerType;
import org.prgrms.springorder.properties.BlackListProperties;
import org.prgrms.springorder.utils.FIleUtil;
import org.springframework.stereotype.Component;

@Component
public class FileBlackListRepository {

	private final File file;

	private final Map<UUID, Customer> memory;

	public FileBlackListRepository(BlackListProperties blackListProperties) {
		file = new File(blackListProperties.getPath());
		memory = new HashMap<>();
	}

	@PostConstruct
	public void loadBlackList() {

		List<String> customerList = FIleUtil.readFile(file);
		for (String voucherInfo : customerList) {
			String[] customerInfo = voucherInfo.split(",");
			UUID id = UUID.fromString(customerInfo[0]);
			memory.put(id, new Customer(id, customerInfo[1], customerInfo[2], LocalDateTime.parse(customerInfo[3],
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), CustomerType.BLACK));
		}
	}

	public void save(Customer customer) {
		memory.put(customer.getCustomerId(), customer);
	}

	public List<Customer> findAll() {
		return new ArrayList<>(memory.values());
	}

}

