package org.prgrms.kdt.servlet;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.prgrms.kdt.controller.VoucherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

public class KdtWebApplicationInitializer implements WebApplicationInitializer {
	private static final Logger logger = LoggerFactory.getLogger(KdtWebApplicationInitializer.class);

	@EnableWebMvc
	@Configuration
	@ComponentScan(basePackages = "org.prgrms.kdt")
	@EnableTransactionManagement
	static class ServletConfig implements WebMvcConfigurer, ApplicationContextAware {

		ApplicationContext applicationContext;
		@Override
		public void configureViewResolvers(ViewResolverRegistry registry) {
			registry.jsp().viewNames("jsp/*");

			SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
			springResourceTemplateResolver.setApplicationContext(applicationContext);
			springResourceTemplateResolver.setSuffix(".html");

			SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
			springTemplateEngine.setTemplateResolver(springResourceTemplateResolver);

			ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
			thymeleafViewResolver.setTemplateEngine(springTemplateEngine);
			registry.viewResolver(thymeleafViewResolver);
		}

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/")
				.setCachePeriod(60)
				.resourceChain(true)
				.addResolver(new EncodedResourceResolver());
		}

		@Bean
		public DataSource dataSource() throws SQLException {
			var dataSource = DataSourceBuilder.create()
				.url("jdbc:mysql://localhost/order_mgmt")
				.username("root")
				.password("root1234!")
				.type(HikariDataSource.class)
				.build();

			dataSource.setMaximumPoolSize(100);
			dataSource.setMinimumIdle(10);

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

		@Bean
		public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
			return new DataSourceTransactionManager(dataSource);
		}

		@Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			this.applicationContext = applicationContext;
		}
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		logger.info("Starting Server...");
		var applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(ServletConfig.class);

		var dispatcherServlet = new DispatcherServlet(applicationContext);
		var servletRegistration = servletContext.addServlet("test", dispatcherServlet);
		servletRegistration.addMapping("/");
		servletRegistration.setLoadOnStartup(-1);
	}
}
