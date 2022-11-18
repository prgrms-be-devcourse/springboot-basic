package org.prgrms.springorder.config;

import javax.sql.DataSource;

import org.prgrms.springorder.properties.JdbcProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Profile("jdbc")
public class AppConfig {

	private final JdbcProperties jdbcProperties;

	public AppConfig(JdbcProperties jdbcProperties) {
		this.jdbcProperties = jdbcProperties;
	}

	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder.create()
			.url(jdbcProperties.getUrl())
			.username(jdbcProperties.getUsername())
			.password(jdbcProperties.getPassword())
			.type(HikariDataSource.class)
			.build();
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource());
	}

}
