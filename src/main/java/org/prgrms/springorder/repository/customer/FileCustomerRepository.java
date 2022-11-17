package org.prgrms.springorder.repository.customer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.prgrms.springorder.utils.FIleUtil;
import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.customer.CustomerType;
import org.prgrms.springorder.properties.GlobalProperties;
import org.springframework.stereotype.Component;

@Component
public class FileCustomerRepository implements CustomerRepository {

	private final File file;

	private final Map<UUID, Customer> memory;

	public FileCustomerRepository(GlobalProperties globalProperties) {
		file = new File(globalProperties.getBlacklist());
		memory = new HashMap<>();
	}

	@PostConstruct
	public void loadBlackList() {

		List<String> customerList = FIleUtil.readFile(file);
		for (String voucherInfo : customerList) {
			String[] customerInfo = voucherInfo.split(",");
			UUID id = UUID.fromString(customerInfo[0]);
			memory.put(id, new Customer(id, customerInfo[1], CustomerType.BLACK));
		}
	}

	@Override
	public List<Customer> getBlackList() {
		return new ArrayList<>(memory.values());
	}

}

