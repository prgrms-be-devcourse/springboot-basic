package org.prgrms.kdt.config;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class WebConfig implements WebApplicationInitializer {
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addFilter("httpMethodFilter", HiddenHttpMethodFilter.class)
            .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
    }
}

