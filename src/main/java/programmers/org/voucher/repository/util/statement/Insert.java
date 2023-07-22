package programmers.org.voucher.repository.util.statement;

import programmers.org.voucher.repository.util.constant.Table;

import static programmers.org.voucher.repository.util.constant.Symbol.BLANK;

public class Insert extends Statement {

    private static final String INSERT = "INSERT INTO";

    public Insert(StringBuilder query) {
        this.query = query;
    }

    static public class Builder {
        private StringBuilder query = new StringBuilder();

        public Builder() {
            query.append(INSERT);
        }

        public Builder query(Table table) {
            query.append(BLANK.getSymbol())
                    .append(table);

            return this;
        }

        public Insert build() {
            return new Insert(query);
        }
    }
}
