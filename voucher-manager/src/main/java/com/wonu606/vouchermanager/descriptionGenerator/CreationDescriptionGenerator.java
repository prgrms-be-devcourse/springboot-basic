package com.wonu606.vouchermanager.descriptionGenerator;

import java.util.List;

public class CreationDescriptionGenerator {

    public CreationDescriptionGenerator() {
    }

    public String generate(List<String> voucherTypes) {
        StringBuilder builder = new StringBuilder();

        for (String voucherType : voucherTypes) {
            builder.append(String.format("[%s]\n", voucherType));
        }
        return builder.toString();
    }
}
