package org.prgrms.springbootbasic.view;

import static org.prgrms.springbootbasic.view.ViewText.CUSTOMER_BLACK_LIST;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CustomerBlackList {

    private static final String FILE_CUSTOMER_BLACKLIST_CSV = "file:customer_blacklist.csv";

    private final File customerBlackList;

    public CustomerBlackList(ApplicationContext applicationContext) throws IOException {
        this.customerBlackList = applicationContext
            .getResource(FILE_CUSTOMER_BLACKLIST_CSV)
            .getFile();
    }

    public void printCustomerBlackList() {
        try (
            var fileReader = new FileReader(customerBlackList);
            var br = new BufferedReader(fileReader)) {
            String line;

            System.out.println(CUSTOMER_BLACK_LIST.getText());
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
