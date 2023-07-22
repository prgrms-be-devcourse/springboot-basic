package programmers.org.voucher.repository.util.sqlBuilder;

import programmers.org.voucher.repository.util.statement.delete.Delete;
import programmers.org.voucher.repository.util.statement.Where;

import static programmers.org.voucher.repository.util.constant.Symbol.BLANK;

public class DeleteBuilder {
    private static final String DELETE = "DELETE FROM";

    private StringBuilder query = new StringBuilder();

    public DeleteBuilder delete(Delete delete) {
        query.append(DELETE)
                .append(BLANK.getSymbol())
                .append(delete.getTable());

        return this;
    }

    public DeleteBuilder where(Where where) {
        query.append(where.getQuery());
        return this;
    }

    public String build() {
        return query.toString();
    }
}
