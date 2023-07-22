package programmers.org.voucher.repository.util.statement.insert;

import programmers.org.voucher.repository.util.constant.Table;

public class Insert {

    private Table table;

    public Insert(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }
}
