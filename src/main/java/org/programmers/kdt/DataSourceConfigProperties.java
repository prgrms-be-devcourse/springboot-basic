package org.programmers.kdt;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceConfigProperties {
	// Access
	private String url;
	private String username;
	private String password;

	// Table
	private String customerTable;
	private String customerBlacklistTable;
	private String voucherTable;

	// DataSource
	private DataSource dataSource;

	public String getUrl() {
		return url;
	}


	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}


	@Bean
	public String customerTable() {
		return customerTable;
	}


	@Bean
	public String customerBlacklistTable() {
		return customerBlacklistTable;
	}


	@Bean
	public String voucherTable() {
		return voucherTable;
	}


	@Bean
	public DataSource dataSource() {
		if (dataSource == null) {
			dataSource = DataSourceBuilder.create()
					.url(url)
					.username(username)
					.password(password)
					.type(HikariDataSource.class)
					.build();
		}
		return dataSource;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCustomerTable(String customerTable) {
		this.customerTable = customerTable;
	}

	public void setCustomerBlacklistTable(String customerBlacklistTable) {
		this.customerBlacklistTable = customerBlacklistTable;
	}

	public void setVoucherTable(String voucherTable) {
		this.voucherTable = voucherTable;
	}
}
