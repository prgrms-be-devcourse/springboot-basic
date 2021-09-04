package org.programmers.kdt;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
public class AppConfiguration {

	// For Database
	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder.create()
				.url(url)
				.username(username)
				.password(password)
				.type(HikariDataSource.class)
				.build();
	}

	// Select specific table
	@Value("${spring.datasource.table.customers}")
	private String customerTable;

	@Value("${spring.datasource.table.customers-blacklist}")
	private String customerBlacklistTable;

	@Value("${spring.datasource.table.vouchers}")
	private String voucherTable;

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
	// For Database END ---

	// For File Based Repository
	@Value("${spring.file.voucher-list}")
	private String voucherDataFile;

	@Value("${spring.file.customer-list}")
	private String customerList;

	@Value("${spring.file.customer-blacklist}")
	private String customerBlacklist;

	@Bean
	public String voucherDataFile() {
		return voucherDataFile;
	}

	@Bean
	public String customerList() {
		return customerList;
	}

	@Bean
	public String customerBlacklist() {
		return customerBlacklist;
	}
	// For File Based Repository END ---
}
