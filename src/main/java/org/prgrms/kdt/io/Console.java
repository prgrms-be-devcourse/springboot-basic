package org.prgrms.kdt.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 11:32 오후
 */
public class Console implements Output {

    private final static String GUIDE = "Voucher_Program_Guide";

    @Override
    public void guide() {
        try (BufferedReader intro = Files.newBufferedReader(Paths.get(GUIDE))) {
            System.out.println(intro.lines().collect(Collectors.joining(System.lineSeparator())));
        } catch (IOException e) {
            System.err.println("존재하지 않는 파일입니다.");
            System.exit(0);
        }
    }
}
