package org.prgms.w3d1;



import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("application.properties")
@Configuration
@ComponentScan(basePackages = {"org.prgms.w3d1.model", "org.prgms.w3d1.repository", "org.prgms.w3d1.util"})
public class AppConfiguration {

}
