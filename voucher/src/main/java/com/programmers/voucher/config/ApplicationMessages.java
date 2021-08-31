package com.programmers.voucher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationMessages {

    @Value("${voucher.message.intro:Intro message here}")
    private String introMessage;

    @Value("${voucher.message.command.create.name:Require name message here}")
    private String requireName;

    @Value("${voucher.message.command.create.type:Require type message here}")
    private String requireType;

    @Value("${voucher.message.command.create.amount:Require amount message here}")
    private String requireAmount;

    @Value("${voucher.message.command.create.customerId:Require customer id here}")
    private String requireCustomerId;

    @Value("${voucher.message.command.create.fallback:Fallback message here}")
    private String fallback;

    @Value("${voucher.message.command.update.voucherId:Require voucher id here}")
    private String requireVoucherId;

    @Value("${voucher.message.command.update.field:Require update type here}")
    private String requireUpdateField;

    @Value("${voucher.message.command.update.value:Require update value here}")
    private String requireUpdateValue;

    public String getIntroMessage() {
        return introMessage;
    }

    public String getRequireName() {
        return requireName;
    }

    public String getRequireType() {
        return requireType;
    }

    public String getRequireAmount() {
        return requireAmount;
    }

    public String getRequireCustomerId() {
        return requireCustomerId;
    }

    public String getFallback() {
        return fallback;
    }

    public String getRequireVoucherId() {
        return requireVoucherId;
    }

    public String getRequireUpdateField() {
        return requireUpdateField;
    }

    public String getRequireUpdateValue() {
        return requireUpdateValue;
    }
}
