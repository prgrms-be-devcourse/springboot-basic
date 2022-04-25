package com.programmers.order.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class JdbcConfig {

	private static final Logger log = LoggerFactory.getLogger(JdbcConfig.class);
	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String name;

	@Value("${spring.datasource.password}")
	private String password;

	@Bean
	public DataSource dataSource() {
		log.info("database connection .... ");

		HikariDataSource dataSource = DataSourceBuilder
				.create()
				.url(url)
				.username(name)
				.password(password)
				.type(HikariDataSource.class)
				.build();

		log.info("database connection complete.... ");
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
		return new NamedParameterJdbcTemplate(jdbcTemplate);
	}
}
