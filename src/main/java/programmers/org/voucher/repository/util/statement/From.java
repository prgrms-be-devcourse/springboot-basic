package programmers.org.voucher.repository.util.statement;

import programmers.org.voucher.repository.util.constant.Table;

public class From {

    private Table table;

    public From(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }
}
