package programmers.org.voucher.repository.util.sqlBuilder;

import programmers.org.voucher.repository.util.statement.update.Set;
import programmers.org.voucher.repository.util.statement.update.Update;
import programmers.org.voucher.repository.util.statement.Where;

import static programmers.org.voucher.repository.util.constant.Symbol.BLANK;

public class UpdateBuilder {
    private static final String UPDATE = "UPDATE";

    private StringBuilder query = new StringBuilder();

    public UpdateBuilder update(Update update) {
        query.append(UPDATE)
                .append(BLANK.getSymbol())
                .append(update.getTable());
        return this;
    }

    public UpdateBuilder set(Set set) {
        query.append(set.getQuery());
        return this;
    }

    public UpdateBuilder where(Where where) {
        query.append(where.getQuery());
        return this;
    }

    public String build() {
        return query.toString();
    }
}
