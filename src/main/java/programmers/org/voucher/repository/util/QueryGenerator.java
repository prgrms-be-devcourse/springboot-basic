package programmers.org.voucher.repository.util;

import programmers.org.voucher.repository.util.statement.Statement;

public class QueryGenerator {

    public static String toQuery(Statement... sql) {

        StringBuilder stringBuilder = new StringBuilder();

        for (Statement statement : sql) {
            stringBuilder.append(statement.getQuery());
        }

        return stringBuilder.toString();
    }
}
