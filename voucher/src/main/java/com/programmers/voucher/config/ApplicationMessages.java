package com.programmers.voucher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("voucher")
public class ApplicationMessages {

    @Value("${message.intro:Intro message here}")
    private String introMessage;

    @Value("${message.command.create.name:Require name message here}")
    private String requireName;

    @Value("${message.command.create.type:Require type message here}")
    private String requireType;

    @Value("${message.command.create.fallback:Fallback message here}")
    private String fallback;

    public String getIntroMessage() {
        return introMessage;
    }

    public void setIntroMessage(String introMessage) {
        this.introMessage = introMessage;
    }

    public String getRequireName() {
        return requireName;
    }

    public void setRequireName(String requireName) {
        this.requireName = requireName;
    }

    public String getRequireType() {
        return requireType;
    }

    public void setRequireType(String requireType) {
        this.requireType = requireType;
    }

    public String getFallback() {
        return fallback;
    }

    public void setFallback(String fallback) {
        this.fallback = fallback;
    }
}
