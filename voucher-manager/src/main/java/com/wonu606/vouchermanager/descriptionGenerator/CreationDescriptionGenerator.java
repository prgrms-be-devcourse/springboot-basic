package com.wonu606.vouchermanager.descriptionGenerator;

import java.util.List;

public class CreationDescriptionGenerator {

    public String generate(List<String> voucherTypes) {
        StringBuilder builder = new StringBuilder();

        builder.append("=== 바우처 타입 ===\n");
        for (String voucherType : voucherTypes) {
            builder.append(String.format("[%s]\n", voucherType));
        }

        builder.append("타입과 값을 입력해주세요.(띄워쓰기 단위로 구분)");
        return builder.toString();
    }
}
