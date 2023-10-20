package com.prgrms.vouchermanagement.infra.utils;

import java.util.regex.Pattern;

public class MenuPatternUtils {

    public static final Pattern MENU = Pattern.compile("^(list|create|exit)$");

}
