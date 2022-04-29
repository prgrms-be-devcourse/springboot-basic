package org.prgrms.part1.engine;

import com.opencsv.bean.CsvToBeanBuilder;
import org.prgrms.part1.exception.VoucherException;
import org.prgrms.part1.io.Output;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class CsvReader implements Runnable{
    private final Output output;

    public CsvReader(Output output) {
        this.output = output;
    }

    @Override
    public void run() {
        //ToDo - CSV로 블랙리스트 입력 받아 출력하기
        List<User> blackList = getBlackList();
        printBlackList(blackList);
    }

    private List<User> getBlackList() {
        try {
            return new CsvToBeanBuilder<User>(new FileReader("blacklist.csv"))
                    .withType(User.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            throw new VoucherException("There is no blacklist!");
        }
    }

    private void printBlackList(List<User> blackList) {
        output.print("Show BlackList\n");
        blackList.forEach(u -> {
            output.print("name : " + u.getName());
            output.print("age : " + u.getAge() + "\n");
                });
    }
}
