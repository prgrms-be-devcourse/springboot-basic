package org.programmers.kdt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.file")
public class FileConfigProperties {
	// Files
	private String customerFile;
	private String customerBlacklistFile;
	private String voucherListFile;

	@Bean
	public String customerFile() {
		return customerFile;
	}

	@Bean
	public String customerBlacklistFile() {
		return customerBlacklistFile;
	}

	@Bean
	public String voucherListFile() {
		return voucherListFile;
	}

	public void setCustomerFile(String customerFile) {
		this.customerFile = customerFile;
	}

	public void setCustomerBlacklistFile(String customerBlacklistFile) {
		this.customerBlacklistFile = customerBlacklistFile;
	}

	public void setVoucherListFile(String voucherListFile) {
		this.voucherListFile = voucherListFile;
	}
}
