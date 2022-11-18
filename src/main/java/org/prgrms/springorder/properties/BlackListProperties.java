package org.prgrms.springorder.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "file.blacklist")
public class BlackListProperties {

	private final String path;

	public BlackListProperties(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
