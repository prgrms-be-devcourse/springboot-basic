package org.prgrms.springorder.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "file.voucher")
public class FileVoucherProperties {

	private final String path;

	public FileVoucherProperties(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
