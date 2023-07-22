package programmers.org.voucher.repository.util.sqlBuilder;

import programmers.org.voucher.repository.util.statement.select.From;
import programmers.org.voucher.repository.util.statement.select.Select;
import programmers.org.voucher.repository.util.statement.Where;

import static programmers.org.voucher.repository.util.constant.Symbol.BLANK;

public class SelectBuilder {
    private static final String FROM = "FROM";

    private StringBuilder query = new StringBuilder();

    public SelectBuilder select(Select select) {
        query.append(select.getQuery());
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
