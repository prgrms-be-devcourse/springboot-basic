package org.prgrms.part1.engine;

import org.prgrms.part1.io.Output;

public class CsvReader implements Runnable{
    private final Output output;

    public CsvReader(Output output) {
        this.output = output;
    }

    @Override
    public void run() {
        //ToDo - CSV로 블랙리스트 입력 받아 출력하기
    }
}
