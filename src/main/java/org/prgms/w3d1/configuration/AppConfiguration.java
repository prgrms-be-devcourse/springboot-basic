package org.prgms.w3d1.configuration;



import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.prgms.w3d1.model", "org.prgms.w3d1.repository", "org.prgms.w3d1.controller"})
public class AppConfiguration {

}
