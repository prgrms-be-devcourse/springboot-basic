package com.programmers.vouchermanagement.configuration.properties;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "file")
public class AppProperties {
    private static final String[] CURRENT_DIRECTORIES = System.getProperty("user.dir").split(Pattern.quote(File.separator));
    private final Resources resources;
    private final Domains domains;

    @ConstructorBinding
    public AppProperties(Resources resources, Domains domains) {
        this.resources = resources;
        this.domains = domains;
    }

    public String getCSVCustomerFilePath() {
        if (isInBuild()) {
            return getCustomerCSVBuildFilePath();
        }
        return resources.path() + domains.customer().csvFileName();
    }

    public String getJSONCustomerFilePath() {
        if (isInBuild()) {
            return getCustomerJSONBuildFilePath();
        }
        return resources.path() + domains.customer().jsonFileName();
    }

    public String getVoucherFilePath() {
        if (isInBuild()) {
            return getVoucherBuildFilePath();
        }
        return resources.path() + domains.voucher().fileName();
    }

    private String getCustomerCSVBuildFilePath() {
        return getBuildDirectory() + resources.buildPath() + domains.customer().csvFileName();
    }

    private String getCustomerJSONBuildFilePath() {
        return getBuildDirectory() + resources.buildPath() + domains.customer().jsonFileName();
    }

    private String getVoucherBuildFilePath() {
        return getBuildDirectory() + resources.buildPath() + domains.voucher().fileName();
    }

    private boolean isInBuild() {
        return CURRENT_DIRECTORIES[CURRENT_DIRECTORIES.length - 2].equals("build");
    }

    private String getBuildDirectory() {
        String[] buildDirectories = Arrays.copyOfRange(CURRENT_DIRECTORIES, 0, CURRENT_DIRECTORIES.length - 1);
        return String.join(File.separator, buildDirectories);
    }
}
