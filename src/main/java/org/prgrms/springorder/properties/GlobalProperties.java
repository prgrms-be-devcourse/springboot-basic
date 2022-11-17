package org.prgrms.springorder.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "filepath")
public class GlobalProperties {

	private final String voucher;

	private final String blacklist;

	public GlobalProperties(String voucher, String blacklist) {
		this.voucher = voucher;
		this.blacklist = blacklist;
	}

	public String getVoucher() {
		return voucher;
	}

	public String getBlacklist() {
		return blacklist;
	}
}
