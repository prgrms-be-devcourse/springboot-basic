package org.prgms.black;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;

@Configuration
@ConfigurationProperties(prefix = "black")

public class BlackProperties implements InitializingBean {
    private String version;
    private String title;


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(MessageFormat.format("[BlackProperties] version -> {0}", version));
        System.out.println(MessageFormat.format("[BlackProperties] title -> {0}", title));
    }

    public String getVersion() {
        return version;
    }

    public String getTitle() {
        return title;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
