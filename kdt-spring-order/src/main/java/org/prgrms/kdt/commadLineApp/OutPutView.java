package org.prgrms.kdt.commadLineApp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class OutPutView {


    public static void showBlackList(File blackList) throws IOException {
        List<String> outList = Files.readAllLines(Path.of(blackList.getPath()));
        System.out.println(outList.toString());
    }
}
