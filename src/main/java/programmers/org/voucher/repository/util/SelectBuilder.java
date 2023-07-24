package programmers.org.voucher.repository.util;

import programmers.org.voucher.repository.util.statement.From;
import programmers.org.voucher.repository.util.statement.Select;

import static programmers.org.voucher.repository.util.constant.Symbol.BLANK;
import static programmers.org.voucher.repository.util.constant.Symbol.COMMA;

public class SelectBuilder {
    private static final String SELECT = "SELECT";
    private static final String FROM = "FROM";

    private StringBuilder query = new StringBuilder();

    public SelectBuilder select(Select select) {
        query.append(SELECT);

        for (String column : select.getColumns()) {
            query.append(BLANK.getSymbol())
                    .append(column)
                    .append(COMMA.getSymbol());
        }

        query.deleteCharAt(query.length() - 1);

        return this;
    }

    public SelectBuilder from(From from) {
        query.append(BLANK.getSymbol())
                .append(FROM)
                .append(BLANK.getSymbol())
                .append(from.getTable());

        return this;
    }

    public SelectBuilder where(Where where) {
        query.append(where.getQuery());
        return this;
    }

    public String build() {
        return query.toString();
    }
}
