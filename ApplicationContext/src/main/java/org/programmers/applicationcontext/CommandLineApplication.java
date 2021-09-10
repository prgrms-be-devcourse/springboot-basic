package org.programmers.applicationcontext;

import org.programmers.applicationcontext.config.AppConfiguration;
import org.programmers.applicationcontext.execute.CreateExecute;
import org.programmers.applicationcontext.execute.Execute;
import org.programmers.applicationcontext.execute.ExitExecute;
import org.programmers.applicationcontext.execute.ListExecute;
import org.programmers.applicationcontext.voucher.Voucher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandLineApplication {

    public static void main(String[] args) throws IOException {
        List<Voucher> voucherList = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        var commandLineContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        OutPutView outPutView = new OutPutView();
        CommandType commandType;
        String userCommandType;
        Execute createExecute = new CreateExecute();
        Execute listExecute = new ListExecute();
        Execute exitExecute = new ExitExecute();
        boolean loop = true;


        while (loop) {
            outPutView.selectMenu();
            userCommandType = bufferedReader.readLine();
            commandType = CommandType.of(userCommandType);

            if(commandType == null){
                System.out.println("해당 명령어는 올바르지 않습니다 다시 입력해주세요\n");
                continue;
            }

            switch (commandType) {
                case EXIT:
                    loop = exitExecute.execute(voucherList, commandLineContext, bufferedReader, outPutView);
                    break;
                case CREATE:
                    createExecute.execute(voucherList, commandLineContext, bufferedReader, outPutView);
                    break;
                case LIST:
                    listExecute.execute(voucherList, commandLineContext, bufferedReader, outPutView);
                    break;
                default:
                    outPutView.selectError();
                    break;
            }

        }
        outPutView.terminateProgram();
        bufferedReader.close();
    }

}
